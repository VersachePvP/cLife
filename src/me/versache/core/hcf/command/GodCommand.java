package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.ConfigurationService;
import me.versache.core.hcf.listener.GodListener;

public class GodCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("god")) {
			if (!sender.hasPermission("command.god")) {
				sender.sendMessage(ChatColor.RED + "You lack the sufficient permissions to execute this command.");
				return true;
			}
			if (args.length < 1) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You cannot execute this command from CONSOLE");
					return true;
				}
				Player p = (Player) sender;
				if (GodListener.getInstance().isinGod(p)) {
					GodListener.getInstance().setGod(p, false);
					p.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.RED + "disabled " + ConfigurationService.SECONDARY_COLOR  + "your " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "God Mode");
					return true;
				}
				GodListener.getInstance().setGod(p, true);
				p.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.GREEN + "enabled " + ConfigurationService.SECONDARY_COLOR  + "your " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "God Mode");
				return true;
			}
			if (!sender.hasPermission("command.god.others")) {
				sender.sendMessage(ChatColor.RED + "You lack the sufficient permissions to execute this command.");
				return true;
			}
			Player t = Bukkit.getPlayer(args[0]);
			if (t == null) {
				sender.sendMessage("§cPlayer not found.");
				return true;
			}
			if (GodListener.getInstance().isinGod(t)) {
				GodListener.getInstance().setGod(t, false);
				sender.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.RED + "disabled " + ConfigurationService.SECONDARY_COLOR + t.getName() + "'s " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Staff Mode");
				return true;
			}
			GodListener.getInstance().setGod(t, true);
			t.setAllowFlight(true);
			sender.sendMessage(ConfigurationService.SECONDARY_COLOR + "You have " + ChatColor.GREEN + "enabled " + ConfigurationService.SECONDARY_COLOR + t.getName() + "'s " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + "Staff Mode");
			return true;
		}
		return false;
	}

}
