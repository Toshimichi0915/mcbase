package net.toshimichi.mcbase.commands.arguments;

import lombok.RequiredArgsConstructor;
import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BranchCommand implements Command {

    private final Map<String, Command> branches;
    private final Command help;
    private final String details;

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().isEmpty()) {
            help.execute(context);
            return;
        }

        String arg = context.getArgs().pop();
        Command chain = branches.get(arg);
        if (chain == null) {
            help.execute(context);
            return;
        }

        context.setLabel(context.getLabel() + " " + arg);
        chain.execute(context);
    }

    @Override
    public List<String> complete(CommandContext context) {
        List<String> completion = branches.entrySet()
                .stream()
                .filter(it -> it.getValue().details(context.getSender()) != null)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();

        if (context.getArgs().isEmpty()) {
            return completion;
        }

        String arg = context.getArgs().pop();
        Command chain = branches.get(arg);

        if (chain == null) {
            if (context.getArgs().isEmpty()) {
                return completion
                        .stream()
                        .filter(it -> it.startsWith(arg))
                        .toList();
            } else {
                return List.of();
            }
        } else {
            context.setLabel(context.getLabel() + " " + arg);
            return chain.complete(context);
        }
    }

    @Override
    public String details(CommandSender sender) {
        return details;
    }
}
