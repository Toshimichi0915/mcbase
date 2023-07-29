package net.toshimichi.mcbase.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.Deque;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class SimpleCommandContext implements CommandContext {

    private final CommandSender sender;
    private final Deque<String> args;
    private final Map<String, Object> variables;
    private String label;

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
