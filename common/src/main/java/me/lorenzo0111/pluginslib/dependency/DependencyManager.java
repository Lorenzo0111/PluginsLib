package me.lorenzo0111.pluginslib.dependency;

import io.github.slimjar.app.builder.ApplicationBuilder;
import io.github.slimjar.logging.ProcessLogger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class DependencyManager {
    private final ApplicationBuilder builder;

    public DependencyManager(String name, Path directory) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        this(name,new File(directory.toFile(), "libs"));
    }

    public DependencyManager(String name, File libsDirectory) throws ReflectiveOperationException, URISyntaxException, NoSuchAlgorithmException, IOException {
        libsDirectory.mkdirs();

        this.builder = ApplicationBuilder
                .appending(name)
                .downloadDirectoryPath(libsDirectory.toPath());
    }

    public void enableMirror() {
        builder.mirrorSelector((a, b) -> a); // https://github.com/slimjar/slimjar/issues/61#issuecomment-955549772
    }

    public void logger(ProcessLogger logger) {
        builder.logger(logger);
    }

    public long build() throws ReflectiveOperationException, IOException, URISyntaxException, NoSuchAlgorithmException {
        long before = System.currentTimeMillis();
        builder.build();
        long after = System.currentTimeMillis();
        return after - before;
    }

}
