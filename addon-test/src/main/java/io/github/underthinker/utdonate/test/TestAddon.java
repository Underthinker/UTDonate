package io.github.underthinker.utdonate.test;

import io.github.underthinker.utdonate.core.entity.addon.DonateAddon;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;
import io.github.underthinker.utdonate.core.entity.storage.CardStorage;

import java.util.Collections;
import java.util.UUID;

public class TestAddon extends DonateAddon {
    private final TestConfig config = createConfig("test-config", TestConfig.class);
    private final CardStorage cardStorage = getCore().getCardStorageManager()
            .build(config.getTestStorageType(), new CardStorage.Input("test-storage", Collections.emptyMap()))
            .orElseThrow(() -> new RuntimeException("Failed to create test storage"));

    @Override
    public void onEnable() {
        cardStorage.setup();
        registerListener(ListenerType.COMPLETE, "test", card -> cardStorage.save(new CardData(card, Collections.emptyMap())));
    }

    @Override
    public void onPostEnable() {
        getCore().getTopUpManager().complete(new Card(
                UUID.randomUUID(),
                "test",
                "test",
                "test",
                100,
                "test"
        ));
    }

    @Override
    public void onDisable() {
        cardStorage.stop();
        unregisterListener(ListenerType.COMPLETE, "test");
    }
}
