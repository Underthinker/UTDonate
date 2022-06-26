package io.github.underthinker.utdonate.api.storage;

import io.github.underthinker.utdonate.api.entity.Card;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuiltinCardStorage implements CardStorage {

    private static final Logger LOGGER = Logger.getLogger(BuiltinCardStorage.class.getSimpleName());
    private final File file;

    public BuiltinCardStorage(File file) {
        this.file = file;
        try {
            if (!file.exists()) {
                Files.createFile(file.toPath());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, () -> String.format("Error when creating new file %s!!!", file.getName()));
        }
    }

    @Override
    public CompletableFuture<Collection<Card>> load() {
        return CompletableFuture.supplyAsync(() -> {
            Collection<Card> cards;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                cards = (Collection<Card>) ois.readObject();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e, () -> String.format("Error when loading data from file %s, falling back to empty list!!!", file.getName()));
                cards = Collections.emptyList();
            }
            return cards;
        });
    }

    @Override
    public CompletableFuture<Void> save(Collection<Card> cards) {
        return CompletableFuture.runAsync(() -> {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(cards);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e, () -> String.format("Error when saving data to file %s!!!", file.getName()));
            }
        });
    }
}
