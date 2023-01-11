package net.prismarray.openhivebedwars.bedwars.shop.items.npc_upgrades.summoner_upgrades;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.bedwars.summoner.SummonerManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.concurrent.Callable;

public class SummonerUpgrade extends InventoryGUIItem {

    public SummonerUpgrade(InventoryGUIBase gui, int slot, TeamColor teamColor, Currency currency, int level, Currency purchaseCurrency, int cost, int summonerIndex, Callable<? extends InventoryGUIBase> reloadGUIFactory) {
        super(
                gui,
                slot,
                (isOwned(teamColor, currency, level - 1) ? currency.material : Material.AIR),
                level,
                getName(currency, level),
                getLore(teamColor, currency, level, purchaseCurrency, cost),
                isOwned(teamColor, currency, level)
        );

        if (isOwned(teamColor, currency, level)) {
            addActionListenerToContainingInventory(new InventoryGUIActionListener() {
                @InventoryGUIActionHandler
                public void onClick(InventoryGUIClickAction a) {
                    Broadcast.toPlayer(a.getPlayer(), "§6Your team already has this!");
                }
            });
            return;
        }

        if (isOwned(teamColor, currency, level - 1)) {
            addActionListenerToContainingInventory(new InventoryGUIActionListener() {
                @InventoryGUIActionHandler
                public void onClick(InventoryGUIClickAction a) {
                    ShopManager.takePayment(a.getPlayer(), purchaseCurrency, cost, currency.chatName + " Team Summoner"); // TODO check whether this is the correct message

                    SummonerManager.getTeamSummoner(teamColor).upgrade(summonerIndex);

                    Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> { // re-open the upgrades inventory
                        try {
                            reloadGUIFactory.call().open(a.getPlayer());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    Broadcast.toTeam(Game.getTeamHandler().getTeamByColor(teamColor), "§9Summoner upgraded!"); // TODO set original text
                }
            });
        }
    }

    public static String getName(Currency currency, int level) {
        if (level > 1) {
            return String.format(
                    "§b§lLevel %s %s%s Summoner",
                    level,
                    currency.color,
                    currency.chatName
            );
        }

        return String.format(
                "§b§l%s Summoner",
                currency.chatName
        );
    }

    public static String[] getLore(TeamColor teamColor, Currency currency, int level, Currency purchaseCurrency, int cost) {
        if (isOwned(teamColor, currency, level)) {
            return new String[]{"", "§a§lYou already own this.", "", "§b➜ §7Already owned"};
        }

        return new String[]{
                "",
                "§6§lCost",
                String.format(
                        "  %s%s %s",
                        purchaseCurrency.color,
                        cost,
                        ((cost > 1) ? Currency.getNamePlural(purchaseCurrency) : purchaseCurrency.chatName)),
                "",
                "§b➜ Left-Click to purchase"
        };
    }

    public static boolean isOwned(TeamColor teamColor, Currency currency, int level) {
        int[] summonLevels = SummonerManager.getTeamSummoner(teamColor).getSummonLevels();

        switch (currency) {
            case IRON:
                return (summonLevels[0] >= level);
            case GOLD:
                return (summonLevels[1] >= level);
            case DIAMOND:
                return (summonLevels[2] >= level);
        }

        return false;
    }
}
