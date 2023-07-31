package net.toshimichi.mcbase.commands.arguments;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import net.toshimichi.mcbase.commands.SimpleCommandContext;
import net.toshimichi.mcbase.commands.ValidateCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringArgumentTests {

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
        ValidateCommand command = new ValidateCommand(Commands.string("target"));
        command.execute(new SimpleCommandContext(server.getConsoleSender(), List.of(), null),
                c -> assertFalse(command.isExecuted()));
        command.complete(new SimpleCommandContext(server.getConsoleSender(), List.of(), null),
                (c, l) -> assertTrue(l.isEmpty()));

        command.execute(new SimpleCommandContext(server.getConsoleSender(), List.of("test", "123", "345"), null),
                c -> {
                    assertTrue(command.isExecuted());
                    assertEquals(2, c.getArgs().size());
                    assertEquals("test", c.getVariable("target"));
                });
        command.complete(new SimpleCommandContext(server.getConsoleSender(), List.of("test", "123", "345"), null),
                (c, l) -> {
                    assertEquals(ValidateCommand.COMPLETIONS, l);
                    assertEquals(2, c.getArgs().size());
                    assertEquals("test", c.getVariable("target"));
                });

        command.details(server.getConsoleSender(), (p, d) -> assertEquals(ValidateCommand.DETAILS, d));
    }
}
