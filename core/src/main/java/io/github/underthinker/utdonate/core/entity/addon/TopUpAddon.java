package io.github.underthinker.utdonate.core.entity.addon;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;

public class TopUpAddon extends DonateAddon {
    public void registerTopUp(String name, TopUp topUp) {
        getCore().getTopUpManager().registerTopUp(name, topUp);
    }

    public void unregisterTopUp(String name) {
        getCore().getTopUpManager().unregisterTopUp(name);
    }

    public void completeCard(Card card) {
        getCore().getTopUpManager().complete(card);
    }
}
