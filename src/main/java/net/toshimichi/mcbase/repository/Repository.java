package net.toshimichi.mcbase.repository;

import net.toshimichi.indexer.ObservableSet;

public interface Repository<T> {

    ObservableSet<?, T> getAll();

    void save(T value);
}
