package me.versache.core.hcf.event.tracker;

import org.bukkit.entity.Player;

import me.versache.core.hcf.event.CaptureZone;
import me.versache.core.hcf.event.EventTimer;
import me.versache.core.hcf.event.EventType;
import me.versache.core.hcf.event.faction.EventFaction;

public abstract interface EventTracker
{
  public abstract EventType getEventType();
  
  public abstract void tick(EventTimer paramEventTimer, EventFaction paramEventFaction);
  
  public abstract void onContest(EventFaction paramEventFaction, EventTimer paramEventTimer);
  
  public abstract boolean onControlTake(Player paramPlayer, CaptureZone paramCaptureZone);
  
  public abstract boolean onControlLoss(Player paramPlayer, CaptureZone paramCaptureZone, EventFaction paramEventFaction);
  
  public abstract void stopTiming();
}
