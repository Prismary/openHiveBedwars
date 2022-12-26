package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Broadcast {

    public static String prefix;

    public static void raw(String message) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }

    public static void broadcast(String message) {
        raw(prefix + message);
    }

    public static void rawToTeam(Team team, String message) {

        if (Objects.isNull(team)) {
            return;
        }

        team.getPlayers().forEach(p -> p.sendMessage(message));
    }

    public static void rawToTeamExcept(Team team, String message, Player exempt) {

        if (Objects.isNull(team)) {
            return;
        }

        team.getPlayers().stream()
                .filter(p -> !Objects.equals(p, exempt))
                .forEach(p -> p.sendMessage(message));
    }

    public static void toTeam(Team team, String message) {
        rawToTeam(team, prefix + message);
    }

    public static void toTeamExcept(Team team, String message, Player exempt) {
        rawToTeamExcept(team, prefix + message, exempt);
    }

    public static void toPlayer(Player player, String message) {
        player.sendMessage(prefix + message);
    }

    public static void teamJoin(Team team, Player joiningPlayer) {
        toTeam(team, "§9" + joiningPlayer.getDisplayName() + " §ajoined your team!");
    }

    public static void teamLeave(Team team, Player leavingPlayer) {
        toTeam(team, "§9" + leavingPlayer.getDisplayName() + " §cleft your team §6=(");
    }

    public static void kill(Player killer, TeamColor killerColor, Player killed, TeamColor killedColor) {
        broadcast("§c✖ " + killedColor.chatColor + killed.getDisplayName() + " §7was killed by " + killerColor.chatColor + killer.getDisplayName() + "§7!");
    }

    public static void finalKill(Player killer, TeamColor killerColor, Player killed, TeamColor killedColor) {
        broadcast("§c✖ " + killedColor.chatColor + killed.getDisplayName() + " §7was eliminated by " + killerColor.chatColor + killer.getDisplayName() + "§7!");
    }

    public static void death(Player player, TeamColor color) {
        broadcast("§c✖ " + color.chatColor + player.getDisplayName() + " §7died.");
    }

    public static void bedBreak(TeamColor color) {
        broadcast("§cThe " + color.chatColor + color.chatName + " §cBed has been DESTROYED!");
    }

    public static void teamElimination(TeamColor color) {
        broadcast(color.chatColor + color.chatName + " has been eliminated!");
    }
}
