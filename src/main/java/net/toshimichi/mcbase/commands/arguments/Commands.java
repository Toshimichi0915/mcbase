package net.toshimichi.mcbase.commands.arguments;

import net.toshimichi.mcbase.commands.Command;

import java.util.List;
import java.util.Map;

public class Commands {

    public static Command branch(Map<String, Command> commands, Command help, String details) {
        return new BranchCommand(commands, help, details);
    }

    public static Command branch(Map<String, Command> commands, String details) {
        return new BranchCommand(commands, new SimpleHelpCommand(commands), details);
    }

    public static CommandArgument concat(CommandArgument... arguments) {
        return new ConcatArgument(List.of(arguments));
    }

    public static CommandArgument concat(List<? extends CommandArgument> arguments) {
        return new ConcatArgument(arguments);
    }

    public static CommandArgument integer(String name) {
        return (next) -> new IntArgument(name, next);
    }

    public static CommandArgument permission(String permission) {
        return (next) -> new PermissionArgument(permission, next);
    }

    public static CommandArgument player(String name) {
        return (next) -> new PlayerArgument(name, next);
    }
}
