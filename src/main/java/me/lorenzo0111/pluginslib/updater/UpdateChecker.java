package me.lorenzo0111.pluginslib.updater;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Spigot Update Checker
 */
@SuppressWarnings("unused")
public class UpdateChecker {

    private boolean updateAvailable;
    private final JavaPlugin plugin;
    private final int resourceId;
    private final String url;
    private final String prefix;

    /**
     * @param plugin Plugin to check
     * @param resourceId Spigot resource id
     * @param url Download url of the plugin. <b>Not the api url</b>
     * @param prefix Prefix of the updater
     */
    public UpdateChecker(JavaPlugin plugin, int resourceId, String url,@Nullable String prefix) {
        this.plugin = plugin;
        this.resourceId = resourceId;
        this.url = url;

        this.prefix = prefix == null ? "&8[&eRocketUpdater&8]" : prefix;

        this.fetch();
    }

    /**
     * Fetch updates from spigot api
     */
    private void fetch() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String version = scanner.next();

                    this.updateAvailable = !this.plugin.getDescription().getVersion().equalsIgnoreCase(version);
                }
            } catch (IOException exception) {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

    /**
     * @param entity Entity to send the update message
     */
    public void sendUpdateCheck(CommandSender entity) {
        if (updateAvailable) {
            entity.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s &7An update of %s is available. Download it from %s",this.prefix,plugin.getDescription().getName(),url)));
        }
    }

}