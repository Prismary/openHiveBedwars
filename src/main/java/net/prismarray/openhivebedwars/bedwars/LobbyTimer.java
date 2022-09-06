package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.util.ActionBar;
import net.prismarray.openhivebedwars.util.SoundHandler;
import net.prismarray.openhivebedwars.util.Status;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitScheduler;

public class LobbyTimer {
    Game game;
    BukkitScheduler scheduler;
    Countdown countdown;
    boolean refreshActionBar;
    boolean countdownActive;
    boolean useRedCount;
    int requiredPlayerCount;

    public LobbyTimer(Game game) {
        this.game = game;
        scheduler = Bukkit.getServer().getScheduler();
        countdown = new Countdown(game.plugin, 60, 0, 1);
        refreshActionBar = false;
        countdownActive = false;
        useRedCount = false;
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
        }, 20);
    }

    private void countdownLoop() {
        scheduler.scheduleSyncDelayedTask(game.plugin, new Runnable() {
            @Override
            public void run() {
                if (!countdownActive || !refreshActionBar) { // return early if task should end
                    return;
                }
                tryGameConfirmation(); // Try to confirm game
                tryGameStart(); // Try to start game

                if (useRedCount) {
                    ActionBar.sendToAll("§aStarting game in §c§l" + String.valueOf(countdown.getCount()));
                    SoundHandler.globalPlayerSound(Sound.CLICK);
                } else {
                    ActionBar.sendToAll("§aStarting game in §a§l" + String.valueOf(countdown.getCount()));
                }
                countdownLoop();
            }
        }, 20);
    }

    private void tryGameConfirmation() {
        if (countdown.getCount() == 5) {
            useRedCount = true;
            game.confirmation();
        }
    }

    private void tryGameStart() {
        if (countdown.getCount() == 0) {
            disable();
            game.warmup();
        }
    }
}
