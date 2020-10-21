package com.lupus.drop.runnables;

import com.lupus.drop.dropping.DropBehavior;
import org.bukkit.scheduler.BukkitRunnable;

public class DropRunnable extends BukkitRunnable {
	double modifier;
	public DropRunnable(double modifier){
		this.modifier = modifier;
		DropBehavior.addModifier(modifier);
	}
	@Override
	public void run() {
		DropBehavior.addModifier(-modifier);
	}
}
