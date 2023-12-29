package net.prismarray.openhivebedwars;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.commands.gui.CommandGUI;
import net.prismarray.openhivebedwars.commands.openhivebedwars.CommandOpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.team.CommandTeam;
import net.prismarray.openhivebedwars.commands.vote.CommandVote;
import net.prismarray.openhivebedwars.config.Config;
import net.prismarray.openhivebedwars.config.ConfigValidationException;
import net.prismarray.openhivebedwars.config.LobbyConfig;
import net.prismarray.openhivebedwars.config.MapManager;
import net.prismarray.openhivebedwars.enchantments.InventoryGUIDummyEnchantment;
import net.prismarray.openhivebedwars.events.*;
import org.bukkit.command.defaults.EnchantCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class OpenHiveBedwars extends JavaPlugin {

    private static OpenHiveBedwars instance;

    private Config config;
    private LobbyConfig lobbyConfig;

    public OpenHiveBedwars() {
        instance = this;
    }

    public static OpenHiveBedwars getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        initializeConfig();
        initializeLobbyConfig();
        initializeMapManager();

        registerCommands();
        registerEvents();
        registerEnchantments();

        Game.startup(config.getMode());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }


    private void initializeConfig() {

        this.getLogger().info("Attempting to load config.yml...");

        config = new Config(this.getLogger(), new File(this.getDataFolder(), "config.yml"));
        try {
            config.loadConfig(getResource("config.yml"));
            this.getLogger().info("Successfully loaded config.yml");

        } catch (ConfigValidationException | IOException e) {
            this.getLogger().warning("Failed to load config.yml. Using default values instead...");
            this.getLogger().warning("Error message: " + e.getMessage());
        }
    }

    private void initializeLobbyConfig() {

        this.getLogger().info("Attempting to load lobby.yml...");

        lobbyConfig = new LobbyConfig(this.getLogger(), new File(this.getDataFolder(), "lobby.yml"));
        try {
            lobbyConfig.loadConfig(getResource("lobby.yml"));
            this.getLogger().info("Successfully loaded lobby.yml");

        } catch (ConfigValidationException | IOException e) {
            this.getLogger().warning("Failed to load lobby.yml. Using default values instead...");
            this.getLogger().warning("Error message: " + e.getMessage());
        }
    }

    private void initializeMapManager() {

        MapManager.loadMaps(config.getMode());
    }

    private void registerCommands() {
        getCommand("openhivebedwars").setExecutor(new CommandOpenHiveBedwars());
        getCommand("team").setExecutor(new CommandTeam());
        getCommand("vote").setExecutor(new CommandVote());
        getCommand("gui").setExecutor(new CommandGUI());
    }

    private void registerEvents() {

        getServer().getPluginManager().registerEvents(new EvtPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new EvtPlayerLogin(), this);
        getServer().getPluginManager().registerEvents(new EvtBlockPhysics(), this);
        getServer().getPluginManager().registerEvents(new EvtFoodLevelChange(), this);
        getServer().getPluginManager().registerEvents(new EvtNaturalMobSpawn(), this);
        getServer().getPluginManager().registerEvents(new EvtPlayerDamage(), this);
        getServer().getPluginManager().registerEvents(new EvtWeatherChange(), this);
        getServer().getPluginManager().registerEvents(new EvtBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new EvtBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new EvtFireExtinguish(), this);
        getServer().getPluginManager().registerEvents(new EvtPlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new EvtStructureGrow(), this);
        getServer().getPluginManager().registerEvents(new EvtBedBreak(), this);
        getServer().getPluginManager().registerEvents(new EvtBuildLimit(), this);
        getServer().getPluginManager().registerEvents(new EvtInventoryGUI(), this);
        getServer().getPluginManager().registerEvents(new EvtBridgeBuilder(), this);
        getServer().getPluginManager().registerEvents(new EvtEntityDamage(), this);
        getServer().getPluginManager().registerEvents(new EvtNPCInteraction(), this);
        getServer().getPluginManager().registerEvents(new EvtFormAndFade(), this);
        getServer().getPluginManager().registerEvents(new EvtLeavesDecay(), this);
        getServer().getPluginManager().registerEvents(new EvtTeamChest(), this);
        getServer().getPluginManager().registerEvents(new EvtSpectatorCompass(), this);
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    private void registerEnchantments() {

        int nextID = Arrays.stream(Enchantment.values())
                .mapToInt(e -> e.getId() + 1)
                .max()
                .orElse(0);

        try {
            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            acceptingNew.setAccessible(false);

            Field enchantmentNames = EnchantCommand.class.getDeclaredField("ENCHANTMENT_NAMES");
            enchantmentNames.setAccessible(true);
            ((List<String>) enchantmentNames.get(null)).clear();
            enchantmentNames.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        Enchantment.registerEnchantment(new InventoryGUIDummyEnchantment(nextID++));

        Enchantment.stopAcceptingRegistrations();
    }

    public static Config getBWConfig() {
        return instance.config;
    }

    public static LobbyConfig getLobbyConfig() {
        return instance.lobbyConfig;
    }
}
