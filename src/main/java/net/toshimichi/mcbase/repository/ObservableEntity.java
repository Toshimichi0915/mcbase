package net.toshimichi.mcbase.repository;

import net.toshimichi.indexer.ObservableField;
import net.toshimichi.indexer.ObservableSet;

import java.util.List;

public interface ObservableEntity<T> {

    List<ObservableSet<T, ?>> getObservableSets();

    List<ObservableField<T, ?>> getObservableFields();
}
