package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.bedwars.shop.gui.ItemsGUI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Items extends VillagerShop {

    ItemsGUI guiBase;

    public Items(Location location) {
        super(location);

        guiBase = new ItemsGUI();
    }

    @Override
    void setAppearance() {
        getVillager().setProfession(Villager.Profession.PRIEST);
        getVillager().setCustomName("§d§lItems");
    }

    @Override
    public void openShop(Player player) {
        guiBase.open(player);
    }
}
