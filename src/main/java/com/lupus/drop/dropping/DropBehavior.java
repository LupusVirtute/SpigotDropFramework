package com.lupus.drop.dropping;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;

/**
 * Drop Behavior is the behavior of how the system should react on successful drop chance
 * in executeDrop the successful drop chance is processed
 *
 */
public abstract class DropBehavior implements IDropBehavior {
	protected static double modifier;
	String dropType;
	protected List<Material> materials;
	public DropBehavior(String dropType,List<Material> materials){
		this.dropType = dropType;
		this.materials = materials;
	}
	public static double getModifier(){return modifier; }
	public static void setModifier(double modif){
		modifier = modif;
	}
	public static void addModifier(double mod){
		modifier += mod;
	}
	@Override
	public String getDropType() {
		return dropType;
	}

	/**
	 * Should only be called from IDropMaterial
	 * @param sender sender of this call
	 * @param e Event that was sent from
	 */
	@Override
	public void execute(IDropMaterial sender,BlockBreakEvent e){
		Material mat;
		if (!materials.contains(Material.AIR))
			mat = materials.stream().filter(o->o.equals(sender.getDropMaterial())).findFirst().orElse(null);
		else
			mat = Material.AIR;
		if(mat != null){
			Player p = e.getPlayer();
			if(p == null || e.getBlock() == null){
				return;
			}
			int threshold = sender.getWeight();
			if (p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
				return;
			int fortune = p.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
			double luckModifier = modifier;
			if(fortune != 0){
				luckModifier += ((double)fortune * 0.33d);
			}
			Random rand = new Random();
			double r = rand.nextInt(threshold);
			rand = new Random();
			double rr = rand.nextInt(30) + 2;
			if (p.hasPermission("lupusdrop.2x")) {
				rr = rr * 2;
			}
			if (r <= rr) {
				executeDrop(e,sender,luckModifier,rr);
			}

		}
	}
	protected abstract void executeDrop(BlockBreakEvent e,IDropMaterial mat,double luckModifier,double rr);

}
