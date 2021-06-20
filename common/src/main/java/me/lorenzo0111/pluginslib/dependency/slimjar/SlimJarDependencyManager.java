package me.lorenzo0111.pluginslib.dependency.slimjar;

import io.github.slimjar.app.builder.ApplicationBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class SlimJarDependencyManager {
    private final ApplicationBuilder builder;

    public SlimJarDependencyManager(String name, Path directory) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        this(name,new File(directory.toFile(), "libs"));
    }

    public SlimJarDependencyManager(String name, File libsDirectory) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        libsDirectory.mkdirs();

        this.builder = ApplicationBuilder
                .appending(name)
                .downloadDirectoryPath(libsDirectory.toPath());
    }

    public long build() throws ReflectiveOperationException, IOException, URISyntaxException, NoSuchAlgorithmException {
        long before = System.currentTimeMillis();
        builder.build();
        long after = System.currentTimeMillis();
        return after - before;
    }

}
