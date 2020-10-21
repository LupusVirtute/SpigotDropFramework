package com.lupus.drop;

import com.lupus.drop.runnables.DropRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class LuckMultiplier implements ConfigurationSerializable {
	BukkitTask task;
	double modifier;
	Instant future;
	public LuckMultiplier(Map<String,Object> map){
		if (map.containsKey("future") && map.containsKey("modifier")) {
			future = Instant.ofEpochSecond((long)map.get("future"));
			modifier = (double)map.get("modifier");
			long time = Instant.now().getEpochSecond()-future.getEpochSecond();
			if (time > 0) {
				task = new DropRunnable(modifier).runTaskLater(LupusDrop.getMain(), time);
			}
		}
	}
	public LuckMultiplier(BukkitTask task, double modifier, Instant future){
		this.task = task;
		this.modifier = modifier;
		this.future = future;
	}
	public BukkitTask getTask(){
		return task;
	}

	public double getModifier() {
		return modifier;
	}

	public void setModifier(double modifier) {
		this.modifier = modifier;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> map = new HashMap<>();
		map.put("future",future.getEpochSecond());
		map.put("modifier",modifier);
		return map;
	}
}
