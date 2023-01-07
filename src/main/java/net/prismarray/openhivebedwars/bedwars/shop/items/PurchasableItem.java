package net.prismarray.openhivebedwars.bedwars.shop.items;

import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionHandler;
import net.prismarray.openhivebedwars.gui.InventoryGUIActionListener;
import net.prismarray.openhivebedwars.gui.InventoryGUIBase;
import net.prismarray.openhivebedwars.gui.InventoryGUIItem;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUILeftOrShiftLeftClickAction;
import net.prismarray.openhivebedwars.gui.actions.InventoryGUIRightOrShiftRightClickAction;
import net.prismarray.openhivebedwars.util.Currency;
import net.prismarray.openhivebedwars.util.ItemNameBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class PurchasableItem extends InventoryGUIItem {

    public PurchasableItem(InventoryGUIBase gui, int slot, Material material, short damage, int amount, boolean enchanted, String name,
                           int cost, Currency currency, boolean showFavStatus, boolean isFavorite) {
        this(gui, slot, material, damage, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, null, new ItemStack(material, amount, damage));
    }

    public PurchasableItem(InventoryGUIBase gui, int slot, Material material, short damage, int amount, boolean enchanted, String name,
                           int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore) {
        this(gui, slot, material, damage, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, bonusLore, new ItemStack(material, amount, damage));
    }

    public PurchasableItem(InventoryGUIBase gui, int slot, Material material, short damage, int amount, boolean enchanted, String name,
                           int cost, Currency currency, boolean showFavStatus, boolean isFavorite, ItemStack purchaseItem) {
        this(gui, slot, material, damage, amount, enchanted, name, cost, currency, showFavStatus, isFavorite, null, purchaseItem);
    }

    public PurchasableItem(InventoryGUIBase gui, int slot, Material material, short damage, int amount, boolean enchanted, String name,
                           int cost, Currency currency, boolean showFavStatus, boolean isFavorite, @Nullable String[] bonusLore, ItemStack purchaseItem) {
        super(
                gui,
                slot,
                material,
                damage,
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
