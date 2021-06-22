package me.lorenzo0111.pluginslib.conversation;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.text.Text;

/**
 * A conversation
 */
public abstract class Conversation {
    private final Component reason;
    private final Text escape;

    /**
     * @param reason Reason of the conversation. It will be sent to the player on conversation start
     * @param escape Escape sequence to stop conversation
     */
    public Conversation(Component reason, @Nullable Text escape) {
        this.reason = reason;
        this.escape = escape;
    }

    public abstract void handle(@Nullable Text input);

    public Component reason() { return reason; }
    public @Nullable Text escape() { return escape; }
}
