package net.prismarray.openhivebedwars.bedwars.shop.npc;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.prismarray.openhivebedwars.bedwars.shop.ShopManager;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public abstract class VillagerShop extends Shop {

    private final Villager entity;

    public VillagerShop(Location location) {
        entity = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        entity.setAdult();
        entity.setCanPickupItems(false);
        disableAI();
        setAppearance(); // Set custom name and profession
        entity.setCustomNameVisible(true);

        // Add entity to list of villager shops
        ShopManager.getInstance().addShop(this);
    }

    public Entity getEntity() {
        return entity;
    }

    public Villager getVillager() {
        return entity;
    }

    abstract void setAppearance();

    private void disableAI() {
        // Thanks to illuminator3 on spigotmc.org!

        net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) entity).getHandle();
        NBTTagCompound comp = new NBTTagCompound();
        nmsVil.c(comp);
        comp.setByte("NoAI", (byte) 1);
        nmsVil.f(comp);
        nmsVil.b(true);
    }
}
