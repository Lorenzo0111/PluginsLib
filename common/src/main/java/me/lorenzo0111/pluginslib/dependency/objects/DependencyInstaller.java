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

import me.lorenzo0111.pluginslib.dependency.loader.ClassLoaderReflection;
import me.lorenzo0111.pluginslib.scheduler.IScheduler;

import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.nio.file.Path;

/**
 * Install a dependency to the classpath
 */
public class DependencyInstaller {
    private final Dependency dependency;
    private final Path dependencyFile;

    /**
     * @param dependency Dependency instance
     * @param dependencyFile Dependency file, obtained with {@link Dependency#download(Path, IScheduler)}
     */
    public DependencyInstaller(Dependency dependency, Path dependencyFile) {
        this.dependency = dependency;
        this.dependencyFile = dependencyFile;
    }

    /**
     * Install the dependency
     * @param plugin Plugin
     * @throws MalformedURLException when something went wrong
     */
    public void install(Class<?> plugin) throws MalformedURLException {
        URLClassLoader loader = (URLClassLoader) plugin.getClassLoader();
        ClassLoaderReflection.addURL(loader, dependencyFile.toUri().toURL());
    }

    /**
     * @return The dependency
     */
    public Dependency getDependency() {
        return dependency;
    }

    /**
     * @return The dependency file
     */
    public Path getDependencyFile() {
        return dependencyFile;
    }
}
