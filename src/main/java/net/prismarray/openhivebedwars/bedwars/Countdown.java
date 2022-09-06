package net.prismarray.openhivebedwars.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Countdown {
    private BukkitScheduler scheduler;
    private Plugin plugin;

    private int start;
    private int stop;
    private int delay;
    private int count;
    private boolean stopped;
    private boolean killTask;

    public Countdown(Plugin plugin, int start, int stop, int delay) {
        scheduler = Bukkit.getServer().getScheduler();
        this.plugin = plugin;
        this.start = start;
        this.stop = stop;
        this.delay = delay;
        stopped = true;
        killTask = false;
        reset();
    }

    public boolean start() {
        if (stopped) {
            stopped = false;
            countDown();
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        stopped = true;
        killTask = true;
    }

    public void reset() {
        count = start;
    }

    public int getCount() {
        return count;
    }

    private void decrement() {
        if (count - 1 <= stop) {
            count = stop;
            stop();
        } else {
            count--;
        }
    }

    private void countDown() {
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (killTask) { // return early if task should end
                    killTask = false;
                    return;
                }

                if (!stopped) {
                    decrement();
                    countDown();
                }
            }
        }, delay * 20);
    }
}