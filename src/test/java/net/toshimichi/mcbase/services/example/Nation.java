package net.toshimichi.mcbase.services.example;

import net.toshimichi.indexer.ObservableField;
import net.toshimichi.indexer.ObservableSet;
import net.toshimichi.mcbase.repository.ObservableEntity;

import java.util.List;
import java.util.UUID;

public class Nation implements ObservableEntity<Nation> {

    private final ObservableField<Nation, String> name;
    private final ObservableField<Nation, UUID> leader;
    private final ObservableSet<Nation, UUID> members;
    private final ObservableSet<Nation, Town> towns;

    public Nation(String name, UUID leader) {
        this.name = new ObservableField<>(name);
        this.leader = new ObservableField<>(leader);
        this.members = new ObservableSet<>();
        this.towns = new ObservableSet<>();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public UUID getLeader() {
        return leader.get();
    }

    public void setLeader(UUID leader) {
        this.leader.set(leader);
    }

    public ObservableSet<Nation, UUID> getMembers() {
        return members;
    }

    public ObservableSet<Nation, Town> getTowns() {
        return towns;
    }

    @Override
    public List<ObservableSet<Nation, ?>> getObservableSets() {
        return List.of(members, towns);
    }

    @Override
    public List<ObservableField<Nation, ?>> getObservableFields() {
        return List.of(name, leader);
    }
}
