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

public class PermissionArgumentTests {

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
        ValidateCommand command = new ValidateCommand(Commands.permission("test.perm"));

        PlayerMock player = server.addPlayer();
        command.execute(new SimpleCommandContext(player, List.of(), null), c -> assertFalse(command.isExecuted()));
        command.complete(new SimpleCommandContext(player, List.of(), null), (c, l) -> assertTrue(l.isEmpty()));
        command.details(player, (p, d) -> assertNull(d));

        player.addAttachment(plugin, "test.perm", true);
        command.execute(new SimpleCommandContext(player, List.of(), null), c -> assertTrue(command.isExecuted()));
        command.complete(new SimpleCommandContext(player, List.of(), null), (c, l) -> assertEquals(ValidateCommand.COMPLETIONS, l));
        command.details(player, (p, d) -> assertEquals(ValidateCommand.DETAILS, d));
    }
}
