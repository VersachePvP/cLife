package me.versache.core.hcf.listener.fixes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.versache.core.Base;

public class HungerFixListener
  implements Listener
{
  
  @EventHandler
  public void onMove(PlayerMoveEvent e)
  {
    if ((Base.getPlugin().getFactionManager().getFactionAt(e.getPlayer().getLocation()).isSafezone()) && (e.getPlayer().getFoodLevel() < 20))
    {
      e.getPlayer().setFoodLevel(20);
      e.getPlayer().setSaturation(20.0F);
    }
  }
  
  @EventHandler
  public void onHungerChange(FoodLevelChangeEvent e)
  {
    if ((e.getEntity() instanceof Player))
    {
      Player p = (Player)e.getEntity();
      if (Base.getPlugin().getFactionManager().getFactionAt(p.getLocation()).isSafezone())
      {
        p.setSaturation(20.0F);
        p.setHealth(20.0D);
      }
      p.setSaturation(10.0F);
    }
  }
}
