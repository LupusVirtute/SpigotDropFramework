package com.lupus.drop.listeners;

import com.lupus.drop.dropping.IDropMaterial;
import com.lupus.drop.managers.DropMaterialManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class BlockBreakListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e){
		Material mat = e.getBlock().getType();
		Set<IDropMaterial> materials = DropMaterialManager.getDropMaterialsForMaterial(mat);
		if (materials.size() < 1)
			return;
		for (IDropMaterial iDropMaterial : materials) {
			iDropMaterial.tryDrop(e);
		}
	}
}
