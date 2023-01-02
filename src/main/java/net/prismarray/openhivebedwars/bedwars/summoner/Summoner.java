package net.prismarray.openhivebedwars.bedwars.summoner;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.prismarray.openhivebedwars.bedwars.Countdown;
import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.bedwars.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Summoner {

    protected Game game;
    private Location location;

    private ArrayList<ItemSummon> summons;

    private Hologram title;
    private Hologram subtitle;

    public Summoner(Game game, Location location) {
        this.game = game;
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
