package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.storage.CardStorage;
import io.github.underthinker.utdonate.core.storage.FlatFileCardStorage;
import io.github.underthinker.utdonate.core.storage.JsonCardStorage;
import lombok.Getter;
import me.hsgamer.hscore.builder.Builder;

import java.io.File;

/**
 * The manager for {@link CardStorage} builders.
 * Used to build {@link CardStorage}.
 */
public class CardStorageManager extends Builder<CardStorage.Input, CardStorage> {
    @Getter
    private final File baseFolder;

    public CardStorageManager(File baseFolder) {
        this.baseFolder = baseFolder;
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
    }

    public CardStorageManager(UTDonateCore core) {
        this(new File(core.getDataFolder(), "storage"));
        register(input -> new FlatFileCardStorage(core, input), "flatfile", "csv");
        register(input -> new JsonCardStorage(core, input), "json");
    }
}
