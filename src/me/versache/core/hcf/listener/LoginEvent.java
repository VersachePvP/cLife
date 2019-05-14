package me.versache.core.hcf.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import net.md_5.bungee.api.ChatColor;

public class LoginEvent implements Listener {
	@EventHandler
	public void Join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 2F, 1F);
		for (String msg : Base.config.getStringList("Login")) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
		}
	}
}