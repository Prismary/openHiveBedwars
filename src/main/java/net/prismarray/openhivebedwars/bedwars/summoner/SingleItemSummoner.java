package net.prismarray.openhivebedwars.bedwars.summoner;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public abstract class SingleItemSummoner extends Summoner {

    private final ChatColor barColor;
    private int barPointer;

    public SingleItemSummoner(Location location, ChatColor barColor) {
        super(location);

        this.barColor = barColor;
        barPointer = 0;

        updateTitle();
        disable();
    }

    @Override
    public void tickProgressBar() {
        barPointer++;
        barPointer %= 10;

        updateSubtitle();
    }

    @Override
    public void enable() {
        getSummons().get(0).enable();
        updateSubtitle();
    }

    @Override
    public void disable() {
        getSummons().get(0).disable();
        setSubtitle("§c-- INACTIVE --");
    }

    @Override
    protected void updateSubtitle() {
        String newSubtitle = "➤➤➤➤➤➤➤➤➤➤✚";
        newSubtitle = new StringBuilder(newSubtitle).insert(barPointer + 1, "§8").toString();

        setSubtitle(barColor + newSubtitle);
    }
}
