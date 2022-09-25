package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcast {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static void broadcastToTeam(Team team, String message) {
        if (team != null) {
            for (Player player : team.getPlayers()) {
                player.sendMessage(message);
            }
        }
    }

    public static void broadcastPlayerJoin(Team team, String username) {
        broadcastToTeam(team, "§2" + username + " §ahas joined your team.");
    }

    public static void broadcastPlayerLeave(Team team, String username) {
        broadcastToTeam(team, "§4" + username + " §chas left your team.");
    }
}
