package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;

import java.util.*;
import java.util.function.Consumer;

/**
 * The listener manager
 */
public class ListenerManager {
    private final Map<ListenerType<?>, Map<String, Consumer<?>>> listeners;
    private final UTDonateCore core;

    public ListenerManager(UTDonateCore core) {
        this.core = core;
        listeners = new HashMap<>();
    }

    /**
     * Notify all listeners of a specific type
     *
     * @param type  The type of the listeners
     * @param value The value to pass to the listeners
     * @param <T>   The type of the value
     */
    public <T> void notifyListeners(ListenerType<T> type, T value) {
        if (type.getType().isAssignableFrom(value.getClass())) {
            Collection<Consumer<?>> consumers = listeners.getOrDefault(type, Collections.emptyMap()).values();
            core.getSchedulerFactory().runTask(() -> {
                for (Consumer<?> consumer : consumers) {
                    // noinspection unchecked
                    ((Consumer<T>) consumer).accept(value);
                }
            });
        }
    }

    /**
     * Add a listener
     *
     * @param type     The type of the listener
     * @param name     The name of the listener
     * @param consumer The listener
     * @param <T>      The type of the listener
     */
    public <T> void registerListener(ListenerType<T> type, String name, Consumer<T> consumer) {
        listeners.computeIfAbsent(type, k -> new LinkedHashMap<>()).put(name, consumer);
    }

    /**
     * Remove a listener
     *
     * @param type The type of the listener
     * @param name The name of the listener
     * @param <T>  The type of the value
     */
    public <T> void unregisterListener(ListenerType<T> type, String name) {
        Map<String, Consumer<?>> map = listeners.get(type);
        if (map != null) {
            map.remove(name);
        }
    }

    /**
     * Remove all listeners of a specific type
     *
     * @param type The type of the listeners
     * @param <T>  The type of the value
     */
    public <T> void unregisterAllListeners(ListenerType<T> type) {
        listeners.remove(type);
    }
}
