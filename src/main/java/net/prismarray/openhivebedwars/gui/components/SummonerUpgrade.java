package net.prismarray.openhivebedwars.gui.components;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.bedwars.summoner.SummonerManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIClickAction;
import net.prismarray.openhivebedwars.util.Broadcast;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import net.prismarray.openhivebedwars.util.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class SummonerUpgrade extends InventoryGUIItem {

    public SummonerUpgrade(InventoryGUIBase gui, int slot, TeamColor teamColor, Currency currency, int level, Currency purchaseCurrency, int cost, String reloadGUI) {
        super(
                gui,
                slot,
                (isOwned(teamColor, currency, level - 1) ? currency.material : Material.AIR),
                level,
                ItemNameBuilder.getSummonerUpgradeName(currency, level),
                ItemNameBuilder.getSummonerUpgradeLore(teamColor, currency, level, purchaseCurrency, cost),
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

                    String summonerName = currency.chatName + " Team Summoner"; // TODO check whether this is the correct message
                    boolean isPaid = ShopManager.takePayment(a.getPlayer(), purchaseCurrency, cost, summonerName);

                    if (!isPaid) {
                        return;
                    }

                    SummonerManager.getTeamSummoner(teamColor).upgrade(getSummonerIndex(currency));

                    Bukkit.getScheduler().runTask(OpenHiveBedwars.getInstance(), () -> { // re-open the upgrades inventory
                        try {
                            InventoryGUIManager.openInventoryGUI(reloadGUI, a.getPlayer());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

                    Broadcast.toTeam(Game.getTeamHandler().getTeamByColor(teamColor), "§9Summoner upgraded!"); // TODO set original text
                }
            });
        }
    }

    public static boolean isOwned(TeamColor teamColor, Currency currency, int level) {

        int[] summonLevels = SummonerManager.getTeamSummoner(teamColor).getSummonLevels();
        return (summonLevels[getSummonerIndex(currency)] >= level);
    }

    public static int getSummonerIndex(Currency currency) {
        switch (currency) {
            case IRON:
                return 0;
            case GOLD:
                return 1;
            case DIAMOND:
                return 2;
        }
        return -1;
    }
}
