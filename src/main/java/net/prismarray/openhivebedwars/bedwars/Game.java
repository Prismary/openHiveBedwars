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

        lobbySetup();

        lobby();
    }

    public void lobby() {
        status = Status.LOBBY;
        lobbyTimer.start();
    }

    public void confirmation() {
        status = Status.CONFIRMATION;

        // finalize team composition
        teamHandler.assignAndMerge();
        teamHandler.colorize();

        // load map config: todo map voting
        mapConfig = plugin.mapManager.getMapConfig("d_castle");
    }

    public void warmup() {
        // Arena setup already called by LobbyTimer!

        status = Status.WARMUP;

        lobbyTimer = null;

        // Initiate game start
        spawnAllPlayers();
    }

    public void ingame() {
        status = Status.INGAME;
    }



    public void lobbySetup() {
        // try to copy world: todo proper error handling
        try {
            WorldCopy.copyMapToContainer("lobby", plugin.config.getLobbyName(), plugin.getDataFolder());
            new WorldCreator(plugin.config.getLobbyName()).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        setWorldGamerules(Bukkit.getWorld(plugin.config.getLobbyName()));
        plugin.lobbyConfig.updateWorld(Bukkit.getWorld(plugin.config.getLobbyName()));
    }

    public void arenaSetup() {
        // try to copy world: todo proper error handling
        try {
            WorldCopy.copyMapToContainer(mapConfig.getMapID(), plugin.config.getArenaName(), new File(plugin.getDataFolder() + File.separator + "maps"));
            new WorldCreator(plugin.config.getArenaName()).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        setWorldGamerules(Bukkit.getWorld(plugin.config.getArenaName()));
        mapConfig.updateWorld(Bukkit.getWorld(plugin.config.getArenaName()));
    }

    public void spawnAllPlayers() {
        for (Team team : teamHandler.getTeams()) {
            for (Player player : team.getPlayers()) {
                fullPlayerClear(player);
                spawnPlayer(player);
            }
        }
    }

    public void spawnPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(mapConfig.getTeamSpawn(teamHandler.getPlayerTeam(player).getColor()));
    }

    public void respawnPlayer(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(mapConfig.getSpectatorSpawn());

        new RespawnTimer(this, player).start();
    }


    public TeamHandler getTeamHandler() {
        return teamHandler;
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
        player.teleport(plugin.lobbyConfig.getLobbyPlayerSpawnLocation());
    }

    public void setSpectatorPlayer(Player player) {
        player.teleport(mapConfig.getSpectatorSpawn());
    }

    public void setResultsPlayer(Player player) {
        player.setFallDistance(0);
        player.teleport(plugin.lobbyConfig.getResultsPlayerSpawnLocation());
    }

    public void fullPlayerClear(Player player) {
        player.setFallDistance(0);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }

    public void setWorldGamerules(World world) {
        // Set all gamerules for a world used in openHiveBedwars
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doEntityDrops", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("doMobLoot", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doTileDrops", "true");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("logAdminCommands", "true");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("naturalRegeneration", "true");
        world.setGameRuleValue("randomTickSpeed", "3");
        world.setGameRuleValue("reducedDebugInfo", "false");
        world.setGameRuleValue("sendCommandFeedback", "true");
        world.setGameRuleValue("showDeathMessages", "false");
    }
}
