package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.ConfigurationService;

public class FlyCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (!sender.hasPermission("command.fly")) {
				sender.sendMessage(ChatColor.RED + "You lack the sufficient permissions to execute this command.");
				return true;
			}
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to execute this command");
					return true;
				}
				Player p = (Player) sender;
				if (p.getAllowFlight()) {
					p.setAllowFlight(false);
					p.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.RED + "disabled " + ConfigurationService.SECONDARY_COLOR  + "your " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Flight Mode");
					return true;
				}
				p.setAllowFlight(true);
				p.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.GREEN + "enabled " + ConfigurationService.SECONDARY_COLOR  + "your " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Flight Mode");
				return true;
			}
			if (!sender.hasPermission("command.fly.others")) {
				sender.sendMessage(ChatColor.RED + "You lack the sufficient permissions to execute this command.");
				return true;
			}
			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				sender.sendMessage("§cPlayer not found.");
				return true;
			}
			if (t.getAllowFlight()) {
				t.setAllowFlight(false);
				sender.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.RED + "disabled " + ConfigurationService.SECONDARY_COLOR  + t.getName() + "'s" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Flight Mode");
				return true;
			}
			t.setAllowFlight(true);
			sender.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.GREEN + "enabled " + ConfigurationService.SECONDARY_COLOR  + t.getName() + "'s" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Flight Mode");
			return true;
		}
		return false;
	}

}