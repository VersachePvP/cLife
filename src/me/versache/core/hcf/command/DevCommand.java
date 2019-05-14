package me.versache.core.hcf.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.Base;
import net.md_5.bungee.api.ChatColor;

public class DevCommand implements CommandExecutor {
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (sender instanceof Player) {
			
			player.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*----------------------*--");
			player.sendMessage(ChatColor.GRAY + "» " + ChatColor.GOLD + ChatColor.BOLD.toString() + "Version" + ChatColor.GRAY + ": " + ChatColor.WHITE + Base.getPlugin().getDescription().getVersion());
			player.sendMessage(ChatColor.GRAY + "» " + ChatColor.GOLD + ChatColor.BOLD.toString() + "Server" + ChatColor.GRAY + ": " + ChatColor.WHITE + "HCLife");
			player.sendMessage(ChatColor.GRAY + "» " + ChatColor.GOLD + ChatColor.BOLD.toString() + "Author" + ChatColor.GRAY + ": " + ChatColor.WHITE + "Versache_");
			player.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*----------------------*--");
		}
		return false;
	}

}
