package me.versache.core.hcf.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.utilities.random.Cooldowns;

public class LFFCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command");
            return true;
        }
        if (Cooldowns.isOnCooldown("lff_cooldown", (Player)sender)) {
            sender.sendMessage(ChatColor.RED + "You are still on cooldown for " + ChatColor.RED + ChatColor.BOLD.toString() + Base.getRemaining(Cooldowns.getCooldownForPlayerLong("lff_cooldown", (Player)sender), true));
            return true;
        }
        Bukkit.broadcastMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        Bukkit.broadcastMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + "LFF" + ChatColor.GOLD + " » " + ConfigurationService.THIRD_COLOR + sender.getName() + ConfigurationService.SECONDARY_COLOR + " is looking for a " + ConfigurationService.THIRD_COLOR + ChatColor.BOLD.toString() + "FACTION" + ConfigurationService.SECONDARY_COLOR + "!");
        Bukkit.broadcastMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "--------------------------------");
        Cooldowns.addCooldown("lff_cooldown", (Player)sender, 900);
        return false;
    }
}
