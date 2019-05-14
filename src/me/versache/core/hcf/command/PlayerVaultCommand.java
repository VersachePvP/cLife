package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;

public class PlayerVaultCommand implements CommandExecutor{
	private final Base plugin;

	public PlayerVaultCommand(Base plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "No Permission.");
			return false;
		}

		if(!ConfigurationService.KIT_MAP){
			sender.sendMessage(ChatColor.RED + "This commands can be executed on Kits only.");
			return true;
		}

		Player player = (Player) sender;
		Player target = player;
		if(player.hasPermission("commands.pv.others") && args.length != 0){
			target = Bukkit.getPlayer(args[0]);

			if(target == null){
				player.sendMessage(ChatColor.RED + "Unknown player.");
				return true;
			}
            player.sendMessage(ChatColor.RED + "You need to be at spawn to do this.");
        }
		if(Base.getPlugin().getFactionManager().getFactionAt(player.getLocation()).getName().equals("Spawn")) {
		player.openInventory(target.getEnderChest());
		player.sendMessage(ChatColor.WHITE + "Opened " + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD + (player.equals(target) ? "your" : target.getName() + "'s") + ChatColor.WHITE + " player vault.");

		return true;
	}
		player.sendMessage(ChatColor.RED + "You need to be at spawn inorder to execute this command!");
		return false;
	}
}