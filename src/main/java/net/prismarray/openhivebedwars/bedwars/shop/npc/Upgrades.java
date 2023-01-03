package net.prismarray.openhivebedwars.bedwars.shop.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class Upgrades extends VillagerShop {

    public Upgrades(Location location) {
        super(location);
    }

    @Override
    void setAppearance() {
        getVillager().setProfession(Villager.Profession.LIBRARIAN);
        getVillager().setCustomName("§e§lUpgrades");
    }

    @Override
    public void openShop(Player player) {

    }
}
