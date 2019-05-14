package me.versache.core.hcf.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;

public class KillMoneyListener implements Listener
{
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
    	
    	if(ConfigurationService.KIT_MAP == true) {
        if (e.getEntityType() == EntityType.PLAYER) {
            final Entity killer = (Entity)e.getEntity().getKiller();
            final Entity dead = (Entity)e.getEntity().getPlayer();
            Base.getPlugin().getEconomyManager().addBalance(killer.getUniqueId(), 100);
            e.getEntity().getKiller().sendMessage(ChatColor.YELLOW.toString() + "You have recieved " + ChatColor.GOLD + ChatColor.BOLD.toString() + "$100" + ChatColor.YELLOW + " for killing " + ChatColor.GOLD + ChatColor.BOLD.toString() + e.getEntity().getPlayer().getName() + ChatColor.YELLOW + ".");
        }
    }
    }
}
