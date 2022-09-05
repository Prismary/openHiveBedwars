package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.Status;
import net.prismarray.openhivebedwars.util.WorldCopy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Game {
    OpenHiveBedwars plugin;
    Mode mode;
    Status status;
    TeamHandler teamHandler;

    LobbyTimer lobbyTimer;

    public Game(OpenHiveBedwars plugin, Mode mode) {
        this.plugin = plugin;
        startup(mode);
    }



    // GAME PHASE PROGRESSION
    public void startup(Mode mode) {
        status = Status.STARTUP;

        this.mode = mode;
        teamHandler = new TeamHandler(this);
        lobbyTimer = new LobbyTimer(this);

        lobby();
    }

    public void lobby() {
        status = Status.LOBBY;
        lobbyTimer.enable();
    }

    public void confirmation() {
        status = Status.CONFIRMATION;

        // finalize team composition
        teamHandler.assignAndMerge();
        teamHandler.colorize();
    }

    public void warmup() {
        status = Status.WARMUP;

        lobbyTimer.disable();
    }



    public TeamHandler getTeamHandler() {
        return teamHandler;
    }

    public LobbyTimer getLobbyTimer() {
        return lobbyTimer;
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
