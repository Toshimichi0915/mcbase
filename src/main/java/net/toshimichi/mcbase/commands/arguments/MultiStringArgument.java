package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.List;

@RequiredArgsConstructor
public class MultiStringArgument implements Command {

    private final String name;
    private final Command next;

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().isEmpty()) {
            context.getSender().sendMessage(Component.text("文字列を入力してください。").color(NamedTextColor.RED));
            return;
        }

        String arg = String.join(" ", context.getArgs());
        context.getArgs().clear();
        context.setVariable(name, arg);
        if (next != null) next.execute(context);
    }

    @Override
    public List<String> complete(CommandContext context) {
        if (context.getArgs().isEmpty()) return List.of();

        String arg = String.join(" ", context.getArgs());
        context.getArgs().clear();
        context.setVariable(name, arg);
        if (next == null) return List.of();
        return next.complete(context);
    }

    @Override
    public String details(CommandSender sender) {
        if (next == null) return null;
        return next.details(sender);
    }
}
