package me.lorenzo0111.pluginslib.scheduler;

public interface IScheduler {
    void async(Runnable runnable);
    void sync(Runnable runnable);
}
