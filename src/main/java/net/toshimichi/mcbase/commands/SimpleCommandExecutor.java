package net.toshimichi.mcbase.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class SimpleCommandExecutor implements TabExecutor {

    private final Command chain;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        CommandContext context = new SimpleCommandContext(sender, List.of(args), label);
        chain.execute(context);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        CommandContext context = new SimpleCommandContext(sender, List.of(args), label);
        return chain.complete(context);
    }
}
