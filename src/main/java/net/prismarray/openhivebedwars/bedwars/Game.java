package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.config.MapConfig;
import net.prismarray.openhivebedwars.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Bed;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;

public class Game {
    OpenHiveBedwars plugin;
    Mode mode;
    Status status;
    TeamHandler teamHandler;
    LobbyTimer lobbyTimer;
    MapConfig mapConfig;
    ArrayList<Player> hiddenPlayers;

    public Game(OpenHiveBedwars plugin, Mode mode) {
        this.plugin = plugin;
        hiddenPlayers = new ArrayList<>();
        startup(mode);
    }



    // GAME PHASE PROGRESSION
    public void startup(Mode mode) {
        status = Status.STARTUP;
        Broadcast.prefix = plugin.config.getPrefix();

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
        new GameStartTimer(this).start();

        // Initiate game start
        spawnAllPlayers();
    }

    public void ingame() {
        status = Status.INGAME;
    }

    public void concluded(TeamColor winner) {
        status = Status.CONCLUDED;

        Title.sendToAll("§c§lGame. OVER!", winner.chatColor + winner.chatName + " §7won the game");
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
        World arena = Bukkit.getWorld(plugin.config.getArenaName());
        setWorldGamerules(arena);
        mapConfig.updateWorld(arena);

        // spawn beds
        clearAllBeds();
        for (Team team : teamHandler.getTeams()) {
            spawnBed(team.getColor());
        }
    }

    public void clearBed(TeamColor color) {
        mapConfig.getArenaWorld().getBlockAt(mapConfig.getTeamBedHeadLocation(color)).setType(Material.AIR);
        mapConfig.getArenaWorld().getBlockAt(mapConfig.getTeamBedFootLocation(color)).setType(Material.AIR);
    }

    public void clearAllBeds() {
        TeamColor[] colors;
        switch (mode) {
            case SOLO:
                colors = TeamColor.getSoloModeColours();
                break;
            case DUOS:
                colors = TeamColor.getDuosModeColours();
                break;
            default:
                colors = TeamColor.getTeamsModeColours();
        }

        for (TeamColor color : colors) {
            clearBed(color);
        }
    }

    public void spawnBed(TeamColor color) {
        Block bedFootBlock = mapConfig.getArenaWorld().getBlockAt(mapConfig.getTeamBedFootLocation(color));
        Block bedHeadBlock = mapConfig.getArenaWorld().getBlockAt(mapConfig.getTeamBedHeadLocation(color));

        Vector diff = bedFootBlock.getLocation().toVector().subtract(bedHeadBlock.getLocation().toVector());

        BlockFace facing = null;

        switch ((int) Math.round(diff.getX())) {
            case 1:
                facing = BlockFace.WEST;
                break;
            case -1:
                facing = BlockFace.EAST;
                break;
        }

        switch ((int) Math.round(diff.getZ())) {
            case 1:
                facing = BlockFace.NORTH;
                break;
            case -1:
                facing = BlockFace.SOUTH;
                break;
        }

        // Create Bed Head Block
        BlockState bedHeadState = bedHeadBlock.getState();
        bedHeadState.setType(Material.BED_BLOCK);
        Bed bedHeadData = new Bed(Material.BED_BLOCK);
        bedHeadData.setHeadOfBed(true);
        bedHeadData.setFacingDirection(facing);
        bedHeadState.setData(bedHeadData);
        bedHeadState.update(true);

        // Create Bed Foot Block
        BlockState bedFootState = bedFootBlock.getState();
        bedFootState.setType(Material.BED_BLOCK);
        Bed bedFootData = new Bed(Material.BED_BLOCK);
        bedFootData.setHeadOfBed(false);
        bedFootData.setFacingDirection(facing);
        bedFootState.setData(bedFootData);
        bedFootState.update(true);

        // Thanks to val59000 on spigotmc.org!
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
        player.setAllowFlight(false);
        player.setFlying(false);
        showPlayer(player);
        player.setFallDistance(0);
        player.teleport(mapConfig.getTeamSpawn(teamHandler.getPlayerTeam(player).getColor()));
    }

    public void respawnPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(mapConfig.getSpectatorSpawn());

        new RespawnTimer(this, player).start();
    }

    public void hidePlayer(Player player) {
        hiddenPlayers.add(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(player);
        }
    }

    public void showPlayer(Player player) {
        hiddenPlayers.remove(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(player);
        }
    }

    public void hideHiddenPlayers(Player player) {
        for (Player hiddenPlayer : hiddenPlayers) {
            player.hidePlayer(hiddenPlayer);
        }
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
        fullPlayerClear(player);
        player.teleport(plugin.lobbyConfig.getLobbyPlayerSpawnLocation());
    }

    public void setSpectatorPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(mapConfig.getSpectatorSpawn());
    }

    public void setResultsPlayer(Player player) {
        fullPlayerClear(player);
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
        world.setGameRuleValue("doWeatherCycle", "false");
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
