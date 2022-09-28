package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class TopUpManager {
    private final Map<String, Predicate<Card>> topUpMap = new HashMap<>();
    private final UTDonateCore core;

    public TopUpManager(UTDonateCore core) {
        this.core = core;
    }

    public void registerTopUp(String name, Predicate<Card> topUp) {
        topUpMap.put(name, topUp);
    }

    public CompletableFuture<Boolean> submitAndCheck(Card card) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, Predicate<Card>> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                if (entry.getValue().test(card)) {
                    future.complete(true);
                    return;
                }
            }
            future.complete(false);
        });
        return future;
    }

    public void complete(Card card) {
        // TODO: Implement
    }
}
