package net.prismarray.openhivebedwars.bedwars.shop.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class Specialist extends VillagerShop {

    public Specialist(Location location) {
        super(location, "npc-specialist-root", Villager.Profession.FARMER, "§6§lThe Specialist");
    }
}
