package me.versache.core.factions.factionsystem.event;

import com.google.common.base.Preconditions;

import me.versache.core.factions.factionsystem.type.Faction;

import org.bukkit.event.Event;

public abstract class FactionEvent extends Event {
    protected final Faction faction;

    public FactionEvent(final Faction faction) {
        this.faction = (Faction) Preconditions.checkNotNull((Object) faction, (Object) "Faction cannot be null");
    }

    FactionEvent(final Faction faction, final boolean async) {
        super(async);
        this.faction = (Faction) Preconditions.checkNotNull((Object) faction, (Object) "Faction cannot be null");
    }

    public Faction getFaction() {
        return this.faction;
    }
}
