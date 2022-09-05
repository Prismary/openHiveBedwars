package net.prismarray.openhivebedwars.commands;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Team;
import net.prismarray.openhivebedwars.util.GSU;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CmdTeam extends CommandBase {
    public CmdTeam(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Forbid usage from console
        if (GSU.getPlayer(sender.getName()) == null) {
            System.out.println("This command can not be used from the console.");
            return true;
        }

        if (args.length == 0) {
            return showTeam(sender, command, label, args);
        }

        // Cancel if not lobby status
        if (plugin.game.getStatus() != Status.LOBBY) {
            sender.sendMessage("§cUnable to change team composition at this time.");
            return true;
        }

        switch (args[0]) {
            case "invite":
                return invite(sender, command, label, args, 1);
            case "leave":
                return leave(sender, command, label, args);
            case "accept":
                return accept(sender, command, label, args, 1);
            default:
                return invite(sender, command, label, args, 0);
        }
    }

    private boolean showTeam(CommandSender sender, Command command, String label, String[] args) { // todo clean up messages
        if (!plugin.game.getTeamHandler().isInTeam(GSU.getPlayer(sender.getName()))) {
            sender.sendMessage("§cYou're currently not in a team.");
        } else {
            ArrayList<Player> players = plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(sender.getName())).getPlayers();
            sender.sendMessage("§9Your current team:");
            for (int i = 0; i < players.size(); i++) {
                sender.sendMessage("§7" + players.get(i).getName());
            }
        }
        return true;
    }

    private boolean invite(CommandSender sender, Command command, String label, String[] args, int nameArgPos) {
        if (args[nameArgPos] == null) {
            return false;
        }

        if (plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(args[nameArgPos])) == plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(sender.getName()))
        && plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(args[nameArgPos])) != null) {
            sender.sendMessage("§4" + args[nameArgPos] + " §cis already on your team!");
            return true;
        }

        if (plugin.game.getTeamHandler().isInvitedBy(GSU.getPlayer(args[nameArgPos]), GSU.getPlayer(sender.getName()))) { // Check for reverse invite
            return accept(sender, command, label, args, nameArgPos);
        }

        if (plugin.game.getTeamHandler().invitePlayer(GSU.getPlayer(sender.getName()), GSU.getPlayer(args[nameArgPos]))) {
            sender.sendMessage("§aSuccessfully invited §2" + args[nameArgPos] + " §ato the team.");
            GSU.getPlayer(args[nameArgPos]).sendMessage("§2" + sender.getName() + " §ainvited you to their team!");
        } else {
            sender.sendMessage("§cUnable to invite §4" + args[nameArgPos] + " §cto the team.");
        }
        return true;
    }

    private boolean leave(CommandSender sender, Command command, String label, String[] args) {
        Team team = plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(sender.getName()));
        if (plugin.game.getTeamHandler().removePlayer(GSU.getPlayer(sender.getName()))) {
            sender.sendMessage("§cYou have left your team.");
        } else {
            sender.sendMessage("§cYou are not currently in a team.");
        }
        plugin.game.getTeamHandler().broadcastPlayerLeave(team, sender.getName());
        plugin.game.getTeamHandler().tryDissolution(team);
        return true;
    }

    private boolean accept(CommandSender sender, Command command, String label, String[] args, int nameArgPos) {
        if (args[nameArgPos] == null) {
            return false;
        }

        if (!plugin.game.getTeamHandler().isInvitedBy(GSU.getPlayer(args[nameArgPos]), GSU.getPlayer(sender.getName()))) { // Check for invite
            sender.sendMessage("§cThere is no pending invite from this player!");
            return true;
        }

        if (plugin.game.getTeamHandler().isInTeam(GSU.getPlayer(sender.getName()))) { // Check if player is already teamed
            sender.sendMessage("§cYou are already part of a team! Leave your current team with /team leave");
            return true;
        }

        if (plugin.game.getTeamHandler().isInTeam(GSU.getPlayer(args[nameArgPos])) && plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(args[nameArgPos])).isFull()) { // check if team is full
            sender.sendMessage("§4" + args[nameArgPos] + "§c's team is full!");
            return true;
        }

        // send join message
        if (plugin.game.getTeamHandler().isInTeam(GSU.getPlayer(args[nameArgPos]))) {
            plugin.game.getTeamHandler().broadcastPlayerJoin(plugin.game.getTeamHandler().getPlayerTeam(GSU.getPlayer(args[nameArgPos])), sender.getName());
        } else {
            GSU.getPlayer(args[nameArgPos]).sendMessage("§2" + sender.getName() + " §ajoined your team!");
        }

        // join team
        plugin.game.getTeamHandler().addToPlayer(GSU.getPlayer(sender.getName()), GSU.getPlayer(args[nameArgPos]));
        sender.sendMessage("§aJoined §2" + args[nameArgPos] + "§a's team!");

        plugin.game.getTeamHandler().removeInvite(GSU.getPlayer(args[nameArgPos]), GSU.getPlayer(sender.getName())); // Remove invite
        return true;
    }
}
