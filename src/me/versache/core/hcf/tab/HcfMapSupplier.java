package me.versache.core.hcf.tab;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.FactionMember;
import me.versache.core.factions.factionsystem.type.Faction;
import me.versache.core.factions.factionsystem.type.PlayerFaction;
import me.versache.core.hcf.event.EventTimer;
import me.versache.core.hcf.event.faction.KothFaction;
import me.vertises.aztec.tablist.TablistEntrySupplier;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;

public class HcfMapSupplier implements TablistEntrySupplier {

    public static final DecimalFormat FORMAT = new DecimalFormat("0.00");

    public static final Comparator<PlayerFaction> ONLINE_MEMBER_COMPARATOR = (faction1, faction2) -> {
        return 0;
    };

    public static final Comparator<FactionMember> ROLE_COMPARATOR = (member1, member2) -> {
        return Integer.compare(member1.getRole().ordinal(), member2.getRole().ordinal());
    };

    private final Base plugin;

    public HcfMapSupplier(Base plugin) {
        this.plugin = plugin;
    }

    @Override
    public Table<Integer, Integer, String> getEntries(Player player) {
        Table<Integer, Integer, String> entries = HashBasedTable.create();
        PlayerFaction faction = plugin.getFactionManager().getPlayerFaction(player);
        Location location = player.getLocation();
        String direction = getCardinalDirection(player);
        Faction factionAt = plugin.getFactionManager().getFactionAt(location);
        entries.put(1, 0, ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + ConfigurationService.NAME );

        //Player info shit
        entries.put(0, 0, ConfigurationService.PRIMARY_COLOR + "Player Info");
        entries.put(0, 1, ConfigurationService.THIRD_COLOR + "Kills: " + ConfigurationService.SECONDARY_COLOR + player.getStatistic(Statistic.PLAYER_KILLS));
        entries.put(0, 2, ConfigurationService.THIRD_COLOR + "Deaths: " + ConfigurationService.SECONDARY_COLOR + player.getStatistic(Statistic.DEATHS));

        entries.put(0, 4, ConfigurationService.PRIMARY_COLOR + "Your Location");
        entries.put(0, 5, factionAt.getDisplayName(player));
        entries.put(0, 6, ConfigurationService.SECONDARY_COLOR + "(" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockZ() + ")" + "[" + direction + "]");

        entries.put(1, 1, ConfigurationService.PRIMARY_COLOR + "Players Online");
        entries.put(1, 2, ConfigurationService.SECONDARY_COLOR + "" + Bukkit.getOnlinePlayers().length + ConfigurationService.THIRD_COLOR + "/"  +  ConfigurationService.SECONDARY_COLOR + Bukkit.getMaxPlayers());

        //Faction Shit
        entries.put(0, 8, ConfigurationService.PRIMARY_COLOR + "Faction Info");
        entries.put(0, 9, ConfigurationService.SECONDARY_COLOR + "You do not");
        entries.put(0, 10, ConfigurationService.SECONDARY_COLOR + "a faction");
        entries.put(0, 11, ConfigurationService.SECONDARY_COLOR + "/f create <name>");
        entries.put(0, 12, ConfigurationService.SECONDARY_COLOR+ "to get started!");
        if (faction != null) {
            entries.put(0, 8, ConfigurationService.PRIMARY_COLOR + "Faction Info ");
            entries.put(0, 9, ConfigurationService.PRIMARY_COLOR + "Name: " + ChatColor.GREEN + faction.getName());
            entries.put(0, 10, ConfigurationService.PRIMARY_COLOR + "Online: " + ConfigurationService.SECONDARY_COLOR + faction.getOnlineMembers().size() + "/" + faction.getMembers().size());
            entries.put(0, 11, ConfigurationService.PRIMARY_COLOR + "Home: " + ConfigurationService.SECONDARY_COLOR + (faction.getHome() == null ? "Not set" : (faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ())));
            entries.put(0, 12, ConfigurationService.PRIMARY_COLOR + "DTR: " + ConfigurationService.SECONDARY_COLOR + (faction.getDeathsUntilRaidable()));
            FactionMember[] members = (FactionMember[]) faction.getOnlineMembers().values().toArray(new FactionMember[0]);
            Arrays.sort(members, ROLE_COMPARATOR);
            for (int i = 0; i < 18; i ++) {
                if (members.length <= i) continue;
                FactionMember member = members[i];
                entries.put(1, 4, ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + faction.getName());
                String name = ConfigurationService.THIRD_COLOR + member.getRole().getAstrix() + ChatColor.GREEN + member.getName();
                entries.put(1, 5 + i, name);
            }
        }
        entries.put(2, 0, ConfigurationService.PRIMARY_COLOR + "Team List");
        List<PlayerFaction> factions = plugin.getFactionManager().getFactions().stream().filter(x -> x instanceof PlayerFaction).map(x -> (PlayerFaction) x).filter(x -> x.getOnlineMembers().size() > 0).collect(Collectors.toList());
        Collections.sort(factions, ONLINE_MEMBER_COMPARATOR); //sort factions by online members
        Collections.reverse(factions); //reverse order so higher goes to lower
        for (int i = 0; i < 18; i ++) {
            if (factions.size() <= i) continue;
            PlayerFaction next = factions.get(i);
            String name = next.getDisplayName(player) + ConfigurationService.SECONDARY_COLOR + " (" + next.getOnlineMembers().size() + ")";
            entries.put(2, i + 1, name);
        } if (faction == null) {
            entries.put(2, 0, ConfigurationService.PRIMARY_COLOR + "Factions List");
            entries.put(2, 1, ConfigurationService.SECONDARY_COLOR.toString() + ChatColor.ITALIC + "None Online");
        }
        entries.put(3, 4, ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() +
                "WARNING " + ChatColor.RED + "- ");
        entries.put(3, 5, "Join on 1.7 for");
        entries.put(3, 6, "the best gameplay");
        entries.put(3, 7, "experiance");


        String[] kothInfo = {"Next KOTH", "Not scheduled"};
        EventTimer timer = plugin.getTimerManager().getEventTimer();
        if (timer.getEventFaction() instanceof KothFaction) {
            KothFaction kothFaction = (KothFaction) timer.getEventFaction();
            Location center = kothFaction.getCaptureZone().getCuboid().getCenter();
            kothInfo = new String[] {timer.getEventFaction().getName(), DurationFormatUtils.formatDuration(timer.getRemaining(), "mm:ss"), (center.getBlockX() + ", " + center.getBlockZ())};
        }
        for (int i = 0; i < kothInfo.length; i ++) {
            String next = (i == 0 ? ConfigurationService.PRIMARY_COLOR : ConfigurationService.SECONDARY_COLOR) + kothInfo[i];
            entries.put(0,14 + i, next);
        }
        return entries;
    }

    private String getCardinalDirection(Player player)
    {
        double rot = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rot < 0.0D) {
            rot += 360.0D;
        }
        return getDirection(rot);
    }

    private String getDirection(double rot)
    {
        if ((0.0D <= rot) && (rot < 22.5D)) {
            return "W";
        }
        if ((22.5D <= rot) && (rot < 67.5D)) {
            return "NW";
        }
        if ((67.5D <= rot) && (rot < 112.5D)) {
            return "N";
        }
        if ((112.5D <= rot) && (rot < 157.5D)) {
            return "NE";
        }
        if ((157.5D <= rot) && (rot < 202.5D)) {
            return "E";
        }
        if ((202.5D <= rot) && (rot < 247.5D)) {
            return "SE";
        }
        if ((247.5D <= rot) && (rot < 292.5D)) {
            return "S";
        }
        if ((292.5D <= rot) && (rot < 337.5D)) {
            return "SW";
        }
        if ((337.5D <= rot) && (rot < 360.0D)) {
            return "W";
        }
        return null;
    }

    @Override
    public String getHeader(Player player) {
        return ConfigurationService.TAB_HEADER;
    }

    @Override
    public String getFooter(Player player) {
        return "";
    }
}