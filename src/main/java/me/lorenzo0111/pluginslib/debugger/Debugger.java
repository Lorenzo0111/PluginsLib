package me.lorenzo0111.pluginslib.debugger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Debugger {
    private final Map<String,Object> keys;
    private final JavaPlugin plugin;
    private final Logger logger;

    /**
     * @param plugin Owner of the debugger
     * @param keys Other keys to debug
     */
    public Debugger(JavaPlugin plugin,@Nullable Map<String,Object> keys) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.keys = keys;
    }

    /**
     * Log all debug information
     */
    public void debug() {
        this.log("-----------[ RocketPlugins Debugger ]-----------");

        this.log("Server Information:");
        this.logData("Server Version", Bukkit.getServer().getBukkitVersion());
        this.logData("Server Software", Bukkit.getServer().getVersion());
        this.logData("Server Plugins", Arrays.toString(Bukkit.getServer().getPluginManager().getPlugins()));

        this.log("");

        this.log("Plugin Information");
        this.logData("Plugin Name", plugin.getDescription().getName());
        this.logData("Plugin Version", plugin.getDescription().getVersion());

        this.log("");

        this.log("Other Information");

        keys.forEach(this::logData);

        this.log("-----------[ RocketPlugins Debugger ]-----------");
    }

    /**
     * @param key Key of the item
     * @param data Data
     */
    private void logData(String key, Object data) {
        this.log(key + ": " + data);
    }

    /**
     * @param message Log a message
     */
    private void log(String message) {
        logger.info(message);
    }

    /**
     * @return All other keys to debug
     */
    @Nullable
    public Map<String, Object> getKeys() {
        return keys;
    }
}

