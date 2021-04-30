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

package me.lorenzo0111.pluginslib.command;

import me.lorenzo0111.pluginslib.command.annotations.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Command handler
 */
@SuppressWarnings("unused")
public abstract class Command implements CommandExecutor {
    protected String permission = null;
    protected String message = null;
    protected Customization customization;

    protected final JavaPlugin plugin;
    protected final List<SubCommand> subcommands = new ArrayList<>();

    /**
     * @param plugin Owner of the command
     * @param command Name of the command ( /name )
     * @param customization <b>Optional</b> Command customization
     */
    public Command(JavaPlugin plugin, String command, @Nullable Customization customization) {
        this.plugin = plugin;
        this.customization = customization;
        Objects.requireNonNull(plugin.getCommand(command), "Unable to register command, please make sure that you've added it to the plugin.yml").setExecutor(this);

        try {

            final Constructor<? extends Command> constructor = this.getClass().getConstructor();

            if (constructor.isAnnotationPresent(Permission.class)) {
                final Permission annotation = constructor.getAnnotation(Permission.class);

                this.permission = annotation.value();
                this.message = annotation.msg();
            }

        } catch (NoSuchMethodException e) {
            plugin.getLogger().log(Level.SEVERE,"Unable to check for Permission annotation.", e);
        }

    }

    /**
     * @return Owner of the command
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if (this.customization.getHeader() != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.customization.getHeader()));
        }

        if (this.permission != null && this.message != null && !sender.hasPermission(this.permission)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.message));
            return true;
        }

        if (args.length > 0){
            for (SubCommand subcommand : subcommands) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.perform(sender, args);
                    return true;
                }
            }

        } else {
            String noArgs = this.customization.getNoArgs(command.getName());

            if (noArgs != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noArgs));
            }
            return true;
        }

        String notFound = this.customization.getNotFound(command.getName());

        if (notFound != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', notFound));
        }

        return true;
    }

    /**
     * @param subCommand SubCommand to add
     */
    private void addSubcommand(SubCommand subCommand) {
        this.subcommands.add(subCommand);

        try {
            // Check if the subcommand has a permission

            Method method = subcommands.getClass().getMethod("handleSubcommand");

            if (method.isAnnotationPresent(Permission.class)) {

                final Permission annotation = method.getAnnotation(Permission.class);

                subCommand.setPermission(annotation.value(),annotation.msg());

            }

        } catch (NoSuchMethodException e) {
            plugin.getLogger().log(Level.SEVERE,"Unable to check for Permission annotation.", e);
        }
    }
}
