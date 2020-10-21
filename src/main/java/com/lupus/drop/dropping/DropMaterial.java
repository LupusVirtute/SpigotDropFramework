package com.lupus.drop.dropping;

import com.lupus.drop.managers.BehaviorManager;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public class DropMaterial implements IDropMaterial{
	int weight;
	String dropType;
	public DropMaterial(Material mat,String dropType,int weight){
		dropFrom = mat;
		this.weight = weight;
		this.dropType = dropType;
		dropBehaviors = BehaviorManager.getBehaviorFromType(dropType);
	}
	Material dropFrom;
	Set<IDropBehavior> dropBehaviors;

	@Override
	public Material getDropMaterial() {
		return dropFrom;
	}
	@Override
	public int getWeight(){
		return weight;
	}
	@Override
	public Set<IDropBehavior> getDropBehaviors() {
		return dropBehaviors;
	}
	@Override
	public void addDropBehavior(IDropBehavior behav){
		dropBehaviors.add(behav);
	}
	@Override
	public void tryDrop(BlockBreakEvent e) {
		for (IDropBehavior behav : dropBehaviors) {
			behav.execute(this,e);
		}
	}
}
