package net.prismarray.openhivebedwars.commands.team;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.TeamHandler;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import net.prismarray.openhivebedwars.util.Broadcast;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class SubCommandAccept extends PluginBoundCommandExecutor {

    public SubCommandAccept(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TeamHandler teamHandler = plugin.game.getTeamHandler();
        Player invitee = (Player) sender; // guaranteed to be legal due to PlayerOnlyChecker
        Player inviter;

        if (args.length == 0) {

            List<Player> inviters = teamHandler.getInvitingPlayers(invitee);

            if (inviters.size() == 0) {
                invitee.sendMessage("§cThere are no pending invites for you!");
                return true;
            }

            if (inviters.size() > 1) {
                invitee.sendMessage(
                        "§cThere are multiple pending invites for you. Please specify, which one you " +
                                "would like to accept using §4/team accept <player>§c."
                );
                return true;
            }

            inviter = inviters.get(0);

            if (Objects.isNull(inviter)) {
                invitee.sendMessage("§cThe inviting player is no longer in the lobby!");
                return true;
            }

        } else {

            String inviterName = args[0];
            inviter = Bukkit.getPlayer(inviterName);

            if (Objects.isNull(inviter)) {
                sender.sendMessage("§4" + inviterName + "§c is not in this lobby!");
                return true;
            }
        }


        if (!teamHandler.isInvitedBy(inviter, invitee)) {
            invitee.sendMessage("§cThere is no pending invite from this player!");
            return true;
        }

        if (teamHandler.isInTeam(invitee)) {
            sender.sendMessage("§cYou are already part of a team! Leave your current team with /team leave");
            return true;
        }

        if (teamHandler.isInTeam(inviter) && teamHandler.getPlayerTeam(inviter).isFull()) {
            sender.sendMessage("§4" + inviter.getDisplayName() + "§c's team is full!");
            return true;
        }


        if (!teamHandler.addToPlayer(invitee, inviter)) {
            sender.sendMessage("§cUnable to join " + inviter.getDisplayName() + "§c's team!");
            return true;
        }
        teamHandler.removeInvite(inviter, invitee);

        Broadcast.teamJoin(teamHandler.getPlayerTeam(inviter), invitee);
        invitee.sendMessage("§aJoined §2" + inviter.getDisplayName() + "§a's team!");

        return true;
    }
}
