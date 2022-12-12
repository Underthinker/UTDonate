package io.github.underthinker.utdonate.core.entity.topup;

import io.github.underthinker.utdonate.core.entity.card.Card;

/**
 * A top-up service
 */
public interface TopUp {
    /**
     * Submit a top-up request for a card
     *
     * @param card The card to top-up
     * @return true if the request was submitted successfully, false otherwise
     */
    boolean submit(Card card);
}
