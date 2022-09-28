package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.entity.card.Card;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CardStorage {
    void setup();

    void stop();

    String getName();

    CompletableFuture<Map<Long, Card>> getAll();

    CompletableFuture<Map<Long, Card>> getByOwner(UUID ownerId);

    CompletableFuture<Card> get(long id);

    CompletableFuture<Void> save(Card card);

    CompletableFuture<Void> remove(Long id);
}
