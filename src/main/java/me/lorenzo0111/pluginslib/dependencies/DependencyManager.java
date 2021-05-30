package me.lorenzo0111.pluginslib.dependencies;

import me.bristermitten.pdm.PluginDependencyManager;
import me.bristermitten.pdm.SpigotDependencyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DependencyManager {
    private final JavaPlugin plugin;
    private PluginDependencyManager dependencyManager;

    public DependencyManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init(Runnable then) {
        dependencyManager = SpigotDependencyManager.of(plugin);

        dependencyManager
                .loadAllDependencies()
                .thenRun(then);
    }

    public void init() {
        this.init(() -> plugin.getLogger().info("Dependencies downloaded."));
    }

    public PluginDependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
