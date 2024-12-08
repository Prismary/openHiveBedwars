package net.prismarray.openhivebedwars.bedwars.shop.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class Items extends VillagerShop {

    public Items(Location location) {
        super(location, "npc-items-root", Villager.Profession.PRIEST, "§d§lItems");
    }
}
