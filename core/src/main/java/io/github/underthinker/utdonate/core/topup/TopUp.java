package io.github.underthinker.utdonate.core.topup;

import io.github.underthinker.utdonate.core.entity.card.Card;

public interface TopUp {
    boolean submitAndCheck(Card card);
}
