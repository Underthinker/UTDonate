package io.github.underthinker.utdonate.core.entity.expansion;

import io.github.underthinker.utdonate.core.entity.storage.CardStorage;

import java.util.function.Function;

/**
 * A special type of addon that provides {@link CardStorage}
 */
public class StorageExpansion extends DonateExpansion {
    /**
     * Register a new {@link CardStorage} type
     *
     * @param creator the function that creates the {@link CardStorage}
     * @param name    the name of the {@link CardStorage} type
     */
    public void registerCardStorageType(Function<CardStorage.Input, CardStorage> creator, String... name) {
        getCore().getCardStorageManager().register(creator, name);
    }
}
