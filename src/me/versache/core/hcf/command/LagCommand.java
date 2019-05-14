package me.versache.core.hcf.command;

import java.util.Collection;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class LagCommand implements Listener, CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		double tps = Bukkit.spigot().getTPS()[0];
		double lag = Math.round((1.0D - tps / 20.0D) * 100.0D);
		ChatColor colour;
		if (tps >= 18.0D) {
			colour = ChatColor.GREEN;
		} else {
			if (tps >= 15.0D) {
				colour = ChatColor.YELLOW;
			} else {
				colour = ChatColor.RED;
			}
		}
		sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------------------");
		sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Server TPS is currently at" + ChatColor.GRAY + ": " + ChatColor.WHITE + Math.round(tps * 10000.0D) / 10000.0D + '.');
		sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Server lag is currently at" + ChatColor.GRAY + ": " + ChatColor.WHITE + Math.round(lag * 10000.0D) / 10000.0D + '%');
		if (sender.hasPermission(command.getPermission() + ".memory")) {
			Runtime runtime = Runtime.getRuntime();
			sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Available Processors" + ChatColor.GRAY + ": " + ChatColor.WHITE + runtime.availableProcessors());
			sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Max Memory" + ChatColor.GRAY + ": " + ChatColor.WHITE + + runtime.maxMemory() / 1048576L + "MB");
			sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Total Memory" + ChatColor.GRAY + ": " + ChatColor.WHITE + + runtime.totalMemory() / 1048576L + "MB");
			sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Free Memory" + ChatColor.GRAY + ": " + ChatColor.WHITE + + runtime.freeMemory() / 1048576L + "MB");
			sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------------------");
			Collection<World> worlds = Bukkit.getWorlds();
			for (World world : worlds) {
				World.Environment environment = world.getEnvironment();
				String environmentName = WordUtils.capitalizeFully(environment.name().replace('_', ' '));
				int tileEntities = 0;
				Chunk[] loadedChunks2;
				Chunk[] loadedChunks = loadedChunks2 = world.getLoadedChunks();
				for (Chunk chunk : loadedChunks2) {
					tileEntities += chunk.getTileEntities().length;
				}
				sender.sendMessage(ChatColor.GOLD + world.getName() + '(' + environmentName + "): " + ChatColor.WHITE
						+ loadedChunks.length + " chunks, " + world.getEntities().size() + " entities, " + tileEntities
						+ " tile entities.");
				sender.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "----------------------------------");
			}
		}
		return true;
	}
}
