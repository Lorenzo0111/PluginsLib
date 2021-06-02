package me.lorenzo0111.pluginslib.debugger;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A class that can be debugged with {@link Debugger}
 */
public interface Debuggable {
    /**
     * @return All keys to debug
     */
    @Nullable Map<String,Object> getKeys();

    /**
     * @return A plugin
     */
    JavaPlugin getPlugin();
}
