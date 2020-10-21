package com.lupus.drop.dropping;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public interface IDropMaterial {
	Material getDropMaterial();
	Set<IDropBehavior> getDropBehaviors();
	void addDropBehavior(IDropBehavior behav);
	int getWeight();
	void tryDrop(BlockBreakEvent e);
}
