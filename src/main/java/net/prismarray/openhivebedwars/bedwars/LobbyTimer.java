package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.ActionBar;
import net.prismarray.openhivebedwars.util.SoundHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class LobbyTimer extends Countdown {
    private final Game game;
    private boolean useRedCount;
    private final int requiredPlayerCount;
    private String actionBarContent;

    public LobbyTimer(Game game) {
        super(game.plugin, 61, 0, 1);

        this.game = game;
        useRedCount = false;
        requiredPlayerCount = 3; // expected to be 12
        actionBarContent = "";
    }


    private void updateActionBarText() {
        if (Bukkit.getOnlinePlayers().size() < requiredPlayerCount) { // Waiting for players
            actionBarContent = "§e" + (requiredPlayerCount - Bukkit.getOnlinePlayers().size()) + " players needed to start...";
        } else {
            if (useRedCount) { // Red countdown
                actionBarContent = "§aStarting game in §c§l" + getCount();
            } else { // Regular countdown
                actionBarContent = "§aStarting game in §a§l" + getCount();
            }
        }

    }

    @Override
    public void onDecrement() {
        if (Bukkit.getOnlinePlayers().size() < requiredPlayerCount) {
            setCount(11); // expected to be 61
        }

        if (getCount() == 5) {
            useRedCount = true;
            game.confirmation();
        }

        updateActionBarText();
        ActionBar.sendToAll(actionBarContent);
        if (useRedCount) {
            SoundHandler.globalPlayerSound(Sound.CLICK);
        }
    }

    @Override
    public void onCompletion() {
        game.warmup();
    }
}
