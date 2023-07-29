package net.toshimichi.mcbase.services;

import lombok.RequiredArgsConstructor;
import net.toshimichi.indexer.ObservableField;
import net.toshimichi.indexer.ObservableFieldHandler;
import net.toshimichi.indexer.ObservableSet;
import net.toshimichi.indexer.ObservableSetHandler;
import net.toshimichi.mcbase.repository.ObservableEntity;
import net.toshimichi.mcbase.repository.Repository;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class AutoSaveService<T extends ObservableEntity<T>> implements Service, ObservableSetHandler<Object, T> {

    private final Plugin plugin;
    private final Repository<T> repository;
    private final int interval;

    private final Set<T> dirty = new LinkedHashSet<>();
    private final Map<T, DirtyMarker> markers = new HashMap<>();
    private BukkitTask task;

    @Override
    public void onEnable() {
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::save, interval, interval);
        repository.getAll().subscribe(this);
    }

    @Override
    public void onDisable() {
        task.cancel();
        repository.getAll().unsubscribe(this);

        dirty.forEach(repository::save);
    }

    public void save() {
        Iterator<T> iterator = dirty.iterator();
        T first = iterator.next();

        iterator.remove();
        repository.save(first);
    }

    @Override
    public void add(ObservableSet<?, ? extends T> set, T element) {
        DirtyMarker marker = new DirtyMarker(element);

        marker.subscribe(element);
        markers.put(element, marker);
    }

    @Override
    public void remove(ObservableSet<?, ? extends T> set, T element) {
        DirtyMarker marker = markers.remove(element);

        marker.unsubscribe(element);
    }

    @RequiredArgsConstructor
    private class DirtyMarker implements ObservableSetHandler<Object, Object>, ObservableFieldHandler<Object, Object> {

        private final T owner;

        private void search(Object obj, Consumer<ObservableField<?, ?>> c0, Consumer<ObservableSet<?, ?>> c1) {
            if (!(obj instanceof ObservableEntity<?> target)) return;

            for (ObservableField<?, ?> field : target.getObservableFields()) {
                c0.accept(field);
                if (field.get() instanceof ObservableEntity<?> observable) {
                    search(observable, c0, c1);
                }
            }

            for (ObservableSet<?, ?> set : target.getObservableSets()) {
                c1.accept(set);
                for (Object element : set) {
                    if (element instanceof ObservableEntity<?> observable) {
                        search(observable, c0, c1);
                    }
                }
            }
        }

        public void subscribe(Object obj) {
            search(obj, field -> field.subscribe(this), set -> set.subscribe(this));
        }

        public void unsubscribe(Object obj) {
            search(obj, field -> field.unsubscribe(this), set -> set.unsubscribe(this));
        }

        @Override
        public void accept(ObservableField<?, ?> field, Object old, Object updated) {
            dirty.add(owner);

            unsubscribe(old);
            subscribe(updated);
        }

        @Override
        public void add(ObservableSet<?, ?> set, Object element) {
            dirty.add(owner);

            subscribe(element);
        }

        @Override
        public void remove(ObservableSet<?, ?> set, Object element) {
            dirty.add(owner);

            unsubscribe(element);
        }
    }
}
