package me.versache.core.hcf.scoreboard.provider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import me.versache.core.hcf.classes.PvpClass;
import me.versache.core.hcf.classes.archer.ArcherClass;
import me.versache.core.hcf.classes.bard.BardClass;
import me.versache.core.hcf.classes.type.MinerClass;
import me.versache.core.hcf.classes.type.RogueClass;
import me.versache.core.hcf.command.CobbleCommand;
import me.versache.core.hcf.command.FreezeCommand;
import me.versache.core.hcf.command.StaffModeCommand;
import me.versache.core.hcf.event.EventTimer;
import me.versache.core.hcf.event.eotw.EOTWHandler;
import me.versache.core.hcf.event.faction.ConquestFaction;
import me.versache.core.hcf.event.faction.EventFaction;
import me.versache.core.hcf.event.faction.KothFaction;
import me.versache.core.hcf.event.tracker.ConquestTracker;
import me.versache.core.hcf.listener.VanishListener;
import me.versache.core.hcf.scoreboard.SidebarEntry;
import me.versache.core.hcf.scoreboard.SidebarProvider;
import me.versache.core.hcf.timer.GlobalTimer;
import me.versache.core.hcf.timer.PlayerTimer;
import me.versache.core.hcf.timer.Timer;
import me.versache.core.hcf.timer.type.TeleportTimer;
import me.versache.core.special.sotw.SotwTimer;
import me.versache.core.utilities.random.Cooldowns;
import me.versache.core.utilities.random.DateTimeFormats;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.DurationFormatter;

public class TimerSidebarProvider implements SidebarProvider {

	protected static String STRAIGHT_LINE = BukkitUtils.STRAIGHT_LINE_DEFAULT.substring(0, 16);
	protected static final String NEW_LINE = ChatColor.STRIKETHROUGH + "-*--------";

	private Base plugin;

	public TimerSidebarProvider(Base plugin) {
		this.plugin = plugin;
	}

	private static String handleBardFormat(long millis, boolean trailingZero) {
		return ((DecimalFormat) (trailingZero ? DateTimeFormats.REMAINING_SECONDS_TRAILING
				: DateTimeFormats.REMAINING_SECONDS).get()).format(millis * 0.001D);
	}

	public SidebarEntry add(String s) {

		if (s.length() < 10) {
			return new SidebarEntry(s);
		}

		if (s.length() > 10 && s.length() < 20) {
			return new SidebarEntry(s.substring(0, 10), s.substring(10, s.length()), "");
		}

		if (s.length() > 20) {
			return new SidebarEntry(s.substring(0, 10), s.substring(10, 20), s.substring(20, s.length()));
		}

		return null;
	}

