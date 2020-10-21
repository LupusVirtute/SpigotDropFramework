package com.lupus.drop;

import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.drop.behaviors.DropBehaviorMoney;
import com.lupus.drop.commands.ChangeLuckCommand;
import com.lupus.drop.commands.LuckListCommand;
import com.lupus.drop.commands.RemoveMultCommand;
import com.lupus.drop.dropping.DropMaterial;
import com.lupus.drop.dropping.IDropBehavior;
import com.lupus.drop.managers.BehaviorManager;
import com.lupus.drop.managers.DropMaterialManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Plugin(name="LupusDrop", version="1.0-SNAPSHOT")
@Description(desc = "Simple drop plugin")
@Author(name = "LupusVirtute")
@Website(url="github.com/PuccyDestroyerxXx")

@Dependency(plugin = "Vault")
@Dependency(plugin = "LupusUtils")
@Dependency(plugin = "LupusCaseOpener")
@Dependency(plugin = "LupusCommandFramework")

@Permission(name = "lupusdrop.2x",desc = "Double the drop!")

@Commands(
		{
			@Command(name = "changemult", permission = "lupusdrop.admin"),
			@Command(name = "multlist", permission = "lupusdrop.admin"),
			@Command(name = "removemult", permission = "lupusdrop.admin"),
		}
)
public class LupusDrop extends JavaPlugin {
	static Economy econ;
	static JavaPlugin main;
	public static JavaPlugin getMain(){
		return main;
	}
	static List<LuckMultiplier> activeLuckMultipliers;
	public static Economy getEconomy() {
		return econ;
	}
	public static List<LuckMultiplier> getActiveLuckMultipliers(){
		return activeLuckMultipliers;
	}
	public static void addLuckMultiplier(LuckMultiplier mult){
		activeLuckMultipliers.add(mult);
	}
	public static void removeLuckMultiplier(LuckMultiplier mult){
		activeLuckMultipliers.remove(mult);
	}
	@Override
	public void onEnable() {
		activeLuckMultipliers = new ArrayList<>();
		System.out.println("Enabling LupusDrop");
		if (!setupEconomy() ) {
			getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		main = this;
		initSerializableClasses();
		this.getConfig().getDefaults();
		initDropBehaviors();
		loadDropMaterials();
		loadMultipliers();
	}

	@Override
	public void onDisable() {
		saveMultipliers();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	public void initSerializableClasses(){
		ConfigurationSerialization.registerClass(LuckMultiplier.class);
	}
	public void loadMultipliers(){
		File file = new File(getDataFolder(),"multipliers.yml");
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		for (String key : configuration.getKeys(false)){
			LuckMultiplier multiplier = (LuckMultiplier) configuration.get(key);
			activeLuckMultipliers.add(multiplier);
		}
	}
	public void saveMultipliers(){
		File file = new File(getDataFolder(),"multipliers.yml");
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		for (int i = 0; i <activeLuckMultipliers.size(); i++) {
			configuration.set(String.valueOf(i),activeLuckMultipliers.get(i));
		}
		try {
			configuration.save(file);
		} catch (IOException ignored) {

		}
	}
	public void loadDropMaterials(){
		for (String key : this.getConfig().getKeys(false)) {
			Material mat = Material.getMaterial(key);
			int weight = getConfig().getInt(key+".weight");
			String dropType = getConfig().getString(key+".dropType");
			DropMaterialManager.addDropMaterial(new DropMaterial(mat,dropType.toUpperCase(),weight));
		}
	}
	public static void initDropBehaviors(){
		Reflections reflections = new Reflections("com.lupus.drop.behaviors");

		Set<Class<? extends IDropBehavior>> subTypes = reflections.getSubTypesOf(IDropBehavior.class);

		for (Class<? extends IDropBehavior> clazz : subTypes){
			Constructor<?> theRightOne = null;
			try {
				theRightOne = clazz.getConstructor();
			} catch (NoSuchMethodException ignored) {

			}

			if (theRightOne != null){
				IDropBehavior dropBehavior = null;
				try{
					dropBehavior = (IDropBehavior) theRightOne.newInstance();
				}
				catch (Exception ignored){

				}
				BehaviorManager.addBehavior(dropBehavior);
			}
		}
	}
	public static LupusCommand[] commands = new LupusCommand[]{
			new ChangeLuckCommand(),
			new LuckListCommand(),
			new RemoveMultCommand(),

	};
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		String cmd = command.getName().toLowerCase();
		for (LupusCommand lupusCommand : commands) {
			if (lupusCommand.isMatch(cmd)) {
				lupusCommand.run(sender,args);
				break;
			}
		}
		return super.onCommand(sender, command, label, args);
	}
}
