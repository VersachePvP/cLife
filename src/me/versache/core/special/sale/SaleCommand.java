package me.versache.core.special.sale;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.hcf.timer.event.TimerExpireEvent;
import me.versache.core.special.sale.SaleListener;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.DurationFormatter;
import me.versache.core.utilities.util.JavaUtils;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;

public class SaleCommand implements CommandExecutor, TabCompleter {

	private final Base plugin;

	public SaleCommand(Base plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("command.keysalesale")) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}

		if (args.length == 0 || args.length > 2) {
            sender.sendMessage(
                    "§7§m--------------------------------");
            sender.sendMessage(
            		"§a§l/sale start <duration> §7-§e Starts the sale timer.");
            sender.sendMessage(
            		"§a§l/sale end §7-§e Ends the current sale timer.");
            sender.sendMessage(
                    "§7§m--------------------------------");
			return true;
		}

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("end")) {
				if (plugin.getTimerManager().getSaleTimer().clearCooldown()) {
					Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					Bukkit.broadcastMessage(ChatColor.RED + "The sale has been ended!");
					Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
							+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("start")) {
				long duration = JavaUtils.parse(args[1]);

				if (duration == -1L) {
					sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is an invalid duration.");
					return true;
				}

				if (duration < 1000L) {
					sender.sendMessage(ChatColor.RED + "keysale time must last for at least 20 ticks.");
					return true;
				}

				SaleTimer saleTimer = plugin.getTimerManager().getSaleTimer();

				if (!saleTimer.setRemaining(duration, true)) {
					sender.sendMessage(ChatColor.RED + "The keysale is already on.");
					return true;
				}

				sender.sendMessage(ChatColor.GREEN + "Started keysale timer for "
						+ DurationFormatUtils.formatDurationWords(duration, true, true) + ".");
				
				Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
						+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
				Bukkit.broadcastMessage(ChatColor.YELLOW + "A Sale has started! Visit " + ChatColor.WHITE + ConfigurationService.DONATE_URL
						+ ChatColor.YELLOW + " (" + DurationFormatUtils.formatDurationWords(duration, true, true) + ChatColor.YELLOW + ")");
				Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH
						+ BukkitUtils.STRAIGHT_LINE_DEFAULT);
			} else {
				sender.sendMessage(ChatColor.RED + "Unknown sub-command!");
				return true;
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Collections.emptyList();
	}
}
