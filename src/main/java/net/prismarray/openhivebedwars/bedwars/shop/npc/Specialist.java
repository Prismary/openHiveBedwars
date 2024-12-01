package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Specialist extends VillagerShop {

    public Specialist(Location location) {
        super(location);
    }

    @Override
    void setAppearance() {
        getVillager().setProfession(Villager.Profession.FARMER);
        getVillager().setCustomName("§6§lThe Specialist");
    }

    @Override
    public void openShop(Player player) {
        InventoryGUIManager.openInventoryGUI("npc-specialist-root", player);
    }
}
