package com.lupus.drop.dropping;

import org.bukkit.event.block.BlockBreakEvent;

public interface IDropBehavior {
	String getDropType();
	void execute(IDropMaterial dropMaterial,BlockBreakEvent e);
}
