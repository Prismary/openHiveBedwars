package net.prismarray.openhivebedwars.bedwars.summoner;

import net.prismarray.openhivebedwars.bedwars.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;

public abstract class Summoner {

    private final Location location;

    private final ArrayList<ItemSummon> summons;

    private final Hologram title;
    private final Hologram subtitle;

    public Summoner(Location location) {
        this.location = location;

        summons = new ArrayList<>();

        title = new Hologram(new Location(
                location.getWorld(),
                location.getX(),
                location.getY() - 0.5d,
                location.getZ()
        ));

        subtitle = new Hologram(new Location(
                location.getWorld(),
                location.getX(),
                location.getY() - 0.75d,
                location.getZ()
        ));

        summonSetup();
    }

    public void tickSummons(int gameTime) {
        summons.forEach(summon -> summon.tick(gameTime));
    }

    public Location getLocation() {
        return location;
    }

    public abstract void enable();

    public abstract void disable();

    public void tickProgressBar() {
        // optional method to be overridden by summoners featuring a progress bar
    }

    protected ArrayList<ItemSummon> getSummons() {
        return summons;
    }

    protected abstract void summonSetup();

    protected abstract void updateTitle();

    protected abstract void updateSubtitle();

    protected void setTitle(String newTitle) {
        title.setContent(newTitle);
    }

    protected void setSubtitle(String newSubtitle) {
        subtitle.setContent(newSubtitle);
    }
}
