package me.versache.core.hcf.command;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.versache.core.Base;
import me.versache.core.ConfigurationService;
import me.versache.core.utilities.random.BaseCommand;
import me.versache.core.utilities.random.BaseConstants;
import me.versache.core.utilities.random.StaffPriority;
import me.versache.core.utilities.user.FactionUser;
import me.versache.core.utilities.user.UserManager;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.chat.Text;

public class WhoisCommand extends BaseCommand {
	
    private final Base plugin;

    public WhoisCommand(final Base plugin) {
        super("whois", "Check information about a player.");
        this.plugin = plugin;
        this.setUsage("/(command) [playerName]");
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(args.length < 1) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        final Player target = BukkitUtils.playerWithNameOrUUID(args[0]);
        if(target == null || !BaseCommand.canSee(sender, target)) {
            sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
            return true;
        }
        final Location location = target.getLocation();
        final World world = location.getWorld();
        sender.sendMessage(ChatColor.GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
        sender.sendMessage(ConfigurationService.SECONDARY_COLOR + " [" + ConfigurationService.PRIMARY_COLOR + target.getDisplayName() + ConfigurationService.SECONDARY_COLOR + ']');
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  Hunger: " + ConfigurationService.SECONDARY_COLOR + target.getFoodLevel() + '/' + 20 + " (" + target.getSaturation() + " saturation)");
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  Exp/Level: " + ConfigurationService.SECONDARY_COLOR + target.getExp() + '/' + target.getLevel());
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  Location: " + ConfigurationService.SECONDARY_COLOR + world.getName() + ' ' + ConfigurationService.SECONDARY_COLOR + '[' + WordUtils.capitalizeFully(world.getEnvironment().name().replace('_', ' ')) + "] " + ConfigurationService.SECONDARY_COLOR + '(' + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ')');
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  Operator: " + ConfigurationService.SECONDARY_COLOR + target.isOp());
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  GameMode: " + ConfigurationService.SECONDARY_COLOR + WordUtils.capitalizeFully(target.getGameMode().name().replace('_', ' ')));
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + "  Idle Time: " + ConfigurationService.SECONDARY_COLOR + DurationFormatUtils.formatDurationWords(BukkitUtils.getIdleTime(target), true, true));
   //     sender.sendMessage(ChatColor.AQUA + "  IP4 Address: " + ConfigurationService.SECONDARY_COLOR + (sender.hasPermission(command.getPermission() + ".ip") ? target.getAddress().getHostString(): ChatColor.STRIKETHROUGH + "1.1.1.1" ));
        sender.sendMessage(ChatColor.GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return (args.length == 1) ? null : Collections.emptyList();
    }
}
