package net.prismarray.openhivebedwars.bedwars;


import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

    private final ArmorStand entity;

    public Hologram(Location location) {
        entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        entity.setMarker(true);
        entity.setGravity(false);
        entity.setVisible(false);
    }

    public void setContent(String newContent) {
        entity.setCustomName(newContent);
        entity.setCustomNameVisible(true);
    }

    public void show() {
        entity.setVisible(true);
    }

    public void hide() {
        entity.setVisible(false);
    }
}
