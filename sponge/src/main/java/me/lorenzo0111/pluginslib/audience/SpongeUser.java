package me.lorenzo0111.pluginslib.audience;

import com.google.inject.Inject;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.spongeapi.SpongeAudiences;
import org.spongepowered.api.command.CommandSource;

public class SpongeUser implements User<CommandSource> {
    private final CommandSource user;
    @Inject
    private SpongeAudiences adventure;

    public SpongeUser(CommandSource source) {
        this.user = source;
    }

    @Override
    public CommandSource player() {
        return user;
    }

    @Override
    public Audience audience() {
        return adventure.receiver(user);
    }

    @Override
    public boolean hasPermission(String permission) {
        return user.hasPermission(permission);
    }
}
