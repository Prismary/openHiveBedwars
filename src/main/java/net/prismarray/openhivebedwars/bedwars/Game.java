package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.summoner.*;
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

    private Mode mode;
    private Status status;

    private TeamHandler teamHandler;
    private CombatHandler combatHandler;

    private LobbyTimer lobbyTimer;
    private MapVoting mapVoting;
    private MapConfig mapConfig;

    private final ArrayList<Player> hiddenPlayers;

    private final ArrayList<TeamSummoner> teamSummoners; // todo nicht public danke
    private final ArrayList<DiamondSummoner> diamondSummoners;
    private final ArrayList<EmeraldSummoner> emeraldSummoners;


    public Game(Mode mode) {
        hiddenPlayers = new ArrayList<>();
        teamSummoners = new ArrayList<>();
        diamondSummoners = new ArrayList<>();
        emeraldSummoners = new ArrayList<>();


        startup(mode);
    }



    // GAME PHASE PROGRESSION
    public void startup(Mode mode) {
        status = Status.STARTUP;
        Broadcast.prefix = OpenHiveBedwars.getInstance().config.getPrefix();

        this.mode = mode;
        teamHandler = new TeamHandler(this);
        combatHandler = new CombatHandler(this);
        lobbyTimer = new LobbyTimer(this);
        mapVoting = new MapVoting(this);

        lobbySetup();

        lobby();
    }

    public void lobby() {
        status = Status.LOBBY;
        lobbyTimer.start();
    }

    public void confirmation() {
        status = Status.CONFIRMATION;

        // end voting and set map
        mapConfig = mapVoting.conclude();

        // finalize team composition
        teamHandler.assignAndMerge();
        teamHandler.colorize();

        // start arena setup as task
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(OpenHiveBedwars.getInstance(), this::arenaSetup, 0);
    }

    public void warmup() {
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
        String lobbyName = null;
        try {
            lobbyName = OpenHiveBedwars.getInstance().config.getLobbyName();
            WorldCopy.copyMapToContainer("lobby", lobbyName, OpenHiveBedwars.getInstance().getDataFolder());
            new WorldCreator(lobbyName).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        setWorldGamerules(Bukkit.getWorld(lobbyName));
        OpenHiveBedwars.getInstance().lobbyConfig.updateWorld(Bukkit.getWorld(lobbyName));
    }

    public void arenaSetup() {
        // try to copy world: todo proper error handling
        String arenaName = null;
        try {
            arenaName = OpenHiveBedwars.getInstance().config.getArenaName();
            WorldCopy.copyMapToContainer(mapConfig.getMapID(), arenaName, new File(OpenHiveBedwars.getInstance().getDataFolder() + File.separator + "maps"));
            new WorldCreator(arenaName).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        World arena = Bukkit.getWorld(arenaName);
        setWorldGamerules(arena);
        mapConfig.updateWorld(arena);

        // spawn beds
        clearAllBeds();
        teamHandler.getTeams().forEach(team -> spawnBed(team.getColor()));

        // Spawn team summoners
        teamHandler.getTeams().forEach(team -> spawnTeamSummoners(team.getColor()));

        // Spawn single item summoners
        spawnDiamondSummoners();
        spawnEmeraldSummoners();
    }

    public void spawnTeamSummoners(TeamColor teamColor) {
        mapConfig.getTeamSummonerLocations(teamColor).stream()
                .map(location -> new TeamSummoner(this, location, teamColor))
                .forEach(teamSummoners::add);
    }

    public void spawnDiamondSummoners() {
        mapConfig.getDiamondSummonerLocations().stream()
                .map(location -> new DiamondSummoner(this, location))
                .forEach(diamondSummoners::add);
    }

    public void spawnEmeraldSummoners() {
        mapConfig.getEmeraldSummonerLocations().stream()
                .map(location -> new EmeraldSummoner(this, location))
                .forEach(emeraldSummoners::add);
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
        player.setFallDistance(0);
        player.teleport(mapConfig.getTeamSpawn(teamHandler.getPlayerTeam(player).getColor()));
        showPlayer(player);
    }

    public void respawnPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(mapConfig.getSpectatorSpawn());
        SoundHandler.playerSound(player, "mob.guardian.curse", 1f, 0.5f);

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
    public CombatHandler getCombatHandler() {
        return combatHandler;
    }
    public MapVoting getMapVoting() {
        return mapVoting;
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
        player.teleport(OpenHiveBedwars.getInstance().lobbyConfig.getLobbyPlayerSpawnLocation());
    }

    public void setSpectatorPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(mapConfig.getSpectatorSpawn());
    }

    public void setResultsPlayer(Player player) {
        fullPlayerClear(player);
        player.teleport(OpenHiveBedwars.getInstance().lobbyConfig.getResultsPlayerSpawnLocation());
    }

    public void fullPlayerClear(Player player) {
        player.setFallDistance(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
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
