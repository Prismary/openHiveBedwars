package net.prismarray.openhivebedwars.bedwars.summoner;

import net.prismarray.openhivebedwars.bedwars.Countdown;
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

    public void summonStart() {
        summons.forEach(Countdown::start);
    }

    public Location getLocation() {
        return location;
    }

    public void tickProgressBar() {
        // To be overridden by single item summoners
        // Called on countdown decrement
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
