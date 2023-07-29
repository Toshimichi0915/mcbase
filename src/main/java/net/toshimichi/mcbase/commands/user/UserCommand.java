package net.toshimichi.mcbase.commands.user;

import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.arguments.Commands;

import java.util.Map;

public class UserCommand {

    public static Command create() {
        Map<String, Command> subCommands = Map.of(
                "kill", KillCommand.create()
        );

        return Commands.branch(subCommands, "一般ユーザーが実行可能なコマンドです");
    }
}
