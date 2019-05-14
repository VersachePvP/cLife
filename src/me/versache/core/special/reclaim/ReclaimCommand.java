package me.versache.core.special.reclaim;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.ConfigurationService;
import me.versache.core.utilities.util.Color;
import net.md_5.bungee.api.ChatColor;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ReclaimCommand implements CommandExecutor {
	
	private ReclaimManager rm;
	
	public ReclaimCommand(ReclaimManager rm) {
		this.rm = rm;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		Player player = (Player)sender;
		if(rm.hasReclaimed(player)) {
			player.sendMessage(Color.translate("&cYou have already reclaimed for this map!"));
			return false;
		}
		String rank = PermissionsEx.getPermissionManager().getUser(player).getGroupNames()[0];
		if(rm.getReclaim(rank).size()==0) {
			player.sendMessage(Color.translate("&cYou don't have any reclaims."));
			return false;
		}
		rm.setReclaimed(player, true);
		for(String command : rm.getReclaim(rank)) {
			command=command
					.replace("%USER%", player.getName())
					.replace("%RANK%", rank);
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		}
        player.sendMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME + ChatColor.GOLD + " » " + ConfigurationService.SECONDARY_COLOR + "You have successfully reclaimed!");
		
		return false;
	}

}
