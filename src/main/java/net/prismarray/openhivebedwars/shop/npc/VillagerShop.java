package net.prismarray.openhivebedwars.shop.npc;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.prismarray.openhivebedwars.shop.ShopManager;
import net.prismarray.openhivebedwars.gui.InventoryGUIManager;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class VillagerShop extends Shop {

    private final Villager entity;
    private final String inventoryGUI;

    public VillagerShop(@Nonnull Location location, @Nonnull String inventoryGUI) {
        this(location, inventoryGUI, null, null);
    }

    public VillagerShop(@Nonnull Location location, @Nonnull String inventoryGUI, @Nullable Villager.Profession profession, @Nullable String customName) {

        this.inventoryGUI = inventoryGUI;
        this.entity = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        entity.setAdult();
        entity.setCanPickupItems(false);
        disableAI();

        if (Objects.nonNull(profession)) {
            getVillager().setProfession(profession);
        }

        if (Objects.nonNull(customName)) {
            getVillager().setCustomName(customName);
        }

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

    private void disableAI() {
        // Thanks to illuminator3 on spigotmc.org!

        net.minecraft.server.v1_8_R3.Entity nmsVil = ((CraftEntity) entity).getHandle();
        NBTTagCompound comp = new NBTTagCompound();
        nmsVil.c(comp);
        comp.setByte("NoAI", (byte) 1);
        nmsVil.f(comp);
        nmsVil.b(true);
    }

    @Override
    public void openShop(Player player) {
        InventoryGUIManager.openInventoryGUI(inventoryGUI, player);
    }
}
