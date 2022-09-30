package io.github.underthinker.utdonate.core;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;

public interface UTDonateCore {
    SchedulerFactory getSchedulerFactory();

    TopUpManager getTopUpManager();

    DonateAddonManager getAddonManager();

    default void completeCard(Card card) {
        getTopUpManager().complete(card);
    }
}
