package me.versache.core.hcf.listener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.versache.core.Base;
import net.minecraft.util.gnu.trove.map.TObjectIntMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;

public class KillstreakListener implements Listener
{
    public static KillstreakListener INSTANCE;
    private final TObjectIntMap<UUID> killStreakMap;
    
    public KillstreakListener(final Base plugin) {
        KillstreakListener.INSTANCE = this;
        this.killStreakMap = (TObjectIntMap<UUID>)new TObjectIntHashMap();
    }
    
    public int getKillStreak(final OfflinePlayer player) {
        return this.killStreakMap.get((UUID)player.getUniqueId());
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        final Player entity = event.getEntity();
        if (entity.getKiller() instanceof Player) {
            final Player player = event.getEntity().getKiller();
            this.killStreakMap.adjustOrPutValue((UUID)player.getUniqueId(), 1, 1);
            for (final KillStreaks killStreaks : Base.killStreaks) {
                if (this.killStreakMap.get((Object)player.getUniqueId()) == killStreaks.getNumber()) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), killStreaks.getCommand().replace("%player%", player.getName()));
                    Bukkit.broadcastMessage(Base.getInstance().getConfig().getString("killstreaks.broadcast-message").replace("%item%", killStreaks.getName()).replace("%killstreak%", Integer.toString(killStreaks.getNumber())).replace("%player%", player.getName()).replaceAll("&", "s"));
                    break;
                }
            }
        }
        this.killStreakMap.put((UUID)entity.getUniqueId(), 0);
    }
}
