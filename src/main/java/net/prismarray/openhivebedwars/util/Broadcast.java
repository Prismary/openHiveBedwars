package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Broadcast {

    public static void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }

    public static void broadcastToTeam(Team team, String message) {

        if (Objects.isNull(team)) {
            return;
        }

        team.getPlayers().forEach(p -> p.sendMessage(message));
    }

    public static void broadcastToTeamExcept(Team team, String message, Player exempt) {

        if (Objects.isNull(team)) {
            return;
        }

        team.getPlayers().stream()
                .filter(p -> !Objects.equals(p, exempt))
                .forEach(p -> p.sendMessage(message));
    }

    public static void broadcastPlayerJoin(Team team, Player joining_player) {
        broadcastToTeamExcept(
                team, "§2" + joining_player.getDisplayName() + "§a has joined your team.", joining_player
        );
    }

    public static void broadcastPlayerLeave(Team team, Player leaving_player) {
        broadcastToTeamExcept(
                team, "§4" + leaving_player.getDisplayName() + "§c has left your team.", leaving_player
        );
    }
}
