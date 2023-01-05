package net.prismarray.openhivebedwars.bedwars;

import net.prismarray.openhivebedwars.OpenHiveBedwars;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Countdown {
    protected final BukkitScheduler scheduler;

    private int start;
    private final int stop;
    private final int delay;
    private int count;
    private boolean stopped;
    private boolean killTask;

    public Countdown(int start, int stop, int delay) {
        scheduler = Bukkit.getServer().getScheduler();
        this.start = start;
        this.stop = stop;
        this.delay = delay;
        stopped = true;
        killTask = false;
        reset();
    }

    public void onDecrement() { // To be overridden

    }

    public void onCompletion() { // To be overridden

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

    public void setCount(int value) {
        count = value;
    }

    public void changeStart(int diff) {
        start = start + diff;
        count = count + diff;
    }

    private void decrement() {
        if (count - 1 <= stop) {
            count = stop;
            stop();
            onCompletion();
        } else {
            count--;
            onDecrement();
        }
    }

    private void countDown() {

        scheduler.scheduleSyncDelayedTask(OpenHiveBedwars.getInstance(), () -> {

            if (killTask) { // return early if task should end
                killTask = false;
                return;
            }

            if (!stopped) {
                decrement();
                countDown();
            }
        }, delay * 20L);
    }
}
