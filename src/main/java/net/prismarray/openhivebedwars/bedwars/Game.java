package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
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

    private static final Game instance = new Game();

    private Mode mode;
    private Status status;

    private GameTimer gameTimer;
    private TeamHandler teamHandler;
    private CombatHandler combatHandler;

    private LobbyTimer lobbyTimer;
    private MapVoting mapVoting;
    private MapConfig mapConfig;

    private final ArrayList<Player> hiddenPlayers;

    public Game() {
        hiddenPlayers = new ArrayList<>();
    }


    public static Game getInstance() {
        return instance;
    }


    // GAME PHASE PROGRESSION
    public static void startup(Mode mode) {
        instance.status = Status.STARTUP;
        Broadcast.prefix = OpenHiveBedwars.getBWConfig().getPrefix();

        instance.mode = mode;
        instance.gameTimer = new GameTimer();
        instance.teamHandler = new TeamHandler();
        instance.combatHandler = new CombatHandler();
        instance.lobbyTimer = new LobbyTimer();
        instance.mapVoting = new MapVoting();

        lobbySetup();

        lobby();
    }

    public static void lobby() {
        instance.status = Status.LOBBY;
        instance.lobbyTimer.start();
    }

    public static void confirmation() {
        instance.status = Status.CONFIRMATION;

        // end voting and set map
        instance.mapConfig = getMapVoting().conclude();

        // finalize team composition
        getTeamHandler().assignAndMerge();
        getTeamHandler().colorize();

        // start arena setup as task
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(OpenHiveBedwars.getInstance(), Game::arenaSetup, 0);
    }

    public static void warmup() {
        instance.status = Status.WARMUP;

        instance.lobbyTimer = null;
        new WarmupTimer().start();

        instance.gameTimer.start();

        // Initiate game start
        spawnAllPlayers();
    }

    public static void ingame() {
        instance.status = Status.INGAME;

        SummonerManager.enableTeamSummoners();
        SummonerManager.enableDiamondSummoners();
    }

    public static void concluded(TeamColor winner) {
        instance.status = Status.CONCLUDED;

        Title.sendToAll("§c§lGame. OVER!", winner.chatColor + winner.chatName + " §7won the game");

        // TODO: disable and or remove all summoners
    }

    public static void lobbySetup() {
        // try to copy world: todo proper error handling
        String lobbyName = null;
        try {
            lobbyName = OpenHiveBedwars.getBWConfig().getLobbyName();
            WorldCopy.copyMapToContainer("lobby", lobbyName, OpenHiveBedwars.getInstance().getDataFolder());
            new WorldCreator(lobbyName).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        setWorldGamerules(Bukkit.getWorld(lobbyName));
        OpenHiveBedwars.getLobbyConfig().updateWorld(Bukkit.getWorld(lobbyName));
    }

    public static void arenaSetup() {
        // try to copy world: todo proper error handling
        String arenaName = null;
        try {
            arenaName = OpenHiveBedwars.getBWConfig().getArenaName();
            WorldCopy.copyMapToContainer(getMapConfig().getMapID(), arenaName, new File(OpenHiveBedwars.getInstance().getDataFolder() + File.separator + "maps"));
            new WorldCreator(arenaName).createWorld();
        } catch (Exception e) {
            Bukkit.shutdown();
        }
        World arena = Bukkit.getWorld(arenaName);
        setWorldGamerules(arena);
        getMapConfig().updateWorld(arena);

        // Spawn beds
        clearAllBeds();
        getTeamHandler().getTeams().forEach(team -> spawnBed(team.getColor()));

        // Spawn summoners
        SummonerManager.spawnAllSummoners();

        // Spawn NPCs
        ShopManager.getInstance().spawnNPCs();
    }

    public static void clearBed(TeamColor color) {
        getMapConfig().getArenaWorld().getBlockAt(getMapConfig().getTeamBedHeadLocation(color)).setType(Material.AIR);
        getMapConfig().getArenaWorld().getBlockAt(getMapConfig().getTeamBedFootLocation(color)).setType(Material.AIR);
    }

    public static void clearAllBeds() {

        TeamColor[] colors;
        switch (instance.mode) {
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

    public static void spawnBed(TeamColor color) {
        Block bedFootBlock = getMapConfig().getArenaWorld().getBlockAt(getMapConfig().getTeamBedFootLocation(color));
        Block bedHeadBlock = getMapConfig().getArenaWorld().getBlockAt(getMapConfig().getTeamBedHeadLocation(color));

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

    public static void spawnAllPlayers() {

        for (Team team : getTeamHandler().getTeams()) {
            for (Player player : team.getPlayers()) {
                fullPlayerClear(player);
                spawnPlayer(player);
            }
        }
    }

    public static void spawnPlayer(Player player) {

        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFallDistance(0);
        player.teleport(getMapConfig().getTeamSpawn(getTeamHandler().getPlayerTeam(player).getColor()));
        showPlayer(player);
    }

    public static void respawnPlayer(Player player) {

        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(getMapConfig().getSpectatorSpawn());
        SoundHandler.playerSound(player, "mob.guardian.curse", 1f, 0.5f);

        new RespawnTimer(player).start();
    }

    public static void hidePlayer(Player player) {
        instance.hiddenPlayers.add(player);
        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));
    }

    public static void showPlayer(Player player) {
        instance.hiddenPlayers.remove(player);
        Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
    }

    public static void hideHiddenPlayers(Player player) {
        instance.hiddenPlayers.forEach(Game::hidePlayer);
    }

    public static TeamHandler getTeamHandler() {
        return instance.teamHandler;
    }
    public static CombatHandler getCombatHandler() {
        return instance.combatHandler;
    }
    public static MapVoting getMapVoting() {
        return instance.mapVoting;
    }

    public static MapConfig getMapConfig() {
        return instance.mapConfig;
    }

    public static Status getStatus() {
        return instance.status;
    }

    public static void setStatus(Status status) {
        instance.status = status;
    }

    public static Mode getMode() {
        return instance.mode;
    }


    public static void setLobbyPlayer(Player player) {
        fullPlayerClear(player);
        player.teleport(OpenHiveBedwars.getLobbyConfig().getLobbyPlayerSpawnLocation());
    }

    public static void setSpectatorPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        hidePlayer(player);
        player.teleport(instance.mapConfig.getSpectatorSpawn());
    }

    public static void setResultsPlayer(Player player) {
        fullPlayerClear(player);
        player.teleport(OpenHiveBedwars.getLobbyConfig().getResultsPlayerSpawnLocation());
    }

    public static void fullPlayerClear(Player player) {

        player.setFallDistance(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
    }

    public static void setWorldGamerules(World world) {

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
