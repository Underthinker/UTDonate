package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import io.github.underthinker.utdonate.core.entity.storage.CardStorage;
import io.github.underthinker.utdonate.core.scheduler.SchedulerTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public abstract class MapBasedCardStorage implements CardStorage {
    protected final UTDonateCore core;
    private final File file;
    private final Map<Long, CardData> cards = new ConcurrentHashMap<>();
    private final AtomicBoolean isSaving = new AtomicBoolean(false);
    private final AtomicReference<SchedulerTask> currentTask = new AtomicReference<>();

    protected MapBasedCardStorage(UTDonateCore core, CardStorage.Input input) {
        this.core = core;
        this.file = new File(core.getCardStorageManager().getBaseFolder(), input.getName() + "." + getFileExtension());
    }

    protected abstract String getFileExtension();

    protected abstract Map<Long, CardData> load(File file);

    protected abstract void save(File file, Map<Long, CardData> map);

    @Override
    public boolean setup() {
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                core.getLogger().log(Level.SEVERE, e, () -> "Failed to create file " + file.getName());
                return false;
            }
        }
        cards.putAll(load(file));
        return true;
    }

    @Override
    public void stop() {
        Optional.ofNullable(currentTask.get()).ifPresent(SchedulerTask::cancel);
        if (!isSaving.get()) {
            save();
        }
    }

    private void save() {
        Map<Long, CardData> map = new HashMap<>(cards);
        save(file, map);
    }

    private void scheduleSave() {
        if (isSaving.get()) return;
        isSaving.set(true);
        currentTask.set(core.getSchedulerFactory().runTaskLater(() -> {
            save();
            isSaving.set(false);
        }, 40));
    }

    @Override
    public CompletableFuture<Map<Long, CardData>> getAll() {
        return CompletableFuture.completedFuture(Collections.unmodifiableMap(cards));
    }

    @Override
    public CompletableFuture<Map<Long, CardData>> getByOwner(UUID ownerId) {
        return CompletableFuture.supplyAsync(() ->
                cards.entrySet().stream()
                        .filter(entry -> Objects.equals(entry.getValue().getCard().getOwnerId(), ownerId))
                        .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll)
        );
    }

    @Override
    public CompletableFuture<CardData> get(long id) {
        return CompletableFuture.completedFuture(cards.get(id));
    }

    @Override
    public CompletableFuture<Long> save(CardData card) {
        return CompletableFuture.supplyAsync(() -> {
            long nextId;
            do {
                nextId = ThreadLocalRandom.current().nextLong();
            } while (cards.containsKey(nextId));
            cards.put(nextId, card);
            scheduleSave();
            return nextId;
        });
    }

    @Override
    public CompletableFuture<Void> remove(long id) {
        return CompletableFuture.runAsync(() -> {
            cards.remove(id);
            scheduleSave();
        });
    }
}
