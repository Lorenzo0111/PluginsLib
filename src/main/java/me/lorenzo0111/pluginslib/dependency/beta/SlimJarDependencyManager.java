package me.lorenzo0111.pluginslib.dependency.beta;

import io.github.slimjar.app.builder.ApplicationBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class SlimJarDependencyManager {
    private final ApplicationBuilder builder;

    public SlimJarDependencyManager(JavaPlugin plugin) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        this(plugin,new File(plugin.getDataFolder(), "libs"));
    }

    public SlimJarDependencyManager(JavaPlugin plugin, File directory) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        directory.mkdirs();

        this.builder = ApplicationBuilder
                .appending(plugin.getName())
                .downloadDirectoryPath(directory.toPath());
    }

    public long build() throws ReflectiveOperationException, IOException, URISyntaxException, NoSuchAlgorithmException {
        long before = System.currentTimeMillis();
        builder.build();
        long after = System.currentTimeMillis();
        return after - before;
    }

}
