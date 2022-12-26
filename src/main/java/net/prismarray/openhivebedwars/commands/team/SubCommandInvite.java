package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SubCommandInvite extends PluginBoundCommandExecutor {

    public SubCommandInvite(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TeamHandler teamHandler = plugin.game.getTeamHandler();

        String inviteeName = args[0]; // guaranteed to be legal due to AdvancedCommandExecutor argument limits
        Player inviter = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker
        Player invitee = Bukkit.getPlayer(inviteeName);

        if (Objects.isNull(invitee)) {
            sender.sendMessage("§4" + inviteeName + "§c is not in this lobby!");
            return true;
        }

        Team inviterTeam = teamHandler.getPlayerTeam(inviter);
        Team inviteeTeam = teamHandler.getPlayerTeam(invitee);

        if (Objects.nonNull(inviterTeam) && Objects.equals(inviterTeam, inviteeTeam)) {
            sender.sendMessage("§4" + invitee.getDisplayName() + " §c is already on your team!");
            return true;
        }

        if (teamHandler.isInvitedBy(invitee, inviter)) {
            // refer execution to accept sub command in case of mutual invite
            return (new SubCommandAccept(plugin)).onCommand(sender, command, label, new String[]{inviteeName});
        }

        if (!teamHandler.invitePlayer(inviter, invitee)) {
            sender.sendMessage("§cUnable to invite §4" + invitee.getDisplayName() + "§c to the team.");
        }

        inviter.sendMessage("§aSuccessfully invited §2" + invitee.getDisplayName() + "§a to the team.");
        invitee.sendMessage("§2" + inviter.getDisplayName() + "§a invited you to their team!");
        return true;
    }
}
