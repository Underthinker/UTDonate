package io.github.underthinker.utdonate.core.topup;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardInput;
import io.github.underthinker.utdonate.core.entity.topup.TopUpProgressResponse;
import io.github.underthinker.utdonate.core.entity.topup.TopUpSubmitResponse;

import java.util.concurrent.CompletableFuture;

public interface TopUp {
    CompletableFuture<TopUpSubmitResponse> submitAndCheck(CardInput cardInput);

    CompletableFuture<TopUpProgressResponse> checkProgress(Card cardInput);
}
