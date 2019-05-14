package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			String r = "";
				if (args.length > 0) {
					for (int i = 0; i < args.length; i++) {
						r = r + args[i] + " ";
					}
					r = r.replace("&s", "§");
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4Alert&8]&f " + r));
				} else {
					sender.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
				}
			}
		return true;
	}

}
