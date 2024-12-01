package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Enchanter extends VillagerShop {

    public Enchanter(Location location) {
        super(location);
    }

    @Override
    void setAppearance() {
        getVillager().setProfession(Villager.Profession.BUTCHER);
        getVillager().setCustomName("§c§lThe Enchanter");
    }

    @Override
    public void openShop(Player player) {
        InventoryGUIManager.openInventoryGUI("npc-enchanter-root", player);
    }
}
