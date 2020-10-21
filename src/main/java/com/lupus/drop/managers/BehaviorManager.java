package com.lupus.drop.managers;

import com.lupus.drop.dropping.IDropBehavior;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BehaviorManager {
	static HashMap<String, HashSet<IDropBehavior>> map = new HashMap<>();
	public static void addBehavior(IDropBehavior behavior){
		if (behavior == null)
			return;
		String behavType = behavior.getDropType().toUpperCase();
		if (!map.containsKey(behavType))
			map.put(behavType,new HashSet<>());
		map.get(behavType).add(behavior);
	}
	public static Set<IDropBehavior> getBehaviorFromType(String type){
		return map.get(type.toUpperCase());
	}
	public static void removeBehaviorTypes(String behavType){
		map.remove(behavType.toUpperCase());
	}

}

