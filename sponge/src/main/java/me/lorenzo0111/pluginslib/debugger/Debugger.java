package me.lorenzo0111.pluginslib.debugger;

import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;

import java.util.Map;

public class Debugger {
    private final Map<String,Object> keys;
    private final String name;
    private final String version;
    @Inject private Logger logger;

    /**
     * @param name Name of the plugin
     * @param version Version of the plugin
     * @param debuggable Debuggable to debug
     */
    public Debugger(String name, String version, Debuggable debuggable) {
        this.name = name;
        this.version = version;
        this.keys = debuggable.getKeys();
    }

    /**
     * Log all debug information
     */
    public void debug() {
        this.log("-----------[ RocketPlugins Debugger ]-----------");

        this.log("Server Information:");
        this.logData("Server Version", Sponge.getPlatform().getMinecraftVersion().getName());
        this.logData("Server Software", "Sponge");
        this.logData("Server Plugins", Sponge.getPluginManager().getPlugins());

        this.log("");

        this.log("Plugin Information");
        this.logData("Plugin Name", name);
        this.logData("Plugin Version", version);

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