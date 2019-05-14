package me.versache.core.hcf.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TLCommand implements CommandExecutor {
	
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("No Console");
            return true;
        }
        final Player player = (Player)sender;
	    final int x = player.getLocation().getBlockX();
	    final int y = player.getLocation().getBlockY();
	    final int z = player.getLocation().getBlockZ();
		
	    if (command.getName().equalsIgnoreCase("tl")) {
	    		player.chat("/f message " + player.getName() + "'s location is " + ChatColor.GRAY + "[" + ChatColor.WHITE + x + ", " + y + ", " + z + ChatColor.GRAY + "]");
                return true;
            }
		return true;
    }

}
