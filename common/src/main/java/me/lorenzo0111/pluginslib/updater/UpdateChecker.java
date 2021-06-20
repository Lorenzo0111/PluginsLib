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

package me.lorenzo0111.pluginslib.updater;

import me.lorenzo0111.pluginslib.ChatColor;
import me.lorenzo0111.pluginslib.scheduler.IScheduler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Spigot Update Checker
 */
@SuppressWarnings("unused")
public class UpdateChecker {

    private boolean updateAvailable;
    private final IScheduler scheduler;
    private final int resourceId;
    private final String url;
    private final String prefix;
    private final String name;
    private final String api;
    private final String version;

    /**
     * @param scheduler Scheduler
     * @param version Version of the plugin
     * @param name Name of the plugin
     * @param resourceId Spigot resource id
     * @param url Download url of the plugin. <b>Not the api url</b>
     * @param prefix Prefix of the updater
     * @param api The url of the api. If set to null it will use the spigot api
     */
    public UpdateChecker(IScheduler scheduler, String version, String name, int resourceId, String url, @Nullable String prefix, @Nullable String api) {
        this.scheduler = scheduler;
        this.resourceId = resourceId;
        this.url = url;
        this.version = version;
        this.name = name;

        this.prefix = prefix == null ? "&8[&eRocketUpdater&8]" : prefix;
        this.api = api == null ? "https://api.spigotmc.org/legacy/update.php?resource=" : api;

        this.fetch();
    }

    /**
     * Fetch updates from spigot api
     */
    private void fetch() {
        scheduler.async(() -> {
            try (InputStream inputStream = new URL(api + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String version = scanner.next();

                    this.updateAvailable = !this.version.equalsIgnoreCase(version);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * @param entity Entity to send the update message
     */
    public void sendUpdateCheck(Audience entity) {
        if (updateAvailable) {
            String string = ChatColor.translateAlternateColorCodes('&', String.format("%s &7An update of %s is available. Download it from %s",this.prefix,name,url));
            entity.sendMessage(Component.text(string));
        }
    }

}