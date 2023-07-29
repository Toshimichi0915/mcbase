package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.List;

@RequiredArgsConstructor
public class IntArgument implements Command {

    private final String name;
    private final Command next;

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().isEmpty()) {
            context.getSender().sendMessage(Component.text("整数を入力してください。").color(NamedTextColor.RED));
            return;
        }

        String arg = context.getArgs().pop();
        try {
            int result = Integer.parseInt(arg);
            context.setVariable(name, result);
            if (next != null) next.execute(context);
        } catch (NumberFormatException e) {
            context.getSender().sendMessage(Component.text(arg + "は整数ではありません。").color(NamedTextColor.RED));
        }
    }

    @Override
    public List<String> complete(CommandContext context) {
        if (context.getArgs().isEmpty()) return List.of();

        String arg = context.getArgs().pop();
        try {
            Integer.parseInt(arg);
            if (next == null) return List.of();
            return next.complete(context);
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    @Override
    public String details(CommandSender sender) {
        if (next == null) return null;
        return next.details(sender);
    }
}
