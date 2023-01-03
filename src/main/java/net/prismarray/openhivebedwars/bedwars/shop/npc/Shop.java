package net.prismarray.openhivebedwars.bedwars.shop.npc;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class Shop {

    public abstract Entity getEntity();

    public abstract void openShop(Player player);

}
