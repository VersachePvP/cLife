package me.versache.core.hcf.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.Base;
import me.versache.core.hcf.deathban.Deathban;
import me.versache.core.utilities.random.Cooldowns;
import me.versache.core.utilities.user.FactionUser;

public class GoldReviveCommand implements CommandExecutor {
	private final Base plugin;

	public GoldReviveCommand(final Base plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " [player]");
			return true;
		}
		final Player player = (Player) sender;
		final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		if (Cooldowns.isOnCooldown("gold_cooldown", player)) {
			sender.sendMessage("§cYou cannot do this for another §l"
					+ Cooldowns.getCooldownForPlayerInt("gold_cooldown", player) / 60 + " §cminutes.");
			return true;
		}
		final UUID targetUUID = target.getUniqueId();
		final FactionUser factionTarget = this.plugin.getUserManager().getUser(targetUUID);
		final Deathban deathban = factionTarget.getDeathban();
		if (deathban == null || !deathban.isActive()) {
			sender.sendMessage(ChatColor.RED + target.getName() + " is not death-banned.");
			return true;
		}

		factionTarget.removeDeathban();
		sender.sendMessage(
				ChatColor.GREEN + "You have revived " + ChatColor.GREEN + target.getName() + ChatColor.GREEN + '.');
		Bukkit.broadcastMessage(ChatColor.AQUA + sender.getName() + ChatColor.GRAY + " has use their donator revive on " + ChatColor.AQUA + target.getName()
				+ ChatColor.GRAY + "." + "You can purchase this at " + ChatColor.AQUA + "store.hcstatic.com");
		Cooldowns.addCooldown("gold_cooldown", player, 3600);

		return true;
	}

}
