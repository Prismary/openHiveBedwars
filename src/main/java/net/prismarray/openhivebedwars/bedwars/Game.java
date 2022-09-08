package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.config.MapConfig;
import net.prismarray.openhivebedwars.util.Mode;
import net.prismarray.openhivebedwars.util.Status;
import net.prismarray.openhivebedwars.util.WorldCopy;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;

public class Game {
    OpenHiveBedwars plugin;
    Mode mode;
    Status status;
    TeamHandler teamHandler;
    LobbyTimer lobbyTimer;
    MapConfig mapConfig;

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

        // Set up lobby world: todo load from cfg
        WorldCopy.copyMapToContainer("hive_bw_lobby", plugin.config.getLobbyName(), Bukkit.getWorldContainer());
        new WorldCreator(plugin.config.getLobbyName()).createWorld();

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

        // load map config: todo map voting
        mapConfig = plugin.mapManager.getMapConfig("d_castle");

        // try to copy world: todo proper error handling
        try {
            WorldCopy.copyMapToContainer(mapConfig.getMapID(), plugin.config.getArenaName(), new File(plugin.getDataFolder() + File.separator + "maps"));
            new WorldCreator(plugin.config.getArenaName()).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }

        mapConfig.updateWorld(Bukkit.getWorld(plugin.config.getArenaName()));
    }

    public void warmup() {
        status = Status.WARMUP;

        lobbyTimer.disable();

        // Initiate game start
        spawnAllPlayers();
    }

    public void ingame() {
        status = Status.INGAME;
    }



    public void spawnAllPlayers() {
        for (Team team : teamHandler.getTeams()) {
            for (Player player : team.getPlayers()) {
                spawnPlayer(player);
            }
        }
    }

    public void spawnPlayer(Player player) {
        //fullPlayerClear(player);
        Location location = mapConfig.getTeamSpawn(teamHandler.getPlayerTeam(player).getColor());
        location.setWorld(Bukkit.getWorld("arena"));
        player.teleport(location);
    }



    public TeamHandler getTeamHandler() {
        return teamHandler;
    }

    public LobbyTimer getLobbyTimer() {
        return lobbyTimer;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


    public void setLobbyPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(new Location(plugin.getServer().getWorld(plugin.config.getLobbyName()), 3.5, 54, -0.5, 0, 0));
    }

    public void setSpectatorPlayer(Player player) {
        player.teleport(mapConfig.getSpectatorSpawn());
    }

    public void setResultsPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(new Location(plugin.getServer().getWorld(plugin.config.getLobbyName()), 51.5, 54, 15.5, -90, 0));
    }


    public void respawnPlayer(Player player) {
        spawnPlayer(player);
    }
    public void fullPlayerClear(Player player) {
        player.setFallDistance(0);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }


}
