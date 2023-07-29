package net.toshimichi.mcbase.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Command {

    void execute(CommandContext context);

    List<String> complete(CommandContext context);

    String details(CommandSender sender);
}
