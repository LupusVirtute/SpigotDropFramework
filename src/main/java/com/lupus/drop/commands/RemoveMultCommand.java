package com.lupus.drop.commands;

import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.drop.LuckMultiplier;
import com.lupus.drop.LupusDrop;
import com.lupus.drop.dropping.DropBehavior;
import com.lupus.utils.ColorUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


public class RemoveMultCommand extends LupusCommand {
	public RemoveMultCommand() {
		super("removemult", usage("/removemult","[TaskID]"), "Deletes Modifier specified by task id", 1);
	}

	@Override
	public void run(CommandSender commandSender, String[] strings) {
		if (!NumberUtils.isNumber(strings[0])){
			commandSender.sendMessage(ColorUtil.text2Color(strings[0]+" &4&lTo nie jest numer"));
			return;
		}
		int id = Integer.parseInt(strings[0]);
		LuckMultiplier mult = LupusDrop.
				getActiveLuckMultipliers().
				stream().
				filter(o ->
						o.getTask().getTaskId() == (id)
				).
				findFirst().
				orElse(null);
		if (mult == null){
			commandSender.sendMessage(ColorUtil.text2Color("&4&lPodane ID nie zostało zidentyfikowane"));
			return;
		}
		mult.getTask().cancel();
		DropBehavior.addModifier(-mult.getModifier());
		commandSender.sendMessage(ColorUtil.text2Color("&a&lPoprawnie usunięto modyfikator &6"+ mult.getModifier()+"x"));
		Bukkit.broadcastMessage(
				ColorUtil.text2Color("&4&l"+commandSender.getName()+" &9Usunął modyfikator &6"+mult.getModifier()+"x")
		);
		LupusDrop.removeLuckMultiplier(mult);
	}

}
