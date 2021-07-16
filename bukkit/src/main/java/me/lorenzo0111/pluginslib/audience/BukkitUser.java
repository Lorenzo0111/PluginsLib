package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A bukkit user
 */
public class BukkitUser implements User<CommandSender> {
    private final CommandSender sender;

    /**
     * @param sender Entity to convert
     */
    public BukkitUser(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * @return the entity
     */
    @Override
    public CommandSender player() {
        return sender;
    }

    /**
     * @return the audience
     */
    @Override
    public Audience audience() {
        if (!BukkitAudienceManager.initialized())
            BukkitAudienceManager.init(JavaPlugin.getProvidingPlugin(BukkitUser.class));
        return BukkitAudienceManager.audience(sender);
    }

    /**
     * @param permission Permission to check
     * @return true if the player has that permission
     */
    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
