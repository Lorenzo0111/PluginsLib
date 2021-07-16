package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;

/**
 * @param <T> A user
 */
public interface User<T> {
    /**
     * @return Player instance of the user
     */
    T player();
    /**
     * @return Audience instance of the user
     */
    Audience audience();

    /**
     * @param permission Permission to check
     * @return true if the player has that permission
     */
    boolean hasPermission(String permission);
}
