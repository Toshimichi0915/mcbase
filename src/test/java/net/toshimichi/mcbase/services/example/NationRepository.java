package net.toshimichi.mcbase.services.example;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.toshimichi.indexer.ObservableSet;
import net.toshimichi.mcbase.repository.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class NationRepository implements Repository<Nation> {

    private final Path path;
    private final Gson gson = new Gson();
    private final ObservableSet<?, Nation> nations = new ObservableSet<>();
    private final ObservableSet<?, Town> towns = nations.createFlatMap(Nation::getTowns);
    private final Map<UUID, Town> residentIndex = towns.createFlatIndex(Town::getResidents);

    @Override
    public ObservableSet<?, Nation> getAll() {
        return nations;
    }

    public Town getTown(UUID resident) {
        return residentIndex.get(resident);
    }

    @SneakyThrows
    @Override
    public void save(Nation value) {
        Path file = path.resolve(value.getName() + ".json");
        Files.createDirectories(path);
        Files.writeString(file, gson.toJson(value));
    }

    @SneakyThrows
    public static NationRepository create(Path path) {
        NationRepository nationRepository = new NationRepository(path);

        List<Path> files;
        try (Stream<Path> stream = Files.list(path)) {
            files = stream.toList();
        }

        for (Path file : files) {
            Nation nation = nationRepository.gson.fromJson(Files.readString(file), Nation.class);
            nationRepository.nations.add(nation);
        }

        return nationRepository;
    }
}
