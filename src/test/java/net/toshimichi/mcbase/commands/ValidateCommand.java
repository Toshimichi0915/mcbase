package net.toshimichi.mcbase.commands;

import lombok.Getter;
import net.toshimichi.mcbase.commands.arguments.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ValidateCommand implements Command {

    public static List<String> COMPLETIONS = List.of(ValidateCommand.class.getCanonicalName());
    public static String DETAILS = ValidateCommand.class.getCanonicalName();

    private final Command command;

    @Getter private boolean executed;
    @Getter private boolean completed;

    public ValidateCommand(CommandArgument argument) {
        this.command = argument.create(new CallbackCommand());
    }

    @Override
    public void execute(CommandContext context) {
        executed = false;
        command.execute(context);
    }

    public void execute(CommandContext context, Consumer<CommandContext> validator) {
        command.execute(context);
        validator.accept(context);
    }

    @Override
    public List<String> complete(CommandContext context) {
        completed = false;
        return command.complete(context);
    }

    public void complete(CommandContext context, BiConsumer<CommandContext, List<String>> validator) {
        List<String> completion = command.complete(context);
        validator.accept(context, completion);
    }

    @Override
    public String details(CommandSender sender) {
        return command.details(sender);
    }

    public void details(CommandSender sender, BiConsumer<CommandSender, String> validator) {
        String details = command.details(sender);
        validator.accept(sender, details);
    }

    private class CallbackCommand implements Command {

        @Override
        public void execute(CommandContext context) {
            executed = true;
        }

        @Override
        public List<String> complete(CommandContext context) {
            completed = true;
            return COMPLETIONS;
        }

        @Override
        public String details(CommandSender sender) {
            return DETAILS;
        }
    }
}
