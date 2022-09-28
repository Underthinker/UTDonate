package io.github.underthinker.utdonate.core.topup;

import io.github.underthinker.utdonate.core.entity.Card;

public interface TopUp {
    boolean submitAndCheck(Card card);
}
