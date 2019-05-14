package me.versache.core.factions.combatlog;

import org.bukkit.scheduler.BukkitTask;

public class CombatLogEntry
{
    public final LoggerEntity loggerEntity;
    public final BukkitTask task;

    public CombatLogEntry(LoggerEntity loggerEntity, BukkitTask task)
    {
        this.loggerEntity = loggerEntity;
        this.task = task;
    }
}
