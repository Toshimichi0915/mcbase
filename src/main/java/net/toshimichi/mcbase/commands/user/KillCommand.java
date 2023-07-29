package net.toshimichi.mcbase.commands.user;

import net.toshimichi.mcbase.commands.Command;
import net.toshimichi.mcbase.commands.CommandContext;
import net.toshimichi.mcbase.commands.arguments.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KillCommand implements Command {

    public static Command create() {
        return Commands.concat(
                Commands.player("target"),
                Commands.permission("test123")
        ).create(new KillCommand());
    }

    @Override
    public void execute(CommandContext context) {
        Player target = context.getVariable("target");
        target.setHealth(0);
    }

    @Override
    public List<String> complete(CommandContext context) {
        return List.of();
    }

    @Override
    public String details(CommandSender sender) {
        return "プレイヤーをキルします";
    }
}
