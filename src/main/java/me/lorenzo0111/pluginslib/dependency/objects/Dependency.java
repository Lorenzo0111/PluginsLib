/*
 * This file is part of PluginsLib, licensed under the MIT License.
 *
 * Copyright (c) Lorenzo0111
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.lorenzo0111.pluginslib.dependency.objects;

import com.google.common.io.ByteStreams;
import me.lorenzo0111.pluginslib.exceptions.DownloadException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * A dependency file
 */
public class Dependency {
    private final String group;
    private final String artifact;
    private final String version;
    private final URL repository;

    /**
     * @param group GroupId
     * @param artifact ArtifactId
     * @param version Version
     * @param repository Repository url
     */
    public Dependency(String group, String artifact, String version, URL repository) {
        this.group = group;
        this.artifact = artifact;
        this.version = version;
        this.repository = repository;
    }

    /**
     * @param group GroupId
     * @param artifact ArtifactId
     * @param version Version
     * @param repository Repository url as string
     * @throws MalformedURLException if the url is invalid
     */
    public Dependency(String group, String artifact, String version, String repository) throws MalformedURLException {
        this(group,artifact,version, new URL(repository));
    }

    /**
     * Download a jar
     * @param path Folder
     * @param plugin Plugin
     * @return A completable future with the file path
     */
    public CompletableFuture<Path> download(Path path, JavaPlugin plugin) {
        CompletableFuture<Path> completableFuture = new CompletableFuture<>();

        Runnable runnable = () -> {
            Path file = path.resolve(this.getArtifact() + ".jar");

            if (Files.exists(file)) {
                completableFuture.complete(file);
                return;
            }

            try {
                URL artifact = new URL(this.repository.toString() + this.getMavenPath());
                URLConnection connection = artifact.openConnection();

                connection.setRequestProperty("User-Agent", "pluginslib");

                connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5));
                connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));

                try (InputStream in = connection.getInputStream()) {
                    byte[] bytes = ByteStreams.toByteArray(in);
                    if (bytes.length == 0) {
                        throw new DownloadException(this, "The stream is empty");
                    }

                    completableFuture.complete(Files.write(file, bytes));

                }

            } catch (IOException e) {
                throw new DownloadException(this, e.getMessage());
            }
        };

        plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(plugin,runnable);

        return completableFuture;
    }

    /**
     * Add a jar to the classpath
     * @param folder Folder
     * @param plugin Plugin
     * @return A completable future
     */
    public CompletableFuture<Void> install(Path folder, JavaPlugin plugin) {
        return this.download(folder,plugin)
                .thenAccept((file) -> {
                    try {
                        new DependencyInstaller(this,file)
                                .install(plugin);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * @return Dependency string
     */
    @Override
    public String toString() {
        return String.format("%s:%s:%s",group,artifact,version);
    }

    /**
     * @return GroupId
     */
    public String getGroup() {
        return group;
    }

    /**
     * @return ArtifactId
     */
    public String getArtifact() {
        return artifact;
    }

    /**
     * @return Version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return Repository url
     */
    public URL getRepository() {
        return repository;
    }

    /**
     * @return Maven formatted path
     */
    public String getMavenPath() {
        return String.format("%s/%s/%s/%s-%s.jar",
                group.replace(".", "/"),
                artifact,
                version,
                artifact,
                version
        );
    }
}
