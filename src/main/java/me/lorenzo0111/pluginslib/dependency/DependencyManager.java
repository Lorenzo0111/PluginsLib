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

package me.lorenzo0111.pluginslib.dependency;

import me.lorenzo0111.pluginslib.dependency.objects.Dependency;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Used to manage external jar dependencies
 */
public class DependencyManager {
    private final List<Dependency> dependencies = new ArrayList<>();
    private final JavaPlugin plugin;
    private final Path folder;

    /**
     * @param plugin Owner of the manager
     * @param folder Libs folder
     * @param dependencies A list of dependencies to download
     */
    public DependencyManager(JavaPlugin plugin, File folder, List<Dependency> dependencies) {
        this.dependencies.addAll(dependencies);
        this.plugin = plugin;
        this.folder = folder.toPath();
    }

    /**
     * @param plugin Owner of the manager
     * @param folder Libs folder
     * @param dependencies A list of dependencies to download
     */
    public DependencyManager(JavaPlugin plugin, File folder, Dependency... dependencies) {
        this(plugin,folder, Arrays.asList(dependencies));
    }

    /**
     * @param dependency Dependency to add
     */
    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    /**
     * @return All dependencies
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    /**
     * Install all dependencies
     * @return A completable future used to download them
     */
    public CompletableFuture<Void> installAll() {
        return CompletableFuture.allOf(dependencies.stream()
                .map((dependency) -> dependency.install(folder,plugin))
                .toArray(CompletableFuture[]::new));
    }
}
