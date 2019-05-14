package me.versache.core.hcf.event.argument;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.versache.core.Base;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.hcf.event.EventTimer;
import me.versache.core.utilities.util.command.CommandArgument;

public class EventCancelArgument
  extends CommandArgument
{
  private final Base plugin;
  
  public EventCancelArgument(Base plugin)
  {
    super("cancel", "Cancels a running event", new String[] { "stop", "end" });
    this.plugin = plugin;
    this.permission = ("hcf.command.event.argument." + getName());
  }
  
  
  public String getUsage(String label)
  {
    return '/' + label + ' ' + getName();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    EventTimer eventTimer = this.plugin.getTimerManager().eventTimer;
    Faction eventFaction = eventTimer.getEventFaction();
    if (!eventTimer.clearCooldown())
    {
      sender.sendMessage(ChatColor.RED + "There is not a running event.");
      return true;
    }
    return true;
  }
}
