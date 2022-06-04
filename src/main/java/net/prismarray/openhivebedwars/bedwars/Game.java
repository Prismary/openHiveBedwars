package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.Status;
import net.prismarray.openhivebedwars.util.WorldCopy;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {

    OpenHiveBedwars plugin;
    Mode mode;
    Status status;
    ArrayList<Team> teams;

    public Game(OpenHiveBedwars plugin, Mode mode) {
        this.plugin = plugin;
        setup(mode);
    }

    public void setup(Mode mode) {
        status = Status.STARTUP;

        this.mode = mode;
        teams = new ArrayList<Team>();
    }


    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


    public void setLobbyPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(new Location(plugin.getServer().getWorld("lobby"), 3.5, 54, -0.5, 0, 0));
    }

    public void setIngamePlayer(Player player) {
        respawnPlayer(player);
    }

    public void setSpectatorPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(new Location(plugin.getServer().getWorld("arena"), 0, 100, 0));
    }

    public void setResultsPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(new Location(plugin.getServer().getWorld("lobby"), 51.5, 54, 15.5, -90, 0));
    }


    public void respawnPlayer(Player player) {
        player.setFallDistance(0);
    }
    public void fullPlayerClear(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }
}
