package me.versache.core.hcf.classes.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.versache.core.hcf.classes.PvpClass;

public class PvpClassEquipEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final PvpClass pvpClass;
    
    static {
        handlers = new HandlerList();
    }
    
    public PvpClassEquipEvent(final Player player, final PvpClass pvpClass) {
        super(player);
        this.pvpClass = pvpClass;
    }
    
    public PvpClass getPvpClass() {
        return this.pvpClass;
    }
    
    public static HandlerList getHandlerList() {
        return PvpClassEquipEvent.handlers;
    }
    
    public HandlerList getHandlers() {
        return PvpClassEquipEvent.handlers;
    }
}
