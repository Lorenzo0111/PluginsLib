package me.lorenzo0111.pluginslib.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitScheduler implements IScheduler {
    private final JavaPlugin plugin;

    public BukkitScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,runnable);
    }

    @Override
    public void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin,runnable);
    }
}
