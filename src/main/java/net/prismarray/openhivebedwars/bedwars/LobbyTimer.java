package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.ActionBar;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class LobbyTimer {
    Game game;
    BukkitScheduler scheduler;
    Countdown countdown;
    boolean refreshActionBar;
    boolean countdownActive;
    int requiredPlayerCount;

    public LobbyTimer(Game game) {
        this.game = game;
        scheduler = Bukkit.getServer().getScheduler();
        countdown = new Countdown(game.plugin, 60, 0, 1);
        refreshActionBar = false;
        countdownActive = false;
        requiredPlayerCount = 2;
    }

    public void update() { // slightly delayed due to slow player count update
        scheduler.scheduleSyncDelayedTask(game.plugin, new Runnable() {
            @Override
            public void run() {
                if (game.status == Status.LOBBY) {
                    if (Bukkit.getOnlinePlayers().size() < requiredPlayerCount) {
                        disableCountdown();
                    } else {
                        enableCountdown();
                    }
                }
            }
        }, 1);
    }

    public void enable() {
        refreshActionBar = true;
        waitingLoop();
    }

    public void disable() {
        refreshActionBar = false;
        countdown.stop();
    }

    private void enableCountdown() {
        if (!countdownActive) {
            countdownActive = true;
            countdown.reset();
            countdown.start();
            countdownLoop();
        }
    }

    private void disableCountdown() {
        if (countdownActive) {
            countdownActive = false;
            countdown.stop();
            waitingLoop();
        }
    }

    private void waitingLoop() {
        scheduler.scheduleSyncDelayedTask(game.plugin, new Runnable() {
            @Override
            public void run() {
                if (countdownActive || !refreshActionBar) { // return early if task should end
                    return;
                }
                ActionBar.sendToAll("§e" + String.valueOf(requiredPlayerCount - Bukkit.getOnlinePlayers().size()) + " players needed to start...");
                waitingLoop();
            }
        }, 10);
    }

    private void countdownLoop() {
        scheduler.scheduleSyncDelayedTask(game.plugin, new Runnable() {
            @Override
            public void run() {
                if (!countdownActive || !refreshActionBar) { // return early if task should end
                    return;
                }
                ActionBar.sendToAll("§aStarting game in §a§l" + String.valueOf(countdown.getCount()));
                countdownLoop();
            }
        }, 10);
    }
}
