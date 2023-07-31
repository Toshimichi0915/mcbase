package net.toshimichi.mcbase.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SimpleCommandContext implements CommandContext {

    private final CommandSender sender;
    private final Deque<String> args;
    private final Map<String, Object> variables;
    private String label;

    public SimpleCommandContext(CommandSender sender, List<String> args, String label) {
        this.sender = sender;
        this.args = new ArrayDeque<>(args);
        this.variables = new HashMap<>();
        this.label = label;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getVariable(String name) {
        return (T) variables.get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }
}
