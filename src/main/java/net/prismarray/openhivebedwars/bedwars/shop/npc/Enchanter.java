package net.prismarray.openhivebedwars.bedwars.shop.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class Enchanter extends VillagerShop {

    public Enchanter(Location location) {
        super(location, "npc-enchanter-root", Villager.Profession.BUTCHER, "§c§lThe Enchanter");
    }
}
