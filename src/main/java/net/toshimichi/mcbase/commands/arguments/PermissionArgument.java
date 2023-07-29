package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.List;

@RequiredArgsConstructor
public class PermissionArgument implements Command {

    private final String permission;
    private final Command next;

    @Override
    public void execute(CommandContext context) {
        if (!context.getSender().hasPermission(permission)) {
            context.getSender().sendMessage(Component.text("権限がありません。").color(NamedTextColor.RED));
            return;
        }

        if (next != null) next.execute(context);
    }

    @Override
    public List<String> complete(CommandContext context) {
        if (!context.getSender().hasPermission(permission)) return List.of();
        return next.complete(context);
    }

    @Override
    public String details(CommandSender sender) {
        if (!sender.hasPermission(permission)) return null;
        if (next == null) return null;
        return next.details(sender);
    }
}
