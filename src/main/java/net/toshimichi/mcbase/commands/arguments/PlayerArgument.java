package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class PlayerArgument implements Command {

    private final String name;
    private final Command next;

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().isEmpty()) {
            context.getSender().sendMessage(Component.text("プレイヤー名を入力してください。").color(NamedTextColor.RED));
            return;
        }

        String arg = context.getArgs().pop();
        Player player = Bukkit.getPlayerExact(arg);
        if (player == null) {
            context.getSender().sendMessage(Component.text(arg + "は存在しません。").color(NamedTextColor.RED));
            return;
        }

        context.setVariable(name, player);
        if (next != null) next.execute(context);
    }

    @Override
    public List<String> complete(CommandContext context) {

        List<String> completion = Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .sorted()
                .toList();

        if (context.getArgs().isEmpty()) return completion;

        String arg = context.getArgs().pop();
        Player player = Bukkit.getPlayerExact(arg);
        if (player == null) {
            if (context.getArgs().isEmpty()) {
                return completion
                        .stream()
                        .filter(it -> it.startsWith(arg))
                        .toList();
            } else {
                return List.of();
            }
        } else {
            context.setVariable(name, player);
            if (next == null) return List.of();
            return next.complete(context);
        }
    }

    @Override
    public String details(CommandSender sender) {
        if (next == null) return null;
        return next.details(sender);
    }
}
