package net.prismarray.openhivebedwars.bedwars.summoner;

import net.prismarray.openhivebedwars.bedwars.Game;
import net.prismarray.openhivebedwars.util.Format;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public abstract class SingleItemSummoner extends Summoner {

    ChatColor barColor;
    int barPointer;

    public SingleItemSummoner(Game game, Location location, ChatColor barColor) {
        super(game, location);

        this.barColor = barColor;
        barPointer = 0;

        updateTitle();
        updateSubtitle();
    }

    @Override
    public void tickProgressBar() {
        barPointer++;
        barPointer %= 10;

        updateSubtitle();
    }

    @Override
    protected void updateSubtitle() {
        String newSubtitle = "➤➤➤➤➤➤➤➤➤➤✚";
        newSubtitle = new StringBuilder(newSubtitle).insert(barPointer + 1, "§8").toString();

        setSubtitle(barColor + newSubtitle);
    }
}
