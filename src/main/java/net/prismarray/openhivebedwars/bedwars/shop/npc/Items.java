package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Items extends VillagerShop {

    public Items(Location location) {
        super(location);
    }

    @Override
    void setAppearance() {
        getVillager().setProfession(Villager.Profession.PRIEST);
        getVillager().setCustomName("§d§lItems");
    }

    @Override
    public void openShop(Player player) {
        InventoryGUIManager.openInventoryGUI("npc-items-root", player);
    }
}
