package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;

public interface User<T> {
    T player();
    Audience audience();
    boolean hasPermission(String permission);
}
