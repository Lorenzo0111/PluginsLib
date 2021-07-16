package me.lorenzo0111.pluginslib.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit scheduler
 */
public class BukkitScheduler implements IScheduler {
    private final JavaPlugin plugin;

    /**
     * @param plugin Owner of the scheduler
     */
    public BukkitScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Run a task asynchronously
     * @param runnable Task to run
     */
    @Override
    public void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,runnable);
    }

    /**
     * Run a task synchronously
     * @param runnable Task to run
     */
    @Override
    public void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin,runnable);
    }
}
