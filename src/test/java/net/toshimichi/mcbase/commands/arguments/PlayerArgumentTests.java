package net.toshimichi.mcbase.commands.arguments;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.toshimichi.mcbase.commands.SimpleCommandContext;
import net.toshimichi.mcbase.commands.ValidateCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerArgumentTests {

    private ServerMock server;
    private MockPlugin plugin;

    @BeforeEach
    public void beforeEach() {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
    }

    @AfterEach
    public void afterEach() {
        MockBukkit.unmock();
    }

    @Test
    public void test() {
        ValidateCommand command = new ValidateCommand(Commands.player("target"));

        PlayerMock dummy = server.addPlayer("dummy");
        PlayerMock player = server.addPlayer("Toshimichi0915");

        command.execute(new SimpleCommandContext(server.getConsoleSender(), List.of(), null),
                c -> assertFalse(command.isExecuted()));
        command.complete(new SimpleCommandContext(server.getConsoleSender(), List.of(), null),
                (c, l) -> assertIterableEquals(List.of(player.getName(), dummy.getName()), l));
        command.complete(new SimpleCommandContext(server.getConsoleSender(), List.of("Toshi"), null),
                (c, l) -> assertIterableEquals(List.of(player.getName()), l));

        command.execute(new SimpleCommandContext(server.getConsoleSender(), List.of(player.getName()), null),
                c -> {
                    assertTrue(command.isExecuted());
                    assertEquals(0, c.getArgs().size());
                    assertEquals(player, c.getVariable("target"));
                });
        command.complete(new SimpleCommandContext(server.getConsoleSender(), List.of(player.getName()), null),
                (c, l) -> {
                    assertEquals(ValidateCommand.COMPLETIONS, l);
                    assertEquals(0, c.getArgs().size());
                    assertEquals(player, c.getVariable("target"));
                });

        command.details(server.getConsoleSender(), (p, d) -> assertEquals(ValidateCommand.DETAILS, d));
    }
}
