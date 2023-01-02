package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.prismarray.openhivebedwars.bedwars.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemSummon extends Countdown {

    private Summoner summoner;
    private boolean active;

    private Material item;
    private EnumParticle particleEffect;


    public ItemSummon(Summoner summoner, Material item, EnumParticle particleEffect, int maxCount, int delay) {
        super(summoner.game.plugin, maxCount, 0, delay);

        this.summoner = summoner;
        this.item = item;
        this.particleEffect = particleEffect;

        active = false;
    }

    public void enable() {
        active = true;
    }

    public void disable() {
        active = false;
    }

    public void summon() {
        reset();
        start();

        if (!active) {
            return;
        }

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

    @Override
    public void onDecrement() {
        summoner.tickProgressBar();
    }

    @Override
    public void onCompletion() {
        summon();
        summoner.tickProgressBar();
    }
}
