package me.versache.core.factions.factionsystem.argument;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.versache.core.Base;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.command.CommandArgument;

public class FactionVersionArgument extends CommandArgument
{
    private final Base plugin;
    
    public FactionVersionArgument(final Base plugin) {
        super("author", "View plugin information.");
        this.plugin = plugin;
    }
    
    public String getUsage(final String label) {
        return "/" + label + ' ' + this.getName();
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + BukkitUtils.STRAIGHT_LINE_DEFAULT));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&o((&7This server is running &6" + Base.getPlugin().getDescription().getName() + "&7. Version: &6" + Base.getPlugin().getDescription().getVersion() + "&7&o))"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 » &7Created by &6&lVersache_"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 » &7Discord&7: &eVersache#1438"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 » &7This core was created for &6&l&nViciousMC&r &7and cannot be used on any other server without permission."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + BukkitUtils.STRAIGHT_LINE_DEFAULT));
        return true;
    }
}
