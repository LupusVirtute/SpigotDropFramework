package com.lupus.drop.commands;

import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.drop.LupusDrop;
import com.lupus.utils.ColorUtil;
import org.bukkit.command.CommandSender;

public class MultiplierHelpCommand extends LupusCommand {
	public MultiplierHelpCommand(){
		super("multhelp",usage("/multhelp"),"Multiplier help",0);
	}
	@Override
	public void run(CommandSender commandSender, String[] strings) {
		commandSender.sendMessage(ColorUtil.text2Color("&1--- &5Multiplier Help &1---"));
		for (LupusCommand command : LupusDrop.commands) {
			commandSender.sendMessage(command.getUsageDesc());
		}
	}
}
