package me.lorenzo0111.pluginslib.conversation;

import com.google.inject.Inject;
import me.lorenzo0111.pluginslib.conversation.impl.ConversationListener;
import me.lorenzo0111.pluginslib.exceptions.LibraryException;
import net.kyori.adventure.platform.spongeapi.SpongeAudiences;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConversationUtil {
    private static Object plugin;
    private static Map<UUID,Conversation> pending;
    @Inject private static SpongeAudiences adventure;

    /**
     * Initialize the conversation factory
     * @param plugin Plugin
     */
    public static void init(Object plugin) {
        if (ConversationUtil.plugin != null)
            throw new LibraryException("ConversationUtil has been already initialized.");
        ConversationUtil.plugin = plugin;
        ConversationUtil.pending = new HashMap<>();
        Sponge.getEventManager().registerListeners(plugin,new ConversationListener());
    }

    /**
     * Start a conversation
     * @param player Player
     * @param conversation Conversation
     */
    public static void startConversation(Player player, Conversation conversation) {
        if (pending.containsKey(player.getUniqueId()))
            return;

        adventure.player(player).sendMessage(conversation.reason());
        pending.put(player.getUniqueId(),conversation);
    }

    /**
     * @return Pending conversations
     */
    public static Map<UUID, Conversation> getPending() {
        return pending;
    }
}
