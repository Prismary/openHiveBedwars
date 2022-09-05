package net.prismarray.openhivebedwars.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GSU { // General Static Utility

    public static Player getPlayer(String username) {
        username = username.toLowerCase();
        ImmutableList<Player> currentPlayers =  ImmutableList.copyOf(Bukkit.getOnlinePlayers());
        for (int i = 0; i < currentPlayers.size(); i++) {
            if (currentPlayers.get(i).getName().toLowerCase().equals(username)) {
                return currentPlayers.get(i);
            }
        }
        return null;
    }
}
