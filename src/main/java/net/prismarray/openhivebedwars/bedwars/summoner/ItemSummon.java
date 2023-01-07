package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemSummon {

    private final Summoner summoner;
    private final Material item;
    private final EnumParticle particleEffect;

    private boolean active;
    private int delay;


    public ItemSummon(Summoner summoner, Material item, EnumParticle particleEffect, int delay) {
        this.summoner = summoner;
        this.item = item;
        this.particleEffect = particleEffect;

        this.delay = delay;
        active = false;
    }

    public void enable() {
        active = true;
    }

    public void disable() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void tick(int gameTime) {
        if (!active) { // return early if summoner is inactive
            return;
        }

        if (gameTime % delay == 0) {
            summon();
        }
    }

    public void changeDelay(int diff) {
        delay += diff;
    }

    public void summon() {
        Location loc = new Location(
                summoner.getLocation().getWorld(),
                summoner.getLocation().getX(),
                summoner.getLocation().getY() - 0.75d,
                summoner.getLocation().getZ()
        );

        // Drop item stack
        loc.getWorld().dropItem(loc, new ItemStack(item)).setVelocity(new Vector(0, 0, 0));
        // todo particle effect
    }
}
