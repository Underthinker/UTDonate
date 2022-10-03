package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import io.github.underthinker.utdonate.core.entity.storage.CardStorage;
import io.github.underthinker.utdonate.core.scheduler.SchedulerTask;
import me.hsgamer.hscore.common.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class FlatFileCardStorage implements CardStorage {
    private final UTDonateCore core;
    private final File file;
    private final Map<Long, CardData> cards = new ConcurrentHashMap<>();
    private final AtomicBoolean isSaving = new AtomicBoolean(false);
    private final AtomicReference<SchedulerTask> currentTask = new AtomicReference<>();

    public FlatFileCardStorage(UTDonateCore core, CardStorage.Input input) {
        this.core = core;
        this.file = new File(core.getCardStorageManager().getBaseFolder(), input.getName() + ".cards");
    }

    private static String serializeCardData(Pair<Long, CardData> cardDataPair) {
        long id = cardDataPair.getKey();
        CardData cardData = cardDataPair.getValue();
        List<String> list = new ArrayList<>();
        list.add(Long.toString(id));
        list.add(Objects.toString(cardData.getCard().getOwnerId(), ""));
        list.add(Objects.toString(cardData.getCard().getOwnerName(), ""));
        list.add(cardData.getCard().getSerial());
        list.add(cardData.getCard().getPin());
        list.add(Integer.toString(cardData.getCard().getPrice()));
        list.add(cardData.getCard().getProvider());
        for (Map.Entry<String, String> entry : cardData.getData().entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.join("|", list);
    }

    private static Pair<Long, CardData> deserializeCardData(String string) {
        String[] split = string.split("\\|");
        long id = Long.parseLong(split[0]);
        UUID ownerId = Optional.ofNullable(split[1]).filter(s -> !s.isEmpty()).map(UUID::fromString).orElse(null);
        String ownerName = Optional.ofNullable(split[2]).filter(s -> !s.isEmpty()).orElse(null);
        String serial = split[3];
        String pin = split[4];
        int price = Integer.parseInt(split[5]);
        String provider = split[6];
        Map<String, String> data = new HashMap<>();
        for (int i = 7; i < split.length; i++) {
            String[] dataSplit = split[i].split("=", 2);
            data.put(dataSplit[0], dataSplit[1]);
        }
        return Pair.of(id, new CardData(new Card(ownerId, ownerName, serial, pin, price, provider), data));
    }

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
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                Pair<Long, CardData> cardDataPair = deserializeCardData(line);
                cards.put(cardDataPair.getKey(), cardDataPair.getValue());
            }
        } catch (IOException e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to read file " + file.getName());
        }
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
        try {
            List<String> list = new ArrayList<>();
            cards.forEach((id, cardData) -> list.add(serializeCardData(Pair.of(id, cardData))));
            Files.write(file.toPath(), list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            core.getLogger().log(Level.SEVERE, e, () -> "Failed to save file " + file.getName());
        }
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
    public CompletableFuture<Void> save(CardData card) {
        return CompletableFuture.runAsync(() -> {
            long nextId;
            do {
                nextId = ThreadLocalRandom.current().nextLong();
            } while (cards.containsKey(nextId));
            cards.put(nextId, card);
            scheduleSave();
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
