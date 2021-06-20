package me.lorenzo0111.pluginslib.audience;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitUser implements User<CommandSender> {
    private final CommandSender sender;

    public BukkitUser(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public CommandSender player() {
        return sender;
    }

    @Override
    public Audience audience() {
        if (!BukkitAudienceManager.initialized())
            BukkitAudienceManager.init(JavaPlugin.getProvidingPlugin(BukkitUser.class));
        return BukkitAudienceManager.audience(sender);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }
}
