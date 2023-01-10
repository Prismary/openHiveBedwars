package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Stopwatch {

    private final boolean sendOnIncrementOnStart;

    private int start;
    private int count;

    private boolean paused;
    private boolean killTask;


    public Stopwatch(int start, boolean sendOnIncrementOnStart) {
        this.sendOnIncrementOnStart = sendOnIncrementOnStart;

        this.start = start;
        count = start;
        paused = true;
    }

    public void start() {
        paused = false;
        if (sendOnIncrementOnStart) {
            onIncrement();
        }
        startIncrementTask();
    }

    public void set(int newCount) {
        count = newCount;
    }

    public void setStart(int newStart) {
        start = newStart;
    }

    public void pause() {
        paused = true;
        killTask();
    }

    public boolean isPaused() {
        return paused;
    }

    public void reset() {
        pause();
        count = start;
    }

    public int getCount() {
        return count;
    }

    protected void onIncrement() {
        // To be overridden
    }

    private void startIncrementTask() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OpenHiveBedwars.getInstance(), () -> {
            if (killTask) {
                return;
            }
            count++;
            onIncrement();
            startIncrementTask();
        }, 20);
    }

    private void killTask() {
        killTask = true;
        Bukkit.getScheduler().scheduleSyncDelayedTask(OpenHiveBedwars.getInstance(), () -> {
            killTask = false;
        }, 20);
    }
}
