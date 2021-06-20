package me.lorenzo0111.pluginslib.scheduler;

import org.spongepowered.api.Sponge;

public class SpongeScheduler implements IScheduler {
    private final Object plugin;

    public SpongeScheduler(Object plugin) {
        this.plugin = plugin;
    }

    @Override
    public void async(Runnable runnable) {
        Sponge.getScheduler()
                .createTaskBuilder()
                .async()
                .execute(runnable)
                .submit(plugin);
    }

    @Override
    public void sync(Runnable runnable) {
        Sponge.getScheduler()
                .createTaskBuilder()
                .execute(runnable)
                .submit(plugin);
    }
}
