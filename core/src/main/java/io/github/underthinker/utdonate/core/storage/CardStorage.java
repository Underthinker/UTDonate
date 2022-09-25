package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.entity.Card;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CardStorage {
    void setup();

    void stop();

    String getName();

    CompletableFuture<Collection<Card>> getAll();

    CompletableFuture<Collection<Card>> getByOwner(UUID ownerId);

    CompletableFuture<Card> get(UUID id);

    CompletableFuture<Void> save(Card card);

    CompletableFuture<Void> save(Collection<Card> cards);
}
