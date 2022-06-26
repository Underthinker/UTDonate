package io.github.underthinker.utdonate.api.storage;

import io.github.underthinker.utdonate.api.entity.Card;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface CardStorage {
    CompletableFuture<Collection<Card>> load();

    CompletableFuture<Void> save(Collection<Card> cards);
}
