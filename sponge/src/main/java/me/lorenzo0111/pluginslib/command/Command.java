package me.lorenzo0111.pluginslib.command;

import me.lorenzo0111.pluginslib.ChatColor;
import me.lorenzo0111.pluginslib.audience.SpongeUser;
import me.lorenzo0111.pluginslib.command.annotations.AnyArgument;
import me.lorenzo0111.pluginslib.command.annotations.NoArguments;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Command extends ICommand<Object> implements CommandExecutor {
    private final List<String> schema;

    public Command(Object plugin, String command, List<String> argsSchema, @Nullable Customization customization) {
        super(plugin, command, customization);
        this.schema = argsSchema;
    }

    @Override
    public void register(String command) {
        List<CommandElement> elements = new ArrayList<>();
        elements.add(GenericArguments.optional(GenericArguments.string(Text.of("subcommand"))));

        this.schema.forEach(e -> elements.add(GenericArguments.optional(GenericArguments.string(Text.of(e)))));

        CommandSpec cmd = CommandSpec.builder()
                .description(Text.of("Command created with PluginsLib"))
                .arguments(elements.toArray(new CommandElement[0]))
                .executor(this)
                .build();

        Sponge.getGame()
                .getCommandManager()
                .register(
                Sponge.getGame().getPluginManager().fromInstance(getPlugin()),
                     cmd
                );
    }

    @Override
    public @NotNull CommandResult execute(@NotNull CommandSource sender, @NotNull CommandContext args) {
        Optional<String> cmd = args.getOne("subcommand");

        if (this.getCustomization().getHeader() != null) {
            sender.sendMessage(Text.of(ChatColor.translateAlternateColorCodes('&', this.getCustomization().getHeader())));
        }

        if (this.getPermission() != null && this.getMessage() != null && !sender.hasPermission(this.getPermission())) {
            sender.sendMessage(Text.of(ChatColor.translateAlternateColorCodes('&', this.getMessage())));
            return CommandResult.success();
        }

        List<String> arguments = new ArrayList<>();
        this.schema.forEach(arg -> arguments.add(args.getOne(arg).toString()));

        if (cmd.isPresent()){
            for (SubCommand subcommand : subcommands) {
                if (cmd.get().equalsIgnoreCase(subcommand.getName())) {
                    subcommand.perform(new SpongeUser(sender), (String[]) arguments.toArray());
                    return CommandResult.success();
                }
            }

            Optional<SubCommand> anyArgs = this.findSubcommand(AnyArgument.class);

            if (anyArgs.isPresent()) {
                anyArgs.get().perform(new SpongeUser(sender),(String[]) arguments.toArray());
                return CommandResult.success();
            }

        } else {
            Optional<SubCommand> noArgsCommand = this.findSubcommand(NoArguments.class);

            if (noArgsCommand.isPresent()) {
                noArgsCommand.get().perform(new SpongeUser(sender),(String[]) arguments.toArray());
                return CommandResult.success();
            }

            String noArgs = this.getCustomization().getNoArgs(getCommand());

            if (noArgs != null) {
                sender.sendMessage(Text.of(ChatColor.translateAlternateColorCodes('&', noArgs)));
            }
            return CommandResult.success();
        }

        String notFound = this.getCustomization().getNotFound(getCommand());

        if (notFound != null) {
            sender.sendMessage(Text.of(ChatColor.translateAlternateColorCodes('&', notFound)));
        }


        return CommandResult.success();
    }

}
