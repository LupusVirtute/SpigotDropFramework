package com.lupus.drop.managers;

import com.lupus.drop.dropping.IDropMaterial;
import org.bukkit.Material;

import java.util.*;

public class DropMaterialManager {
	static HashMap<Material, Set<IDropMaterial>> dropMaterials = new HashMap<>();
	public static void addDropMaterial(IDropMaterial dropMat){
		if (dropMat == null)
			return;
		Material mat = dropMat.getDropMaterial();
		if (!dropMaterials.containsKey(mat))
			dropMaterials.put(mat,new HashSet<>());
		dropMaterials.get(mat).add(dropMat);
	}
	public static void clear(){
		dropMaterials.clear();
	}
	public static Set<IDropMaterial> getDropMaterialsForMaterial(Material mat){
		Set<IDropMaterial> dropMats = dropMaterials.get(mat);
		return dropMats == null ? new HashSet<>() : dropMats;
	}
}
