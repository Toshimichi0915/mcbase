package net.toshimichi.mcbase.commands.arguments;

import net.toshimichi.mcbase.commands.Command;

import java.util.Map;

public interface HelpCommand {

    Command create(Map<String, Command> commands);
}
