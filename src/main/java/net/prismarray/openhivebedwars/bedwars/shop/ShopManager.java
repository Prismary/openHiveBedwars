package net.prismarray.openhivebedwars.bedwars.shop;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.shop.npc.*;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static void purchase(Player player, String name, ItemStack item, Currency currency, int cost) {
        if (!takePayment(player, currency, cost, name)) { // Attempt to take payment
            return;
        }

        // Add purchased item
        player.getInventory().addItem(item);

        Broadcast.toPlayer(player, String.format(
                "§aPurchased §f%s §afor %s%s %s.",
                name,
                currency.color,
                cost,
                ((cost == 1) ? currency.chatName : Currency.getNamePlural(currency))
        ));
    }

    public static boolean takePayment(Player player, Currency currency, int cost, String name) {
        int playerCurrency = countCurrency(player, currency);

        if (playerCurrency < cost) {
            Broadcast.toPlayer(player, String.format(
                    "§cYou need %s%s %s §c(§7%s §cmore) for §7%s§c!",
                    currency.color,
                    cost,
                    ((cost == 1) ? currency.chatName : Currency.getNamePlural(currency)),
                    cost - playerCurrency,
                    name
            ));
            return false;
        }

        player.getInventory().removeItem(new ItemStack(currency.material, cost));
        return true;
    }

    public static int countCurrency(Player player, Currency currency) {
        AtomicInteger amount = new AtomicInteger();
        player.getInventory().all(currency.material).forEach((integer, itemStack) -> {
            amount.addAndGet(itemStack.getAmount());
        });
        return amount.get();
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
