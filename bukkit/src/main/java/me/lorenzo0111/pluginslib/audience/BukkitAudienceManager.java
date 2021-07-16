package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Audiences manager for bukkit
 */
public final class BukkitAudienceManager implements Listener {
    private static BukkitAudiences audiences;

    private BukkitAudienceManager() {

    }

    /**
     * @param plugin Plugin to use
     */
    public static void init(JavaPlugin plugin) {
        audiences = BukkitAudiences.create(plugin);
    }

    /**
     * Shutdown the system
     */
    public static void shutdown() {
        audiences.close();
    }

    /**
     * @param sender Sender to convert
     * @return An audience
     */
    public static Audience audience(CommandSender sender) {
        return audiences.sender(sender);
    }

    /**
     * @return true if the manager is initialized
     */
    public static boolean initialized() {
        return audiences != null;
    }
}
