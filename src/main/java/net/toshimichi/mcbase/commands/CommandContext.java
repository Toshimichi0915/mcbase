package net.toshimichi.mcbase.commands;

import org.bukkit.command.CommandSender;

import java.util.Deque;

public interface CommandContext {

    CommandSender getSender();

    Deque<String> getArgs();

    String getLabel();

    void setLabel(String label);

    <T> T getVariable(String name);

    void setVariable(String name, Object value);
}
