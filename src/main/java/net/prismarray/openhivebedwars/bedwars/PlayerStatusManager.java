package net.prismarray.openhivebedwars.bedwars;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatusManager {

    public enum PlayerStatus {
        ALIVE,
        RESPAWNING,
        SPECTATOR
    }

    public static final PlayerStatusManager instance = new PlayerStatusManager();
    private final Map<Player, PlayerStatus> playerStates;

    private PlayerStatusManager() {
        playerStates = new HashMap<>();
    }

    public static PlayerStatus getPlayerStatus(Player p) {

        if (!instance.playerStates.containsKey(p)) {
            instance.playerStates.put(p, PlayerStatus.ALIVE);
        }
        return instance.playerStates.get(p);
    }

    public static void registerPlayerDeath(Player p) {
        registerPlayerDeath(p, false);
    }

    public static void registerPlayerDeath(Player p, boolean willRespawn) {

        if (willRespawn) {
            instance.playerStates.put(p, PlayerStatus.RESPAWNING);
        } else {
            instance.playerStates.put(p, PlayerStatus.SPECTATOR);
        }
    }

    public static void registerPlayerRespawn(Player p) {
        instance.playerStates.put(p, PlayerStatus.ALIVE);
    }
}
