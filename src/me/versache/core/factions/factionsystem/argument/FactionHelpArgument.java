package me.versache.core.factions.factionsystem.argument;

import com.google.common.collect.ArrayListMultimap; 
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import me.versache.core.ConfigurationService;
import me.versache.core.factions.factionsystem.FactionExecutor;
import me.versache.core.utilities.util.BukkitUtils;
import me.versache.core.utilities.util.JavaUtils;
import me.versache.core.utilities.util.chat.ClickAction;
import me.versache.core.utilities.util.chat.Text;
import me.versache.core.utilities.util.command.CommandArgument;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FactionHelpArgument extends CommandArgument {
    private static final int HELP_PER_PAGE = 10;
    private final FactionExecutor executor;
    private ImmutableMultimap<Integer, Text> pages;

    public FactionHelpArgument(final FactionExecutor executor) {
        super("help", "View help on how to use factions.");
        this.executor = executor;
        this.isPlayerOnly = true;
    }

    public String getUsage(final String label) {
        return '/' + label + ' ' + this.getName();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(args.length < 2) {
            this.showPage(sender, label, 1);
            return true;
        }
        final Integer page = JavaUtils.tryParseInt(args[1]);
        if(page == null) {
            sender.sendMessage(ChatColor.RED + "'" + args[1] + "' is not a valid number.");
            return true;
        }
        this.showPage(sender, label, page);
        return true;
    }

    private void showPage(final CommandSender sender, final String label, final int pageNumber) {
        if(this.pages == null) {
            final boolean isPlayer = sender instanceof Player;
            int val = 1;
            int count = 0;
            final Multimap<Integer, Text> pages = ArrayListMultimap.create();
            for(final CommandArgument argument : this.executor.getArguments()) {
                if(argument.equals((Object) this)) {
                    continue;
                }
                final String permission = argument.getPermission();
                if(permission != null && !sender.hasPermission(permission)) {
                    continue;
                }
                if(argument.isPlayerOnly() && !isPlayer) {
                    continue;
                }
                ++count;
                pages.get(val).add(new Text(ChatColor.BLUE + "   /" + label + ' ' + argument.getName() + ChatColor.BLUE + " > " + ChatColor.GRAY + argument.getDescription()).setColor(ChatColor.GOLD).setClick(ClickAction.SUGGEST_COMMAND, "/"+label +" "+argument.getName()));
                if(count % HELP_PER_PAGE != 0) {
                    continue;
                }
                ++val;
            }
            this.pages = ImmutableMultimap.copyOf(pages);
        }
        final int totalPageCount = this.pages.size() / HELP_PER_PAGE;
        if(pageNumber < 1) {
            sender.sendMessage(ChatColor.RED + "You cannot view a page less than 1.");
            return;
        }
        if(pageNumber > totalPageCount) {
            sender.sendMessage(ChatColor.RED + "There are only " + totalPageCount + " pages.");
            return;
        }
        if (pageNumber == 1) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString()  + " Faction Help " + ChatColor.GRAY + "(Page " + pageNumber + " out of " + totalPageCount + ')'));
            sender.sendMessage(C(""));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f focus " + "&6» " + ConfigurationService.SECONDARY_COLOR + "Focus a player in another faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f accept &6» " + ConfigurationService.SECONDARY_COLOR + "Accept a join request from an existing faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f ally &6» " + ConfigurationService.SECONDARY_COLOR + "Make an ally pact with other factions."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f announcement &6» " + ConfigurationService.SECONDARY_COLOR + "Set your faction announcement."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f chat &6» " + ConfigurationService.SECONDARY_COLOR + "Toggle faction chat only mode on or off."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f claim &6» " + ConfigurationService.SECONDARY_COLOR + "Claim land in the Wilderness."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f claimchunk &6» " + ConfigurationService.SECONDARY_COLOR + "Claim a chunk of land in the Wilderness."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f claims &6» " + ConfigurationService.SECONDARY_COLOR + "View all claims for a faction."));
            sender.sendMessage(C(""));
            sender.sendMessage(ChatColor.GOLD + "»" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + " Next Page.");
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
        }
        if (pageNumber == 2) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
            sender.sendMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString()  + " Faction Help " + ChatColor.GRAY + "(Page " + pageNumber + " out of " + totalPageCount + ')');
            sender.sendMessage(C(""));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f create &6» " + ConfigurationService.SECONDARY_COLOR + "Create a faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f coleader &6» " + ConfigurationService.SECONDARY_COLOR + "S."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f demote &6» " + ConfigurationService.SECONDARY_COLOR + "Make an ally pact with other factions."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f deposit &6» " + ConfigurationService.SECONDARY_COLOR + "Set your faction announcement."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f disband &6» " + ConfigurationService.SECONDARY_COLOR + "Disband your faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f home &6» " + ConfigurationService.SECONDARY_COLOR + "Teleport to the faction home."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f invite &6» " + ConfigurationService.SECONDARY_COLOR + "Invite a player to the faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f invites &6» " + ConfigurationService.SECONDARY_COLOR + "View faction invitations."));
            sender.sendMessage(C(""));
            sender.sendMessage(ChatColor.GOLD + "»" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + " Next Page.");
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
        }
        if (pageNumber == 3) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
            sender.sendMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString()  + " Faction Help " + ChatColor.GRAY + "(Page " + pageNumber + " out of " + totalPageCount + ')');
            sender.sendMessage(C(""));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f kick &6» " + ConfigurationService.SECONDARY_COLOR + "Kick a player from the faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f leader &6» " + ConfigurationService.SECONDARY_COLOR + "Sets the new leader for your faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f leave &6» " + ConfigurationService.SECONDARY_COLOR + "Leave your current faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f list &6» " + ConfigurationService.SECONDARY_COLOR + "See a list of all factions"));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f map &6» " + ConfigurationService.SECONDARY_COLOR + "View all claims around your chunk."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f message &6» " + ConfigurationService.SECONDARY_COLOR + "Sends a message to your faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f open &6» " + ConfigurationService.SECONDARY_COLOR + "Opens the faction to the public."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f uninvite &6» " + ConfigurationService.SECONDARY_COLOR + "Revokes a players invitation."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f mute &6» " + ConfigurationService.SECONDARY_COLOR + "Mute a factions members."));
            sender.sendMessage(C(""));
            sender.sendMessage(ChatColor.GOLD + "»" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + " Next Page.");
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
        }
        if (pageNumber == 4) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
            sender.sendMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString()  + " Faction Help " + ChatColor.GRAY + "(Page " + pageNumber + " out of " + totalPageCount + ')');
            sender.sendMessage(C(""));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f rename &6» " + ConfigurationService.SECONDARY_COLOR + "Change your factions name."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f promote &6» " + ConfigurationService.SECONDARY_COLOR + "Promotes a player to captain."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f sethome &6» " + ConfigurationService.SECONDARY_COLOR + "Sets the faction home location."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f show &6» " + ConfigurationService.SECONDARY_COLOR + "Get details about a faction"));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f stuck &6» " + ConfigurationService.SECONDARY_COLOR + "Teleport to a safe position."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f unclaim &6» " + ConfigurationService.SECONDARY_COLOR + "Unclaims land from your faction."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f unally &6» " + ConfigurationService.SECONDARY_COLOR + "Removes an ally pact with other factions."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f withdraw &6» " + ConfigurationService.SECONDARY_COLOR + "Withdraws money from the faction balance."));
            sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f unmute &6» " + ConfigurationService.SECONDARY_COLOR + "Unmute a factions members"));
            sender.sendMessage(C(""));
            sender.sendMessage(ChatColor.GOLD + "»" + ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString() + " Next Page.");
            sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
        }
    if (pageNumber == 5) {
        sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
        sender.sendMessage(ConfigurationService.PRIMARY_COLOR + ChatColor.BOLD.toString()  + " Faction Help " + ChatColor.GRAY + "(Page " + pageNumber + " out of " + totalPageCount + ')');
        sender.sendMessage(C(""));
        sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f manage &6» " + ConfigurationService.SECONDARY_COLOR + "Manages your whole faction."));
        sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f build &6» " + ConfigurationService.SECONDARY_COLOR + "Builds a box around your base for free."));
        sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f top &6» " + ConfigurationService.SECONDARY_COLOR + "Shows the top factions through out the whole server."));
        sender.sendMessage(C(ConfigurationService.PRIMARY_COLOR + " /f version &6» " + ConfigurationService.SECONDARY_COLOR + "Shows the author of this core."));
        sender.sendMessage(C(""));
        sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.RED + "You are in the final page.");
        sender.sendMessage(ChatColor.GOLD + ChatColor.STRIKETHROUGH.toString() + "--*---------------------------------*--");
    }
        /*/sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
        sender.sendMessage(ChatColor.YELLOW  + " Faction Help " + ChatColor.WHITE + "(" + pageNumber + '/' + totalPageCount + ')');
        for(final Text message : this.pages.get(pageNumber)) {
            message.send(sender);
        }
        sender.sendMessage(ChatColor.YELLOW + " To view other pages, use " + ChatColor.YELLOW + '/' + label + ' ' + this.getName() + " <#>" );
        if(pageNumber == 1){
        }
        sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);/*/
    }
    
    public String C (String string) {
    	String message = ChatColor.translateAlternateColorCodes('&', string);
    	return message;
    }
}