	@Override
	public String getTitle() {
		return ConfigurationService.SCOREBOARD_NAME.replace("%straight_line%", "\u258f");
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public List<SidebarEntry> getLines(Player player) {

		List<SidebarEntry> lines = new ArrayList<SidebarEntry>();
		EOTWHandler.EotwRunnable eotwRunnable = this.plugin.getEotwHandler().getRunnable();
		PvpClass pvpClass = this.plugin.getPvpClassManager().getEquippedClass(player);
		EventTimer eventTimer = this.plugin.getTimerManager().eventTimer;
		List<SidebarEntry> conquestLines = null;
		Collection<Timer> timers = this.plugin.getTimerManager().getTimers();
		EventFaction eventFaction = eventTimer.getEventFaction();
		
		
		if ((StaffModeCommand.getInstance().isMod(player))) {
            double tps = Bukkit.spigot().getTPS()[0];
			lines.add(new SidebarEntry(ChatColor.GOLD + ChatColor.BOLD.toString() + "Staff Mode"));
			lines.add(new SidebarEntry(ChatColor.GOLD + " » ", ChatColor.YELLOW + "Vanish" + ChatColor.GRAY + ": ",
					VanishListener.getInstance().isVanished(player) ? ChatColor.GREEN + "True" : ChatColor.RED + "False"));
			lines.add(new SidebarEntry("§6 » §eOnline", "§7: ", "§f" + Bukkit.getOnlinePlayers().length));
	          lines.add(new SidebarEntry(ChatColor.GOLD.toString() + " »" + ChatColor.YELLOW.toString(), " TPS§7: ", ChatColor.WHITE.toString() + Math.round(tps * 10.0D) / 10.0D));    
	          
	          ConfigurationService.KIT_MAP = true; {
	          lines.add(new SidebarEntry("§7§m-*-", "--------", "-------*-"));
        }
		}
	          

		

	
		
		if ((ConfigurationService.KIT_MAP) == true) {
			lines.add(this.add((Base.getPlugin().getConfig().getString("Scoreboard.Title.Name")).replaceAll("&", "§")));
			if(Base.getPlugin().getFactionManager().getFactionAt(player.getLocation()).getName().equals("Spawn")) {
			lines.add(this.add((Base.getPlugin().getConfig().getString("Scoreboard.Kills.Name").replace("%kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)))).replaceAll("&", "§")));
			lines.add(this.add(Base.getPlugin().getConfig().getString("Scoreboard.Deaths.Name").replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS))).replaceAll("&", "§")));
			}
			lines.add(this.add(Base.getPlugin().getConfig().getString("Scoreboard.Ping.Name").replace("%ping%", String.valueOf(((CraftPlayer) player).getHandle().ping)).replaceAll("&", "§")));
		}
		
    final EventTimer eventTimer1 = this.plugin.getTimerManager().getEventTimer();
    List<SidebarEntry> conquestLines1 = null;
    final EventFaction eventFaction1 = eventTimer1.getEventFaction();
    if (eventFaction1 instanceof KothFaction) {
        lines.add(new SidebarEntry(eventTimer1.getScoreboardPrefix(), String.valueOf(eventFaction1.getScoreboardName()) + ChatColor.GRAY, " » " + ChatColor.GREEN + DurationFormatter.getRemaining(eventTimer1.getRemaining(), true)));
        lines.add(new SidebarEntry(ChatColor.GRAY, "/f show " + eventTimer1.getScoreboardPrefix(), String.valueOf(eventFaction1.getScoreboardName())));
	}
   

	if ((pvpClass instanceof ArcherClass)) {
		lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.YELLOW, "Active Class",
				ChatColor.GRAY + ": " + ChatColor.WHITE + "Archer"));
		if (Cooldowns.isOnCooldown("Archer_item_cooldown", player)) {
			lines.add(new SidebarEntry(ChatColor.GREEN + ChatColor.BOLD.toString(), ChatColor.GREEN + ChatColor.BOLD.toString() + "Buff Delay", ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(Cooldowns.getCooldownForPlayerLong("Archer_item_cooldown", player), true)));
		}
	}
	

	if ((pvpClass instanceof BardClass)) {
		lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.YELLOW, "Active Class",
				ChatColor.GRAY + ": " + ChatColor.WHITE + "Bard"));
	}

	if ((pvpClass instanceof RogueClass)) {
		lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.YELLOW, "Active Class",
				ChatColor.GRAY + ": " + ChatColor.WHITE + "Rouge"));
	}

		if ((pvpClass != null) && ((pvpClass instanceof BardClass))) {
			BardClass bardClass = (BardClass) pvpClass;
			lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.YELLOW, "Active Class",
					ChatColor.GRAY + ": " + ChatColor.WHITE + "Bard"));
			lines.add(new SidebarEntry(ChatColor.AQUA + ChatColor.GOLD.toString() + " » " + ChatColor.YELLOW + "Bard ",
					ChatColor.YELLOW + ChatColor.YELLOW.toString() + "Energy", ChatColor.GRAY + ": " + ChatColor.RED
							+ handleBardFormat(bardClass.getEnergyMillis(player), true)));
			long remaining2 = bardClass.getRemainingBuffDelay(player);
			if (remaining2 > 0L) {
				lines.add(new SidebarEntry(ChatColor.GREEN + ChatColor.BOLD.toString() + "Bard ",
						ChatColor.GREEN + ChatColor.BOLD.toString() + "Effect",
						ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining2, true)));
			}
		}
		final SotwTimer.SotwRunnable sotwRunnable = this.plugin.getSotwTimer().getSotwRunnable();
		if (sotwRunnable != null) {
			lines.add(new SidebarEntry(String.valueOf(ChatColor.GREEN.toString()) + ChatColor.BOLD, "SOTW Timer", ChatColor.GRAY + ": "));
			lines.add(new SidebarEntry(String.valueOf(ChatColor.GRAY.toString()), " \u27b8 §aTimer", ChatColor.GRAY + ": " + String.valueOf(ChatColor.RED.toString()) + Base.getRemaining(sotwRunnable.getRemaining(), true)));
		}
		
		if (FreezeCommand.frozen.contains(player.getName())) {
		}

		if ((pvpClass instanceof MinerClass)) {
			lines.add(new SidebarEntry(ChatColor.YELLOW.toString() + ChatColor.YELLOW, "Active Class",
					ChatColor.GRAY + ": " + ChatColor.WHITE + "Miner"));
            lines.add(new SidebarEntry(ChatColor.GOLD + "»" + ChatColor.YELLOW, " Diamonds", ChatColor.GRAY + ": " + ChatColor.AQUA + player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE)));
        if (CobbleCommand.disabled.contains(player)) {
            lines.add(new SidebarEntry(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Cobble", ChatColor.GRAY + ": " + ChatColor.GREEN + "True", ""));
        }
        else {
            lines.add(new SidebarEntry(ChatColor.GOLD + "» " + ChatColor.YELLOW + "Cobble", ChatColor.GRAY + ": " + ChatColor.RED + "False", ""));       	
        }
		}

		

		for (Timer timer : timers) {
			if (((timer instanceof PlayerTimer)) && (!(timer instanceof TeleportTimer))) {
				PlayerTimer playerTimer = (PlayerTimer) timer;
				long remaining3 = playerTimer.getRemaining(player);
				if (remaining3 > 0L) {
					String timerName1 = playerTimer.getName();
					if (timerName1.length() > 14) {
						timerName1 = timerName1.substring(0, timerName1.length());
					}
					lines.add(new SidebarEntry(playerTimer.getScoreboardPrefix(), timerName1,
					 (ChatColor.translateAlternateColorCodes('&', Base.getPlugin().getConfig().getString("Scoreboard.Timers.Color")) + Base.getRemaining(remaining3, true))));
				}
			} else if ((timer instanceof GlobalTimer)) {
				GlobalTimer playerTimer2 = (GlobalTimer) timer;
				long remaining3 = playerTimer2.getRemaining();
				if (remaining3 > 0L) {
					String timerName = playerTimer2.getName();
					if (timerName.length() > 14) {
						timerName = timerName.substring(0, timerName.length());
					}
					if (!timerName.equalsIgnoreCase("Conquest")) {
						lines.add(new SidebarEntry(playerTimer2.getScoreboardPrefix(), timerName + ChatColor.GRAY,
								": " + ChatColor.WHITE + Base.getRemaining(remaining3, true)));
					}
				}
			}
		}

		if (eotwRunnable != null) {
			long remaining4 = eotwRunnable.getTimeUntilStarting();
			if (remaining4 > 0L) {
				lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GRAY + "",
						"" + ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining4, true)));
			} else if ((remaining4 = eotwRunnable.getTimeUntilCappable()) > 0L) {
				lines.add(new SidebarEntry(ChatColor.DARK_RED.toString() + ChatColor.BOLD, "EOTW" + ChatColor.GRAY + "",
						"" + ChatColor.GRAY + ": " + ChatColor.RED + Base.getRemaining(remaining4, true)));
			}
		}
	


		if ((eventFaction1 instanceof ConquestFaction)) {
			ConquestFaction conquestFaction = (ConquestFaction) eventFaction1;
			CONQUEST_FORMATTER.get();
			conquestLines1 = new ArrayList<SidebarEntry>();
			ConquestTracker conquestTracker = (ConquestTracker) conquestFaction.getEventType().getEventTracker();
			int count = 0;
			for (Iterator<?> localIterator = conquestTracker.getFactionPointsMap().entrySet().iterator(); localIterator
					.hasNext(); count = 3) {
				Map.Entry<PlayerFaction, Integer> entry = (Map.Entry) localIterator.next();
				String factionName = ((PlayerFaction) entry.getKey()).getDisplayName(player);
				if (factionName.length() > 14) {
					factionName = factionName.substring(0, 14);
				}
				lines.add(new SidebarEntry(ChatColor.WHITE + " * " + ChatColor.RED, factionName,
						ChatColor.GRAY + ": " + ChatColor.WHITE + entry.getValue()));
				if (++count == 3) {
					break;
				}
			}
		}

		if ((conquestLines1 != null) && (!conquestLines1.isEmpty())) {
			if (player.hasPermission("command.mod")) {
				conquestLines1.add(new SidebarEntry("§7§m------", "------", "--------"));
			}
			conquestLines1.addAll(lines);
			lines = conquestLines1;
			
		}
		if (!lines.isEmpty()) {
			lines.add(0, new SidebarEntry(ChatColor.GRAY, ChatColor.STRIKETHROUGH + "-*---------", "-------*-"));
			lines.add(lines.size(), new SidebarEntry(ChatColor.GRAY, NEW_LINE, "--------*-"));
		}
		return lines;

	}

	private String getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public static final ThreadLocal<DecimalFormat> CONQUEST_FORMATTER = new ThreadLocal<DecimalFormat>() {
		protected DecimalFormat initialValue() {
			return new DecimalFormat("00.0");
		}
	};
}
