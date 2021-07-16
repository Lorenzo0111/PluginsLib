package me.lorenzo0111.pluginslib.command;

import me.lorenzo0111.pluginslib.ChatColor;
import me.lorenzo0111.pluginslib.audience.BukkitUser;
import me.lorenzo0111.pluginslib.command.annotations.AnyArgument;
import me.lorenzo0111.pluginslib.command.annotations.NoArguments;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * Command
 */
public abstract class Command extends ICommand<JavaPlugin> implements CommandExecutor {

    /**
     * @param plugin        Owner of the command
     * @param command       Name of the command ( /name )
     * @param customization <b>Optional</b> Command customization
     */
    public Command(JavaPlugin plugin, String command, @Nullable Customization customization) {
        super(plugin, command, customization);
    }

    /**
     * @param command Register the command in the plugin manager
     */
    @Override
    public void register(String command) {
        Objects.requireNonNull(this.getPlugin().getCommand(command)).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        BukkitUser user = new BukkitUser(sender);

        if (this.getCustomization().getHeader() != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getCustomization().getHeader()));
        }

        if (this.getPermission() != null && this.getMessage() != null && !sender.hasPermission(this.getPermission())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getMessage()));
            return true;
        }

        if (args.length > 0){
            for (SubCommand subcommand : subcommands) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.perform(user, args);
                    return true;
                }
            }

            Optional<SubCommand> anyArgs = this.findSubcommand(AnyArgument.class);

            if (anyArgs.isPresent()) {
                anyArgs.get().perform(user,args);
                return true;
            }

        } else {
            Optional<SubCommand> noArgsCommand = this.findSubcommand(NoArguments.class);

            if (noArgsCommand.isPresent()) {
                noArgsCommand.get().perform(user,args);
                return true;
            }

            String noArgs = this.getCustomization().getNoArgs(command.getName());

            if (noArgs != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noArgs));
            }
            return true;
        }

        String notFound = this.getCustomization().getNotFound(command.getName());

        if (notFound != null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', notFound));
        }

        return true;
    }
}
