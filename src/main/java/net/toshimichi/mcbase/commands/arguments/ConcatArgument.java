package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.toshimichi.mcbase.commands.Command;

import java.util.List;

@RequiredArgsConstructor
public class ConcatArgument implements CommandArgument {

    private final List<? extends CommandArgument> commands;

    @Override
    public Command create(Command next) {
        for (CommandArgument command : commands) {
            next = command.create(next);
        }

        return next;
    }
}
