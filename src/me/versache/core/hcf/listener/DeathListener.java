package me.versache.core.hcf.listener;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.struct.Role;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import me.versache.core.utilities.user.FactionUser;
import me.versache.core.utilities.util.JavaUtils;

public class DeathListener implements Listener {
	
	public static HashMap<UUID, ItemStack[]> PlayerInventoryContents = new HashMap<UUID, ItemStack[]>();
	public static HashMap<UUID, ItemStack[]> PlayerArmorContents = new HashMap<UUID, ItemStack[]>();
	private static final long BASE_REGEN_DELAY = TimeUnit.MINUTES.toMillis(40L);
	private final Base plugin;

	public DeathListener(Base plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onPlayerDeathKillIncrement(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		
		if (killer != null) {
			FactionUser user = this.plugin.getUserManager().getUser(killer.getUniqueId());
			user.setKills(user.getKills() + 1);
		}

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().getInventory().clear();
		e.getPlayer().getInventory().setArmorContents(null);

	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player.getUniqueId());
		if (playerFaction != null) {
			Faction factionAt = this.plugin.getFactionManager().getFactionAt(player.getLocation());
			Role role = playerFaction.getMember(player.getUniqueId()).getRole();
			Player killer = event.getEntity().getKiller();
			if ((ConfigurationService.KIT_MAP) == false) {
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),

						"eco " + killer.getName() + " give 200");
						
			}
			if ((playerFaction.getDeathsUntilRaidable() >= -5.0D) && (ConfigurationService.KIT_MAP) == false) {
				playerFaction.setDeathsUntilRaidable(
						playerFaction.getDeathsUntilRaidable() - factionAt.getDtrLossMultiplier());
				player.getInventory().clear();
				playerFaction.setRemainingRegenerationTime(
						BASE_REGEN_DELAY + playerFaction.getOnlinePlayers().size() * TimeUnit.MINUTES.toMillis(2L));
				playerFaction.broadcast(ChatColor.YELLOW + "Member Death: " + ConfigurationService.TEAMMATE_COLOUR
						+ role.getAstrix() + player.getName() + ChatColor.YELLOW + ". DTR:" + ChatColor.GRAY + " ["
						+ playerFaction.getDtrColour()
						+ JavaUtils.format(Double.valueOf(playerFaction.getDeathsUntilRaidable())) + ChatColor.WHITE
						+ '/' + ChatColor.WHITE + playerFaction.getMaximumDeathsUntilRaidable() + ChatColor.GRAY
						+ "].");
			} else {
				// playerFaction.setRemainingRegenerationTime(BASE_REGEN_DELAY +
				// playerFaction.getOnlinePlayers().size() * TimeUnit.MINUTES.toMillis(2L));
				playerFaction.broadcast(ChatColor.YELLOW + "Member Death: " + ConfigurationService.TEAMMATE_COLOUR
						+ role.getAstrix() + player.getName() + ChatColor.YELLOW + ". DTR:" + ChatColor.GRAY + " ["
						+ playerFaction.getDtrColour()
						+ JavaUtils.format(Double.valueOf(playerFaction.getDeathsUntilRaidable())) + ChatColor.WHITE
						+ '/' + ChatColor.WHITE + playerFaction.getMaximumDeathsUntilRaidable() + ChatColor.GRAY
						+ "].");
			}
		}
		if (Bukkit.spigot().getTPS()[0] > 15.0D) {
			PlayerInventoryContents.put(player.getUniqueId(), player.getInventory().getContents());
			PlayerArmorContents.put(player.getUniqueId(), player.getInventory().getArmorContents());
			Location location = player.getLocation();
			((CraftWorld) location.getWorld()).getHandle();
			for (Player target : Bukkit.getOnlinePlayers()) {
				if (this.plugin.getUserManager().getUser(target.getUniqueId()).isShowLightning()) {
					target.playSound(target.getLocation(), Sound.AMBIENCE_THUNDER, 1.0F, 1.0F);
				}
			}
		}
	}
}
