package me.lorenzo0111.pluginslib.debugger;

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
}

