package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.entity.Card;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface CardStorage {
    CompletableFuture<Collection<Card>> load();

    CompletableFuture<Void> save(Collection<Card> cards);
}
