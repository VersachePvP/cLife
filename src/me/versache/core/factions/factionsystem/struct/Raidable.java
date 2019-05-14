package me.versache.core.factions.factionsystem.struct;

public interface Raidable {
    boolean isRaidable();

    double getDeathsUntilRaidable();

    double getMaximumDeathsUntilRaidable();

    double setDeathsUntilRaidable(double p0);

    long getRemainingRegenerationTime();

    void setRemainingRegenerationTime(long p0);

    RegenStatus getRegenStatus();
}