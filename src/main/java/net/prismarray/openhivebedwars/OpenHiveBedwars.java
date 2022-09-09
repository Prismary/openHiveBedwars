package net.prismarray.openhivebedwars;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.commands.CmdOpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.CmdTeam;
import net.prismarray.openhivebedwars.config.Config;
import net.prismarray.openhivebedwars.config.MapManager;
import net.prismarray.openhivebedwars.events.*;
import net.prismarray.openhivebedwars.util.WorldCopy;
import org.bukkit.WorldCreator;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class OpenHiveBedwars extends JavaPlugin {

    public Config config;
    public MapManager mapManager;
    public Game game;

    @Override
    public void onEnable() {

        // Initialize config
        config = new Config(this.getLogger(), new File(this.getDataFolder(), "config.yml"));
        try {
            config.loadConfig();
        } catch (IOException e) {
            this.getLogger().warning("Failed to load config.yml");
            this.getLogger().warning("Error message: " + e.getMessage());
        }

        // Initialize MapManager
        mapManager = new MapManager(this);
        mapManager.loadMaps();

        registerCommands();
        registerEvents();

        game = new Game(this, config.getMode());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }


    private void registerCommands() {
        getCommand("openhivebedwars").setExecutor(new CmdOpenHiveBedwars(this));
        getCommand("team").setExecutor(new CmdTeam(this));
    }
    private void registerEvents() {

        getServer().getPluginManager().registerEvents(new EvtPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new EvtPlayerLogin(this), this);
        getServer().getPluginManager().registerEvents(new EvtBlockPhysics(this), this);
        getServer().getPluginManager().registerEvents(new EvtFoodLevelChange(this), this);
        getServer().getPluginManager().registerEvents(new EvtCreatureSpawn(this), this);
        getServer().getPluginManager().registerEvents(new EvtEntityDamage(this), this);
        getServer().getPluginManager().registerEvents(new EvtWeatherChange(this), this);
        getServer().getPluginManager().registerEvents(new EvtBlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new EvtBlockBreak(this), this);
        getServer().getPluginManager().registerEvents(new EvtFireExtinguish(this), this);
        getServer().getPluginManager().registerEvents(new EvtPlayerQuit(this), this);
    }
}
