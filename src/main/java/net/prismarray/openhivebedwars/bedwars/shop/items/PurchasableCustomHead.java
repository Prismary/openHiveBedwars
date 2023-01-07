package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUICustomHead;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUILeftOrShiftLeftClickAction;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIRightOrShiftRightClickAction;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class PurchasableCustomHead extends InventoryGUICustomHead {

    public PurchasableCustomHead(InventoryGUIBase gui, int slot, String url, int amount, boolean enchanted, String name,
                                 int cost, Currency currency, boolean showFavStatus, boolean isFavorite, ItemStack purchaseItem) {
        this(gui, slot, url, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, null, purchaseItem);
    }

    public PurchasableCustomHead(InventoryGUIBase gui, int slot, String url, int amount, boolean enchanted, String name,
                                 int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore, ItemStack purchaseItem) {
        super(
                gui,
                slot,
                url,
                amount,
                ItemNameBuilder.getPurchasableName(name, amount),
                ItemNameBuilder.getPurchasableLore(cost, currency, showFavStatus, isFavorite, bonusLore),
                enchanted
        );

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUILeftOrShiftLeftClickAction a) {
                ShopManager.purchase(a.getPlayer(), name, purchaseItem, currency, cost);
            }
        });

        if (!showFavStatus) {
            return;
        }

        addActionListenerToContainingInventory(new InventoryGUIActionListener() {
            @InventoryGUIActionHandler
            public void onClick(InventoryGUIRightOrShiftRightClickAction a) {
                // todo fav or unfav
            }
        });
    }
}
