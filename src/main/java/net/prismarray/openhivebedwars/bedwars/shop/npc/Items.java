package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.bedwars.shop.gui.npc_items.ItemsGUI;
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
        (new ItemsGUI()).open(player);
    }
}
