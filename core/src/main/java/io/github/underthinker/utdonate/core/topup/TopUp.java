package io.github.underthinker.utdonate.core.topup;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardInput;
import io.github.underthinker.utdonate.core.entity.topup.TopUpProgressResponse;
import io.github.underthinker.utdonate.core.entity.topup.TopUpSubmitResponse;

public interface TopUp {
    TopUpSubmitResponse submitAndCheck(CardInput cardInput);

    TopUpProgressResponse checkProgress(Card cardInput);
}
