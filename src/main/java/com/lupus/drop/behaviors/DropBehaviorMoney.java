package com.lupus.drop.behaviors;

import com.lupus.drop.LupusDrop;
import com.lupus.drop.dropping.DropBehavior;
import com.lupus.drop.dropping.IDropMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class DropBehaviorMoney extends DropBehavior {

	public DropBehaviorMoney() {
		super(
				"MONEY",
				Arrays.asList(
					Material.EMERALD_ORE,
					Material.DIAMOND_ORE,
					Material.LAPIS_ORE,
					Material.QUARTZ_ORE,
					Material.REDSTONE_ORE,
					Material.GOLD_ORE,
					Material.IRON_ORE,
					Material.COAL_ORE
				)
		);
	}

	@Override
	protected void executeDrop(BlockBreakEvent e, IDropMaterial material,double luckModifier,double rr) {
		Player p = e.getPlayer();
		double mon = (rr / 32.0d)*luckModifier;
		mon = new BigDecimal(Double.toString(mon)).setScale(2, RoundingMode.HALF_UP).floatValue();

		LupusDrop.getEconomy().depositPlayer(p,(double)mon);

		p.sendMessage(ChatColor.YELLOW + "Znalazłeś " + ChatColor.GOLD+ mon + "$");
		p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 2);
	}
}
