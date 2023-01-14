package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.bedwars.shop.gui.npc_specialist.SpecialistRootGUI;
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
        new SpecialistRootGUI().open(player);
    }
}
