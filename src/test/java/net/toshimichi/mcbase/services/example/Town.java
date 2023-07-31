package net.toshimichi.mcbase.services.example;

import net.toshimichi.indexer.ObservableField;
import net.toshimichi.indexer.ObservableSet;
import net.toshimichi.mcbase.repository.ObservableEntity;

import java.util.List;
import java.util.UUID;

public class Town implements ObservableEntity<Town> {

    private final ObservableField<Town, String> name;
    private final ObservableSet<Town, UUID> residents;

    public Town(ObservableField<Town, String> name) {
        this.name = name;
        this.residents = new ObservableSet<>();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableSet<Town, UUID> getResidents() {
        return residents;
    }

    @Override
    public List<ObservableSet<Town, ?>> getObservableSets() {
        return List.of(residents);
    }

    @Override
    public List<ObservableField<Town, ?>> getObservableFields() {
        return List.of(name);
    }
}
