package me.cheracc.abilityitems.tools;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Scheduler {
    private final JavaPlugin plugin;
    private final BukkitScheduler scheduler;

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
        scheduler = plugin.getServer().getScheduler();
    }

    public void doNextTick(Runnable runnable) {
        scheduler.runTaskLater(plugin, runnable, 1L);
    }

    public void doAsynchronously(Runnable runnable) {
        scheduler.runTaskAsynchronously(plugin, runnable);
    }

    public BukkitTask doLater(Runnable runnable, int delayInSeconds) {
        return scheduler.runTaskLater(plugin, runnable, delayInSeconds * 20L);
    }

    public BukkitTask doRepeatedly(Runnable runnable, int secondsBetweenExecution) {
        return doRepeatedlyButWaitFirst(runnable, 0, secondsBetweenExecution);
    }

    public BukkitTask doRepeatedlyButWaitFirst(Runnable runnable, int delayInSeconds, int secondsBetweenExecution) {
        return scheduler.runTaskTimer(plugin, runnable, delayInSeconds * 20L, secondsBetweenExecution * 20L);
    }
}
