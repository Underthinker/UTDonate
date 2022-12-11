package io.github.underthinker.utdonate.core.entity.storage;

import io.github.underthinker.utdonate.core.entity.card.CardData;
import lombok.Value;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CardStorage {
    boolean setup();

    void stop();

    CompletableFuture<Map<Long, CardData>> getAll();

    CompletableFuture<Map<Long, CardData>> getByOwner(UUID ownerId);

    CompletableFuture<CardData> get(long id);

    CompletableFuture<Long> save(CardData card);

    CompletableFuture<Void> remove(long id);

    @Value
    class Input {
        String name;
        Map<String, Object> settings;
    }
}
