package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardInput;
import io.github.underthinker.utdonate.core.entity.topup.TopUpProgressResponse;
import io.github.underthinker.utdonate.core.entity.topup.TopUpSubmitResponse;
import io.github.underthinker.utdonate.core.topup.TopUp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// TODO: Better manager
public class TopUpManager {
    private final Map<String, TopUp> topUpMap = new HashMap<>();
    private final UTDonateCore core;

    public TopUpManager(UTDonateCore core) {
        this.core = core;
    }

    public void registerTopUp(String name, TopUp topUp) {
        topUpMap.put(name, topUp);
    }

    public CompletableFuture<Boolean> submitAndCheck(CardInput cardInput) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, TopUp> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                TopUp topUp = entry.getValue();
                TopUpSubmitResponse response = topUp.submitAndCheck(cardInput);
                if (response.isSuccess()) {
                    Card card = Card.fromInput(cardInput, topUpName, response.getCardProperties());
                    core.getPendingCardStorage().save(card);
                    future.complete(true);
                    return;
                }
            }
            future.complete(false);
        });
        return future;
    }

    public CompletableFuture<TopUpProgressResponse> checkProgress(Card card) {
        CompletableFuture<TopUpProgressResponse> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            TopUp topUp = topUpMap.get(card.getTopUpName());
            if (topUp == null) {
                future.completeExceptionally(new IllegalArgumentException("Top up not found"));
                return;
            }
            future.complete(topUp.checkProgress(card));
        });
        return future;
    }
}
