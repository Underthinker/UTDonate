package io.github.underthinker.utdonate.core.entity.topup;

import io.github.underthinker.utdonate.core.entity.card.Card;

public interface TopUp {
    boolean submit(Card card);
}
