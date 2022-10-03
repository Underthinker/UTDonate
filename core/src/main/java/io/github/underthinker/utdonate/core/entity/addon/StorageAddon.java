package io.github.underthinker.utdonate.core.entity.addon;

import io.github.underthinker.utdonate.core.entity.storage.CardStorage;

import java.util.function.Function;

public class StorageAddon extends DonateAddon {
    public void registerCardStorageType(Function<CardStorage.Input, CardStorage> creator, String... name) {
        getCore().getCardStorageManager().register(creator, name);
    }
}
