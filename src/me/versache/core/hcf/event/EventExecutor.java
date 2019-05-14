package me.versache.core.hcf.event;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.hcf.event.argument.EventCancelArgument;
import me.versache.core.hcf.event.argument.EventCreateArgument;
import me.versache.core.hcf.event.argument.EventDeleteArgument;
import me.versache.core.hcf.event.argument.EventRenameArgument;
import me.versache.core.hcf.event.argument.EventSetAreaArgument;
import me.versache.core.hcf.event.argument.EventSetCapzoneArgument;
import me.versache.core.hcf.event.argument.EventStartArgument;
import me.versache.core.hcf.event.argument.EventUptimeArgument;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.command.ArgumentExecutor;
import me.versache.core.utilities.util.command.CommandArgument;

public class EventExecutor
  extends ArgumentExecutor
{
  public EventExecutor(Base plugin)
  {
    super("event");
    addArgument(new EventCancelArgument(plugin));
    addArgument(new EventCreateArgument(plugin));
    addArgument(new EventDeleteArgument(plugin));
    addArgument(new EventRenameArgument(plugin));
    addArgument(new EventSetAreaArgument(plugin));
    addArgument(new EventSetCapzoneArgument(plugin));
    addArgument(new EventStartArgument(plugin));
    addArgument(new EventUptimeArgument(plugin));
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (args.length < 1)
    {
      if (sender.hasPermission("event.admin"))
      {
        sender.sendMessage(ChatColor.GOLD + BukkitUtils.STRAIGHT_LINE_DEFAULT);
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR.toString() + ChatColor.BOLD + "Event Help");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event cancel" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Cancels a running event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event create" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Defines a new event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event delete" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Deletes an event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event setarea" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Sets the area of an event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event setcapzone" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Sets the capzone of an event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event start" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Start an event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event rename" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Renames an event.");
        sender.sendMessage(ConfigurationService.THIRD_COLOR + " /event uptime" + ChatColor.GOLD +" » " + ConfigurationService.SECONDARY_COLOR + "Check the uptime of an event.");
        sender.sendMessage(ChatColor.GOLD + BukkitUtils.STRAIGHT_LINE_DEFAULT);
      }
      return true;
    }
    CommandArgument argument2 = getArgument(args[0]);
    String permission2 = argument2 == null ? null : argument2.getPermission();
    if ((argument2 == null) || ((permission2 != null) && (!sender.hasPermission(permission2))))
    {
      sender.sendMessage(ChatColor.RED + "Command not found");
      return true;
    }
    argument2.onCommand(sender, command, label, args);
    return true;
  }
}
