package me.versache.core.hcf.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.com.google.common.collect.ImmutableList;

public class CommandBlockerListener implements Listener {

	private static final ImmutableList<String> OP_BLOCKED_COMMANDS = ImmutableList.of("//evaluate", "//calc", "//solve",
			"//calculate", "/litebans sqlexec select * from {history}", "//eval", "/about", "/stop");

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		if (OP_BLOCKED_COMMANDS.contains(message.toLowerCase())) {
			player.sendMessage(ChatColor.RED + "Blocked command.");
			event.setCancelled(true);
			return;
		}

		if (player.hasPermission("*") || player.isOp())
			return;

		if (message.startsWith("/bukkit:") || message.startsWith("/minecraft:")) {
			player.sendMessage(ChatColor.RED + "You cannot do this command!");
			if (message.contains(":")) {
				player.sendMessage(ChatColor.RED + "You cannot do this command!");
				event.setCancelled(true);
				return;
			}

		}
	}
}
