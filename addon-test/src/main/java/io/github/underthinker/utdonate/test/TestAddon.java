package io.github.underthinker.utdonate.test;

import io.github.underthinker.utdonate.core.PlatformType;
import io.github.underthinker.utdonate.core.entity.addon.DonateAddon;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import io.github.underthinker.utdonate.core.entity.storage.CardStorage;

import java.util.Collections;
import java.util.UUID;

public class TestAddon extends DonateAddon {
    private final TestConfig config = createConfig("test-config", TestConfig.class);
    private final CardStorage cardStorage = getCore().getCardStorageManager()
            .build(config.getTestStorageType(), new CardStorage.Input("test-storage", Collections.emptyMap()))
            .orElseThrow(() -> new RuntimeException("Failed to create test storage"));

    @Override
    public boolean onLoad() {
        return getCore().getPlatformType() == PlatformType.SPIGOT;
    }

    @Override
    public void onEnable() {
        cardStorage.setup();
    }

    @Override
    public void onPostEnable() {
        cardStorage.save(new CardData(
                new Card(
                        UUID.randomUUID(),
                        "test-card",
                        "test-card",
                        "test-card",
                        100000,
                        "test-card"
                ),
                Collections.emptyMap()
        ));
    }

    @Override
    public void onDisable() {
        cardStorage.stop();
    }
}