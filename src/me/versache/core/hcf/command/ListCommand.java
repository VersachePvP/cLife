package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("list")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------------------*--");
			sender.sendMessage(ChatColor.YELLOW + "There are currently " + ChatColor.RESET
					+ Bukkit.getOnlinePlayers().length + ChatColor.YELLOW + " out of " + ChatColor.RESET
					+ Bukkit.getMaxPlayers() + ChatColor.YELLOW + " connected to the server.");
			sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------------------*--");
		}
		return true;
	}

}
