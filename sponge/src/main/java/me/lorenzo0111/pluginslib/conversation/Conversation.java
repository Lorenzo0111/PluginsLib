package me.lorenzo0111.pluginslib.conversation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 * A conversation
 */
public interface Conversation {

    void handle(Player player, @Nullable Text input);

    /**
     * @return Reason of the conversation. It will be sent to the player on conversation start
     */
    Component reason();

    /**
     * @return Escape sequence to stop conversation
     */
    @Nullable Text escape();
}
