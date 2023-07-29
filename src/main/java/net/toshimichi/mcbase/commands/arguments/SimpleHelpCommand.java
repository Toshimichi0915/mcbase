package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleHelpCommand implements Command {

    private final Map<String, Command> commands;

    @Override
    public void execute(CommandContext context) {
        List<String> names = commands.entrySet()
                .stream()
                .filter(it -> it.getValue().details(context.getSender()) != null)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        if (names.isEmpty()) {
            context.getSender().sendMessage(Component.text("利用可能なコマンドはありません。").color(NamedTextColor.RED));
            return;
        }

        context.getSender().sendMessage(Component.text());
        context.getSender().sendMessage(Component.text("利用可能なコマンドは以下の通りです。")
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.UNDERLINED));
        context.getSender().sendMessage(Component.text());
        for (String name : names) {
            Command command = commands.get(name);
            String details = command.details(context.getSender());
            Component text = Component.text("/" + context.getLabel() + " " + name + " - " + details).color(NamedTextColor.GOLD);
            context.getSender().sendMessage(text);
        }
        context.getSender().sendMessage(Component.text());
    }

    @Override
    public List<String> complete(CommandContext context) {
        return List.of();
    }

    @Override
    public String details(CommandSender sender) {
        return "利用可能なサブコマンド一覧を表示します";
    }
}
