package io.github.underthinker.utdonate.core.entity.storage;

import io.github.underthinker.utdonate.core.entity.card.CardData;
import lombok.Value;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A storage for {@link CardData} objects.
 */
public interface CardStorage {
    /**
     * Set up the storage
     *
     * @return true if the storage was set up successfully, false otherwise
     */
    boolean setup();

    /**
     * Stop the storage
     */
    void stop();

    /**
     * Get all the cards from the storage
     *
     * @return a map of all the cards
     */
    CompletableFuture<Map<Long, CardData>> getAll();

    /**
     * Get cards that belong to an owner
     *
     * @param ownerId the unique id of the owner
     * @return a map of the cards that belong to the owner
     */
    CompletableFuture<Map<Long, CardData>> getByOwner(UUID ownerId);

    /**
     * Get a card by its id
     *
     * @param id the id of the card
     * @return the card
     */
    CompletableFuture<CardData> get(long id);

    /**
     * Save a card
     *
     * @param card the card to save
     * @return the id of the card
     */
    CompletableFuture<Long> save(CardData card);

    /**
     * Delete a card
     *
     * @param id the id of the card
     * @return A completable future that completes when the card is deleted
     */
    CompletableFuture<Void> remove(long id);

    /**
     * An input for the {@link CardStorage} constructor
     */
    @Value
    class Input {
        /**
         * The name of the storage
         */
        String name;
        /**
         * The settings of the storage
         */
        Map<String, Object> settings;
    }
}
