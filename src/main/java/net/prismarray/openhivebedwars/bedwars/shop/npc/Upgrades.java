package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.shop.gui.npc_upgrades.UpgradesRootGUI;
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
        (new UpgradesRootGUI(Game.getTeamHandler().getPlayerTeam(player).getColor())).open(player);
    }
}
