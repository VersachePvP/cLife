package me.versache.core.hcf.event.faction;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.claim.Claim;
import me.versache.core.factions.factionsystem.type.ClaimableFaction;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.hcf.event.CaptureZone;
import me.versache.core.hcf.event.EventType;
import me.versache.core.utilities.util.cuboid.Cuboid;

public abstract class EventFaction
  extends ClaimableFaction
{
  public EventFaction(String name)
  {
    super(name);
    setDeathban(true);
  }
  
  public EventFaction(Map<String, Object> map)
  {
    super(map);
    setDeathban(true);
  }
  
  @Override
  public String getDisplayName(Faction faction) {
      return ChatColor.BLUE + getName() + getEventType().getDisplayName();
  }
  
  @Override
  public String getDisplayName(CommandSender sender) {
      return ConfigurationService.KOTH_COLOR + getName().replace("EOTW", ChatColor.DARK_RED.toString() + ChatColor.BOLD + "EOTW").replace("Citadel", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Citadel")
    		  .replace("Palace", ChatColor.YELLOW.toString() + ChatColor.BOLD + "Palace");
  }

  public String getScoreboardName() {
      return ConfigurationService.KOTH_COLOR  + getName().replace("EOTW", ChatColor.DARK_RED.toString() + ChatColor.BOLD + "EOTW").replace("Citadel", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Citadel")
    		  .replace("Palace", ChatColor.YELLOW.toString() + ChatColor.BOLD + "Palace");
  }
  
  public void setClaim(Cuboid cuboid, CommandSender sender)
  {
    removeClaims(getClaims(), sender);
    Location min = cuboid.getMinimumPoint();
    min.setY(0);
    Location max = cuboid.getMaximumPoint();
    max.setY(256);
    addClaim(new Claim(this, min, max), sender);
  }
  
  
  
  public abstract EventType getEventType();
  
  public abstract List<CaptureZone> getCaptureZones();
}
