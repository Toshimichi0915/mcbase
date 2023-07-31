package net.toshimichi.mcbase.services;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.jimfs.Jimfs;
import net.toshimichi.mcbase.services.example.Nation;
import net.toshimichi.mcbase.services.example.NationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AutoSaveServiceTests {

    private ServerMock server;
    private MockPlugin plugin;
    private FileSystem vfs;

    @BeforeEach
    public void beforeEach() {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
        vfs = Jimfs.newFileSystem();
    }

    @AfterEach
    public void afterEach() throws IOException {
        MockBukkit.unmock();
        vfs.close();
    }

    @Test
    public void testAutoSave() {
        Path path = vfs.getPath("nations");
        NationRepository nationRepository = new NationRepository(path);

        AutoSaveService<Nation> service = new AutoSaveService<>(plugin, nationRepository, 20);
        service.onEnable();

        Nation nation = new Nation("test", UUID.randomUUID());

        nationRepository.getAll().add(nation);
        server.getScheduler().performTicks(25);
        assertFalse(Files.exists(path.resolve("test.json")));

        nation.getMembers().add(UUID.randomUUID());
        server.getScheduler().performTicks(25);
        assertTrue(Files.exists(path.resolve("test.json")));
    }
}
