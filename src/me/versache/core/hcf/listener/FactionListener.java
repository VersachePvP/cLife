package me.versache.core.hcf.listener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.google.common.base.Optional;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.event.FactionCreateEvent;
import me.versache.core.factions.factionsystem.event.FactionRemoveEvent;
import me.versache.core.factions.factionsystem.event.FactionRenameEvent;
import me.versache.core.factions.factionsystem.event.PlayerClaimEnterEvent;
import me.versache.core.factions.factionsystem.event.PlayerJoinFactionEvent;
import me.versache.core.factions.factionsystem.event.PlayerLeaveFactionEvent;
import me.versache.core.factions.factionsystem.event.PlayerLeftFactionEvent;
import me.versache.core.factions.factionsystem.struct.RegenStatus;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import me.versache.core.hcf.event.faction.KothFaction;
import net.md_5.bungee.api.ChatColor;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class FactionListener implements Listener {
	private static final long FACTION_JOIN_WAIT_MILLIS = TimeUnit.SECONDS.toMillis(30L);
	private static final String FACTION_JOIN_WAIT_WORDS = DurationFormatUtils
			.formatDurationWords(FACTION_JOIN_WAIT_MILLIS, true, true);
	private final Base plugin;

	public FactionListener(Base plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onFactionCreate(FactionCreateEvent event) {
		Faction faction = event.getFaction();
		if ((faction instanceof PlayerFaction)) {

			CommandSender sender = event.getSender();

			String prefix = PermissionsEx.getUser((Player) sender).getPrefix().replace("_", " ");
			Bukkit.broadcastMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME + ChatColor.GOLD + " » " + ConfigurationService.SECONDARY_COLOR + "The team " + ChatColor.GOLD + event.getFaction().getName() + ConfigurationService.SECONDARY_COLOR
					+ " has been " + ChatColor.GREEN + "created " +  ConfigurationService.SECONDARY_COLOR + "by " + ChatColor.translateAlternateColorCodes('&', prefix) + sender.getName() + ConfigurationService.SECONDARY_COLOR + ".");
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onFactionRemove(FactionRemoveEvent event) {
		Faction faction = event.getFaction();
		if ((faction instanceof PlayerFaction)) {
			CommandSender sender = event.getSender();

			String prefix = PermissionsEx.getUser((Player) sender).getPrefix().replace("_", " ");

			Bukkit.broadcastMessage(
					ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME + ChatColor.GOLD + " » " + ConfigurationService.SECONDARY_COLOR + "The team " + ChatColor.GOLD + event.getFaction().getName() + ConfigurationService.SECONDARY_COLOR + " has been " + ChatColor.RED + "disbanded " + ConfigurationService.SECONDARY_COLOR + "by "
							+ ChatColor.translateAlternateColorCodes('&', prefix) + sender.getName() + ConfigurationService.SECONDARY_COLOR + ".");
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onFactionRename(FactionRenameEvent event) {
		Faction faction = event.getFaction();

		if ((faction instanceof PlayerFaction)) {
			CommandSender sender = event.getSender();

			String prefix = PermissionsEx.getUser((Player) sender).getPrefix().replace("_", " ");

			Bukkit.broadcastMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME + ChatColor.GOLD + " » " + ConfigurationService.SECONDARY_COLOR + "The team " + ChatColor.GOLD + event.getOriginalName() + ConfigurationService.SECONDARY_COLOR + " has been " + ChatColor.GOLD + "renamed "  + ConfigurationService.SECONDARY_COLOR + "to "
					+ ChatColor.GOLD + "" + event.getNewName() + ConfigurationService.SECONDARY_COLOR + " by "
					+ ChatColor.translateAlternateColorCodes('&', prefix) + event.getSender().getName() + ConfigurationService.SECONDARY_COLOR + ".");
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onFactionRenameMonitor(FactionRenameEvent event) {
		Faction faction = event.getFaction();
		if ((faction instanceof KothFaction)) {
			((KothFaction) faction).getCaptureZone().setName(event.getNewName());
		}
	}

	private long getLastLandChangedMeta(Player player) {
		List<MetadataValue> value = player.getMetadata("landChangedMessage");
		long millis = System.currentTimeMillis();
		long remaining = (value == null) || (value.isEmpty()) ? 0L : ((MetadataValue) value.get(0)).asLong() - millis;
		if (remaining <= 0L) {
			player.setMetadata("landChangedMessage", new FixedMetadataValue(this.plugin, Long.valueOf(millis + 225L)));
		}
		return remaining;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onPlayerClaimEnter(PlayerClaimEnterEvent event) {
		Faction toFaction = event.getToFaction();
		Player player = event.getPlayer();

		if (toFaction.isSafezone()) {
			player.setFoodLevel(20);
			player.setFireTicks(0);
			player.setSaturation(4.0F);

//			if (toFaction instanceof SpawnFaction) {
//				SpawnFaction faction = (SpawnFaction)toFaction;
//				Claim primary = faction.getClaims().stream().findFirst().orElse(null);
//				
//				if(primary == null)
//					return;
//				
//				for (Player online : Bukkit.getOnlinePlayers()) {
//					if(!primary.contains(online))
//						continue;
//					
//					online.hidePlayer(player);
//					player.hidePlayer(player);
//				}
//			}
//		}
//		
			Faction fromFaction = event.getFromFaction();
//		
//		if(fromFaction instanceof SpawnFaction) {
//			for(Player online : Bukkit.getOnlinePlayers()) {
//				online.showPlayer(player);
//				player.showPlayer(online);
//			}
//		}

			if (getLastLandChangedMeta(player) <= 0L) {
				player.sendMessage(ChatColor.YELLOW + "Now leaving: " + fromFaction.getDisplayName(player)
						+ ChatColor.YELLOW + " ("
						+ (fromFaction.isDeathban() ? ChatColor.RED + "Deathban"
								: new StringBuilder().append(ChatColor.GREEN).append("Non-Deathban").toString())
						+ ChatColor.YELLOW + ')');
				player.sendMessage(
						ChatColor.YELLOW + "Now entering: " + toFaction.getDisplayName(player) + ChatColor.YELLOW + " ("
								+ (toFaction.isDeathban() ? ChatColor.RED + "Deathban"
										: new StringBuilder().append(ChatColor.GREEN).append("Non-Deathban").toString())
								+ ChatColor.YELLOW + ')');
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerLeftFaction(PlayerLeftFactionEvent event) {
		Optional<Player> optionalPlayer = event.getPlayer();
		if (optionalPlayer.isPresent()) {
			this.plugin.getUserManager().getUser(((Player) optionalPlayer.get()).getUniqueId())
					.setLastFactionLeaveMillis(System.currentTimeMillis());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerPreFactionJoin(PlayerJoinFactionEvent event) {
		Faction faction = event.getFaction();
		Optional<?> optionalPlayer = event.getPlayer();
		if (((faction instanceof PlayerFaction)) && (optionalPlayer.isPresent())) {
			Player player = (Player) optionalPlayer.get();
			PlayerFaction playerFaction = (PlayerFaction) faction;
			if ((!this.plugin.getEotwHandler().isEndOfTheWorld())
					&& (playerFaction.getRegenStatus() == RegenStatus.PAUSED)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot join factions that are not regenerating DTR.");
				return;
			}
			long difference = this.plugin.getUserManager().getUser(player.getUniqueId()).getLastFactionLeaveMillis()
					- System.currentTimeMillis() + FACTION_JOIN_WAIT_MILLIS;
			if ((difference > 0L) && (!player.hasPermission("hcf.faction.argument.staff.forcejoin"))) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You cannot join factions after just leaving within "
						+ FACTION_JOIN_WAIT_WORDS + ". " + "You gotta wait another "
						+ DurationFormatUtils.formatDurationWords(difference, true, true) + '.');
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onFactionLeave(PlayerLeaveFactionEvent event) {
		Faction faction = event.getFaction();
		if ((faction instanceof PlayerFaction)) {
			Optional<?> optional = event.getPlayer();
			if (optional.isPresent()) {
				Player player = (Player) optional.get();
				if (this.plugin.getFactionManager().getFactionAt(player.getLocation()).equals(faction)) {
					event.setCancelled(true);
					player.sendMessage(
							ChatColor.RED + "You cannot leave your faction whilst you remain in its' territory.");
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
		if (playerFaction != null) {
			playerFaction.printDetails(player);
			playerFaction.broadcast(
					ChatColor.GREEN + "Member Online: " + ChatColor.WHITE
							+ playerFaction.getMember(player).getRole().getAstrix() + player.getName(),
					new UUID[] { player.getUniqueId() });
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerFaction playerFaction = this.plugin.getFactionManager().getPlayerFaction(player);
		if (playerFaction != null) {
			playerFaction.broadcast(ChatColor.RED + "Member Offline: " + ChatColor.WHITE
					+ playerFaction.getMember(player).getRole().getAstrix() + player.getName());
		}
	}

}