package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcast {

    public static String prefix;

    public static void raw(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
    public static void broadcast(String message) {
        raw(prefix + message);
    }

    public static void broadcastToTeam(Team team, String message) {
        if (team != null) {
            for (Player player : team.getPlayers()) {
                player.sendMessage(message);
            }
        }
    }

    public static void playerJoin(Team team, String username) {
        broadcastToTeam(team, "§2" + username + " §ahas joined your team.");
    }

    public static void playerLeave(Team team, String username) {
        broadcastToTeam(team, "§4" + username + " §chas left your team.");
    }

    public static void kill(String killerName, TeamColor killerColor, String killedName, TeamColor killedColor) {
        broadcast("§c✖ " + killedColor.chatColor + killedName + "§7was killed by " + killerColor.chatColor + killerName + "§7!");
    }

    public static void finalKill(String killerName, TeamColor killerColor, String killedName, TeamColor killedColor) {
        broadcast("§c✖ " + killedColor.chatColor + killedName + "§7was eliminated by " + killerColor.chatColor + killerName + "§7!");
    }

    public static void death(String name, TeamColor color) {
        broadcast("§c✖ " + color.chatColor + name + "§7died.");
    }

    public static void bedBreak(TeamColor color) {
        broadcast("§cThe " + color.chatColor + color.chatName + " §cBed has been DESTROYED!");
    }

    public static void teamElimination(TeamColor color) {
        broadcast(color.chatColor + color.chatName + " has been eliminated!");
    }
}
