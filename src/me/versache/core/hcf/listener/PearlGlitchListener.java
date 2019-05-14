package me.versache.core.hcf.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.InventoryHolder;

import me.versache.core.Base;
import me.versache.core.factions.factionsystem.type.ClaimableFaction;
import me.versache.core.factions.factionsystem.type.Faction;
import net.minecraft.util.com.google.common.collect.ImmutableSet;
import net.minecraft.util.com.google.common.collect.Sets;

public class PearlGlitchListener implements Listener
{
    @SuppressWarnings("unused")
	private final ImmutableSet<Material> blockedPearlTypes;
    private final Base plugin;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public PearlGlitchListener(final Base plugin) {
        this.blockedPearlTypes = (ImmutableSet<Material>)Sets.immutableEnumSet((Enum)Material.THIN_GLASS, (Enum[])new Material[] { Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE, Material.FENCE_GATE, Material.ACACIA_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS });
        this.plugin = plugin;
    }
    Location previous;
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
      if (e.getMessage().contains(":"))
      {
        e.setCancelled(true);
      }
    }
    
    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void disableCommand23(PlayerCommandPreprocessEvent event)
    {
     	event.getPlayer();
      String message = event.getMessage().toLowerCase();
      String c = "/" + "me";
      if ((message.equals(c)) || (message.startsWith(c + " ")))
      {
       event.setCancelled(true);
      }
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e)
    {
      Player p = e.getPlayer();
      {
        if ((e.isCancelled()) || (e.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
          return;
        }
        e.getTo();
        if ((e.getTo().getBlock().getType() == Material.FENCE_GATE))
        {
          p.sendMessage(ChatColor.GOLD + "You're pearl has been canceled!");
          p.sendMessage(ChatColor.YELLOW + "Please retry pearling.");
          this.plugin.getTimerManager().enderPearlTimer.refund(p);
          e.setCancelled(true);
        }
        Location target = e.getTo();
        target.setX(target.getBlockX() + 0.60D);
        target.setZ(target.getBlockZ() + 0.65D);
        e.setTo(target);
      }
    }
    
    
    
    @EventHandler
    public void onMove(PlayerInteractEvent e){
        if(e.getPlayer().getLocation().getBlock() != null){
            if(e.getPlayer().getLocation().getBlock().getType() == Material.TRAP_DOOR){
                    if(!Base.getPlugin().getFactionManager().getFactionAt(e.getPlayer().getLocation()).equals(Base.getPlugin().getFactionManager().getPlayerFaction(e.getPlayer().getUniqueId()))) {
                        e.getPlayer().teleport(e.getPlayer().getLocation().add(0, 1, 0));
                    }
        }
        }
    }

}