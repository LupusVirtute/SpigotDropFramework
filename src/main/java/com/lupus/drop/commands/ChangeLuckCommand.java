package com.lupus.drop.commands;

import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.drop.LuckMultiplier;
import com.lupus.drop.LupusDrop;
import com.lupus.drop.runnables.DropRunnable;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;

public class ChangeLuckCommand extends LupusCommand {
	public ChangeLuckCommand() {
		super("changemult", usage("/changemult","[multiplier] [time in sec]"), "Changes the drop multiplier to better one", 2);
	}

	@Override
	public void run(CommandSender commandSender, String[] strings) {
		if(!NumberUtils.isNumber(strings[0]) || !NumberUtils.isNumber(strings[1])) {
			commandSender.sendMessage(ChatColor.RED +"Podaj poprawne liczby");
			return;
		}
		double multi = Integer.parseInt(strings[0]);
		long time = Long.parseLong(strings[1])*20;
		BukkitTask task = new DropRunnable(multi).runTaskLater(LupusDrop.getMain(),time);
		LupusDrop.addLuckMultiplier(
			new LuckMultiplier(task,multi,Instant.now().plusSeconds(time))
		);
	}
}
