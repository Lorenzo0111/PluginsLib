package me.lorenzo0111.pluginslib.conversation.impl;

import me.lorenzo0111.pluginslib.conversation.Conversation;
import me.lorenzo0111.pluginslib.conversation.ConversationUtil;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class ConversationListener {

    @Listener
    public void onChat(MessageChannelEvent.Chat event, @Root Player player) {
        Conversation conversation = ConversationUtil
                .getPending()
                .get(player.getUniqueId());

        if (conversation == null)
            return;

        ConversationUtil.getPending().remove(player.getUniqueId(),conversation);

        if (event.getMessage().toText().equals(conversation.escape())) {
            return;
        }

        conversation.handle(player,event.getMessage());
    }

}
