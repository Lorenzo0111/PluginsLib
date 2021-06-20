package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitAudienceManager implements Listener {
    private static BukkitAudiences audiences;
    private static JavaPlugin plugin;

    private BukkitAudienceManager() {

    }

    @EventHandler
    public void onUnload(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals(plugin.getName())) {
            shutdown();
        }
    }

    public static void init(JavaPlugin plugin) {
        BukkitAudienceManager.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new BukkitAudienceManager(),plugin);
        audiences = BukkitAudiences.create(plugin);
    }

    public static void shutdown() {
        audiences.close();
    }

    public static Audience audience(CommandSender sender) {
        return audiences.sender(sender);
    }

    public static boolean initialized() {
        return audiences != null;
    }
}
