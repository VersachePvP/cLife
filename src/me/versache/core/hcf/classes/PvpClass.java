package me.versache.core.hcf.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.versache.core.Base;
import net.md_5.bungee.api.ChatColor;

public abstract class PvpClass
{
    public static final long DEFAULT_MAX_DURATION;
    protected final Set<PotionEffect> passiveEffects;
    protected final String name;
    protected final long warmupDelay;
    
    static {
        DEFAULT_MAX_DURATION = TimeUnit.MINUTES.toMillis(5L);
    }
    
    public PvpClass(final String name, final long warmupDelay) {
        this.passiveEffects = new HashSet<PotionEffect>();
        this.name = name;
        this.warmupDelay = warmupDelay;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getWarmupDelay() {
        return this.warmupDelay;
    }
    
    public boolean onEquip(final Player player) {
        for (final PotionEffect effect : this.passiveEffects) {
            player.addPotionEffect(effect, true);
        }
        for (final String message : Base.getPlugin().getConfig().getStringList("Classes.Class-Equip")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%class%", this.name));
        }
        return true;
    }
    
    public void onUnequip(final Player player) {
        for (final PotionEffect effect : this.passiveEffects) {
            for (final PotionEffect active : player.getActivePotionEffects()) {
                if (active.getDuration() > PvpClass.DEFAULT_MAX_DURATION && active.getType().equals((Object)effect.getType()) && active.getAmplifier() == effect.getAmplifier()) {
                    player.removePotionEffect(effect.getType());
                    break;
                }
            }
        }
        for (final String message : Base.getPlugin().getConfig().getStringList("Classes.Class-Unequip")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%class%", this.name));
        }
    }
    
    public abstract boolean isApplicableFor(final Player p0);
}
