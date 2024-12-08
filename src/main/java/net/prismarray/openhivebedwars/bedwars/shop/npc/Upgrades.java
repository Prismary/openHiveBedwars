package net.prismarray.openhivebedwars.bedwars.shop.npc;

import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class Upgrades extends VillagerShop {

    public Upgrades(Location location) {
        super(location, "npc-upgrades-root", Villager.Profession.LIBRARIAN, "§e§lUpgrades");
    }
}
