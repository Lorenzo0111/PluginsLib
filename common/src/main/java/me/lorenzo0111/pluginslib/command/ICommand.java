package me.lorenzo0111.pluginslib.command;

import me.lorenzo0111.pluginslib.audience.User;
import me.lorenzo0111.pluginslib.command.annotations.Permission;
import me.lorenzo0111.pluginslib.exceptions.LibraryException;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ICommand<T> {
    private final T plugin;
    private String permission = null;
    private String message = null;
    private final String command;
    private final Customization customization;
    protected final List<SubCommand> subcommands = new ArrayList<>();

    /**
     * @param plugin Owner of the command
     * @param command Name of the command ( /name )
     * @param customization <b>Optional</b> Command customization
     */
    public ICommand(T plugin, String command, @Nullable Customization customization) {
        this.plugin = plugin;
        this.command = command;
        this.customization = customization == null ? new Customization(null,null,null) : customization;
        this.register(command);

        try {

            final Constructor<? extends ICommand> constructor = this.getClass().getConstructor(plugin.getClass(),String.class,Customization.class);

            if (constructor.isAnnotationPresent(Permission.class)) {
                final Permission annotation = constructor.getAnnotation(Permission.class);

                this.permission = annotation.value();
                this.message = annotation.msg();
            }

        } catch (NoSuchMethodException e) {
            throw new LibraryException("Unable to check for Permission annotation.", e);
        }

    }

    public abstract void register(String command);

    /**
     * @return Owner of the command
     */
    public T getPlugin() {
        return plugin;
    }

    /**
     * @param subCommand SubCommand to add
     */
    public void addSubcommand(SubCommand subCommand) {
        this.subcommands.add(subCommand);

        try {
            // Check if the subcommand has a permission

            Method method = subCommand.getClass().getMethod("handleSubcommand", User.class, String[].class);

            if (method.isAnnotationPresent(Permission.class)) {

                final Permission annotation = method.getAnnotation(Permission.class);

                subCommand.setPermission(annotation.value(),annotation.msg());

            }

        } catch (NoSuchMethodException e) {
            throw new LibraryException("Unable to check for Permission annotation.", e);
        }
    }

    /**
     * Find a subcommand annotated with something
     * @param annotation Annotation to find
     * @return Optional subcommand
     */
    public Optional<SubCommand> findSubcommand(Class<? extends Annotation> annotation) {
        return subcommands.stream()
                .filter((cmd) -> {
                    try {
                        return cmd.getClass().getMethod("getName").isAnnotationPresent(annotation);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    return false;
                })
                .findFirst();
    }

    public String getPermission() {
        return permission;
    }

    public Customization getCustomization() {
        return customization;
    }

    public List<SubCommand> getSubcommands() {
        return subcommands;
    }

    public String getMessage() {
        return message;
    }

    public String getCommand() {
        return command;
    }
}
