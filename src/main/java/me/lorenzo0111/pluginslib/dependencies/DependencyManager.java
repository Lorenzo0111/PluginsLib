package me.lorenzo0111.pluginslib.dependencies;

import me.bristermitten.pdm.PluginDependencyManager;
import me.bristermitten.pdm.SpigotDependencyManager;
import me.bristermitten.pdmlibs.artifact.Artifact;
import me.bristermitten.pdmlibs.artifact.ArtifactFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * DependencyManager
 */
public class DependencyManager {
    private final JavaPlugin plugin;
    private final PluginDependencyManager dependencyManager;
    private final List<Artifact> artifacts = new ArrayList<>();
    private final ArtifactFactory factory = new ArtifactFactory();

    /**
     * @param plugin Plugin
     */
    public DependencyManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dependencyManager = SpigotDependencyManager.of(plugin);
    }

    /**
     * @param name Name of the repository, it can be used in {@link DependencyManager#addDependency(String, String, String, String)}
     * @param url Url of the repository
     */
    public void addRepository(String name, String url) {
        dependencyManager.addRepository(name,url);
    }

    /**
     * @param group GroupId
     * @param artifact Artifact name
     * @param version Version
     * @param repoName Repository name
     */
    public void addDependency(String group, String artifact, String version, String repoName) {
        artifacts.add(factory.toArtifact(group, artifact, version, repoName, null));
    }

    /**
     * Download and load dependencies
     * @param then What to run when complete
     */
    public void init(Runnable then) {
        artifacts.forEach(dependencyManager::addRequiredDependency);
        dependencyManager
                .loadAllDependencies()
                .thenRun(then);
    }

    /**
     * Download and load dependencies
     */
    public void init() {
        this.init(() -> plugin.getLogger().info("Dependencies downloaded."));
    }

    public PluginDependencyManager getDependencyManager() {
        return dependencyManager;
    }
}
