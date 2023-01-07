package net.prismarray.openhivebedwars.bedwars.shop;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.shop.npc.*;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class ShopManager {

    private static final ShopManager instance = new ShopManager();

    private final ArrayList<Shop> shops;

    private ShopManager() {
        shops = new ArrayList<>();
    }

    public static ShopManager getInstance() {
        return instance;
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public void spawnNPCs() {
        Game.getTeamHandler().getTeams().forEach(team -> {
            spawnItemNPCs(team.getColor());
            spawnUpgradeNPCs(team.getColor());
        });
        spawnEnchanterNPCs();
        spawnSpecialistNPCs();
    }

    public Shop getShop(Entity entity) {
        for (Shop shop : shops) {
            if (shop.getEntity() == entity) {
                return shop;
            }
        }

        return null;
    }

    private void spawnItemNPCs(TeamColor teamColor) {
        Game.getMapConfig().getTeamItemNPCLocations(teamColor).forEach(Items::new);
    }

    private void spawnUpgradeNPCs(TeamColor teamColor) {
        Game.getMapConfig().getTeamUpgradesNPCLocations(teamColor).forEach(Upgrades::new);
    }

    private void spawnEnchanterNPCs() {
        Game.getMapConfig().getEnchanterNPCLocations().forEach(Enchanter::new);
    }

    private void spawnSpecialistNPCs() {
        Game.getMapConfig().getSpecialistNPCLocations().forEach(Items::new);
    }
}
