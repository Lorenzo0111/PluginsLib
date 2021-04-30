/*
 * This file is part of PluginsLib, licensed under the MIT License.
 *
 * Copyright (c) Lorenzo0111
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

