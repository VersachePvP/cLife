	package me.versache.core.hcf.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.versache.core.Base;

public class StaffGuideCommand implements CommandExecutor {
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!commandSender.hasPermission("command.mod")) {
			commandSender.sendMessage(ChatColor.RED + "No Permission.");
			return true;
	}
		if (command.getName().equalsIgnoreCase("staffguide")) {
			for (String msg : Base.config.getStringList("Staffguide")) {
				commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
			return true;
		}
		return false;
	}
}
