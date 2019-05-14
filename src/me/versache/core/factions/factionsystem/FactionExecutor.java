package me.versache.core.factions.factionsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.versache.core.Base;
import me.versache.core.factions.factionsystem.argument.FactionAcceptArgument;
import me.versache.core.factions.factionsystem.argument.FactionAllyArgument;
import me.versache.core.factions.factionsystem.argument.FactionAnnouncementArgument;
import me.versache.core.factions.factionsystem.argument.FactionChatArgument;
import me.versache.core.factions.factionsystem.argument.FactionClaimArgument;
import me.versache.core.factions.factionsystem.argument.FactionClaimsArgument;
import me.versache.core.factions.factionsystem.argument.FactionCoLeaderArgument;
import me.versache.core.factions.factionsystem.argument.FactionCreateArgument;
import me.versache.core.factions.factionsystem.argument.FactionDemoteArgument;
import me.versache.core.factions.factionsystem.argument.FactionDepositArgument;
import me.versache.core.factions.factionsystem.argument.FactionDisbandArgument;
import me.versache.core.factions.factionsystem.argument.FactionHelpArgument;
import me.versache.core.factions.factionsystem.argument.FactionHomeArgument;
import me.versache.core.factions.factionsystem.argument.FactionInviteArgument;
import me.versache.core.factions.factionsystem.argument.FactionInvitesArgument;
import me.versache.core.factions.factionsystem.argument.FactionKickArgument;
import me.versache.core.factions.factionsystem.argument.FactionLeaderArgument;
import me.versache.core.factions.factionsystem.argument.FactionLeaveArgument;
import me.versache.core.factions.factionsystem.argument.FactionListArgument;
import me.versache.core.factions.factionsystem.argument.FactionManagerArgument;
import me.versache.core.factions.factionsystem.argument.FactionMapArgument;
import me.versache.core.factions.factionsystem.argument.FactionMessageArgument;
import me.versache.core.factions.factionsystem.argument.FactionOpenArgument;
import me.versache.core.factions.factionsystem.argument.FactionPromoteArgument;
import me.versache.core.factions.factionsystem.argument.FactionRenameArgument;
import me.versache.core.factions.factionsystem.argument.FactionSetHomeArgument;
import me.versache.core.factions.factionsystem.argument.FactionShowArgument;
import me.versache.core.factions.factionsystem.argument.FactionStuckArgument;
import me.versache.core.factions.factionsystem.argument.FactionUnallyArgument;
import me.versache.core.factions.factionsystem.argument.FactionUnclaimArgument;
import me.versache.core.factions.factionsystem.argument.FactionUninviteArgument;
import me.versache.core.factions.factionsystem.argument.FactionVersionArgument;
import me.versache.core.factions.factionsystem.argument.FactionWithdrawArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionChatSpyArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionClaimForArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionClearClaimsArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionForceJoinArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionForceLeaderArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionForcePromoteArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionLockArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionMuteArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionRemoveArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionSetDeathbanMultiplierArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionSetDtrArgument;
import me.versache.core.factions.factionsystem.argument.staff.FactionSetDtrRegenArgument;
import me.versache.core.utilities.util.command.ArgumentExecutor;
import me.versache.core.utilities.util.command.CommandArgument;


public class FactionExecutor extends ArgumentExecutor {
    private final CommandArgument helpArgument;

    public FactionExecutor(final Base plugin) {
        super("faction");
        this.addArgument(new FactionLockArgument(plugin));
        this.addArgument(new FactionCoLeaderArgument(plugin));
        this.addArgument(new FactionAcceptArgument(plugin));
        this.addArgument(new FactionAllyArgument(plugin));
        this.addArgument(new FactionChatArgument(plugin));
        this.addArgument(new FactionChatSpyArgument(plugin));
        this.addArgument(new FactionClaimArgument(plugin));
        addArgument(new FactionClaimForArgument(plugin));
        this.addArgument(new FactionClaimsArgument(plugin));
        this.addArgument(new FactionClearClaimsArgument(plugin));
        this.addArgument(new FactionCreateArgument(plugin));
        this.addArgument(new FactionAnnouncementArgument(plugin));
        this.addArgument(new FactionDemoteArgument(plugin));
        this.addArgument(new FactionDisbandArgument(plugin));
        this.addArgument(new FactionSetDtrRegenArgument(plugin));
        addArgument(new FactionDepositArgument(plugin));
        addArgument(new FactionWithdrawArgument(plugin));
        this.addArgument(new FactionForceJoinArgument(plugin));
        this.addArgument(new FactionForceLeaderArgument(plugin));
        this.addArgument(new FactionMuteArgument(plugin));
        this.addArgument(new FactionForcePromoteArgument(plugin));
        this.addArgument(this.helpArgument = new FactionHelpArgument(this));
        this.addArgument(new FactionHomeArgument(this, plugin));
        this.addArgument(new FactionInviteArgument(plugin));
        this.addArgument(new FactionInvitesArgument(plugin));
        this.addArgument(new FactionKickArgument(plugin));
        this.addArgument(new FactionLeaderArgument(plugin));
        this.addArgument(new FactionLeaveArgument(plugin));
        this.addArgument(new FactionListArgument(plugin));
        this.addArgument(new FactionMapArgument(plugin));
        this.addArgument(new FactionMessageArgument(plugin));
        this.addArgument(new FactionOpenArgument(plugin));
        this.addArgument(new FactionRemoveArgument(plugin));
        this.addArgument(new FactionRenameArgument(plugin));
        this.addArgument(new FactionPromoteArgument(plugin));
        this.addArgument(new FactionSetDtrArgument(plugin));
        this.addArgument(new FactionSetDeathbanMultiplierArgument(plugin));
        this.addArgument(new FactionSetHomeArgument(plugin));
        this.addArgument(new FactionShowArgument(plugin));
        this.addArgument(new FactionStuckArgument(plugin));
        this.addArgument(new FactionUnclaimArgument(plugin));
        this.addArgument(new FactionVersionArgument(plugin));
        this.addArgument(new FactionUnallyArgument(plugin));
        this.addArgument(new FactionUninviteArgument(plugin));
        this.addArgument(new FactionManagerArgument(plugin));
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if(args.length < 1) {
            this.helpArgument.onCommand(sender, command, label, args);
            return true;
        }
        final CommandArgument argument = this.getArgument(args[0]);
        if(argument != null) {
            final String permission = argument.getPermission();
            if(permission == null || sender.hasPermission(permission)) {
                argument.onCommand(sender, command, label, args);
                return true;
            }
        }
        this.helpArgument.onCommand(sender, command, label, args);
        return true;
    }


}
