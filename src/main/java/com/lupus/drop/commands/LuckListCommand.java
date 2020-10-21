package com.lupus.drop.commands;

import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.drop.LuckMultiplier;
import com.lupus.drop.LupusDrop;
import com.lupus.utils.ColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;

public class LuckListCommand extends LupusCommand {
	public LuckListCommand(){
		super("multlist",usage("/multlist","[page]"),"Shows the list of luck modifiers active",0);
	}
	static DecimalFormat d2 = new DecimalFormat("#.##");
	@Override
	public void run(CommandSender commandSender, String[] strings) {
		commandSender.sendMessage(ColorUtil.text2Color("&1=== &6Multipliers List &1==="));
		for (LuckMultiplier luckMult : LupusDrop.getActiveLuckMultipliers()){
			String mess = ChatColor.GOLD + d2.format(luckMult.getModifier()) + "x" +ChatColor.DARK_BLUE+ " ID Taska: "+ChatColor.RED+luckMult.getTask().getTaskId();
			commandSender.sendMessage(mess);
		}
		commandSender.sendMessage(ColorUtil.text2Color("&1========================"));

	}
}
