package io.github.underthinker.utdonate.core.entity.expansion;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;

/**
 * A special type of addon that provides {@link TopUp} services
 */
public class TopUpExpansion extends DonateExpansion {
    /**
     * Register the {@link TopUp}
     *
     * @param name  the name of the {@link TopUp}
     * @param topUp the {@link TopUp}
     */
    public void registerTopUp(String name, TopUp topUp) {
        getCore().getTopUpManager().registerTopUp(name, topUp);
    }

    /**
     * Unregister the {@link TopUp}
     *
     * @param name the name of the {@link TopUp}
     */
    public void unregisterTopUp(String name) {
        getCore().getTopUpManager().unregisterTopUp(name);
    }

    /**
     * Mark a card as completed.
     * Used when the {@link TopUp} provider completes checking the card.
     *
     * @param card the {@link Card}
     */
    public void completeCard(Card card) {
        getCore().getTopUpManager().complete(card);
    }

    /**
     * Mark a card as failed.
     * Used when the {@link TopUp} provider fails checking the card.
     *
     * @param card the {@link Card}
     */
    public void failCard(Card card) {
        getCore().getTopUpManager().fail(card);
    }
}
