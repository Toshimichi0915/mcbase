package net.toshimichi.mcbase.commands.arguments;

import net.toshimichi.mcbase.commands.Command;

@FunctionalInterface
public interface CommandArgument {

    Command create(Command next);
}
