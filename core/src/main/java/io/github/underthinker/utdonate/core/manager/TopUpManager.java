package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.topup.TopUp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TopUpManager {
    private final Map<String, TopUp> topUpMap;
    private final List<Consumer<Card>> submitListeners;
    private final List<Consumer<Card>> failCheckListeners;
    private final List<Consumer<Card>> successCheckListeners;
    private final List<Consumer<Card>> completeListeners;
    private final UTDonateCore core;

    public TopUpManager(UTDonateCore core) {
        this.core = core;
        topUpMap = new HashMap<>();
        submitListeners = new LinkedList<>();
        failCheckListeners = new LinkedList<>();
        successCheckListeners = new LinkedList<>();
        completeListeners = new LinkedList<>();
    }

    private static void notifyListeners(List<Consumer<Card>> listeners, Card card) {
        listeners.forEach(listener -> listener.accept(card));
    }

    public void registerTopUp(String name, TopUp topUp) {
        topUpMap.put(name, topUp);
    }

    public void unregisterTopUp(String name) {
        topUpMap.remove(name);
    }

    public void registerSubmitListener(Consumer<Card> listener) {
        submitListeners.add(listener);
    }

    public void registerCompleteListener(Consumer<Card> listener) {
        completeListeners.add(listener);
    }

    public void registerFailCheckListener(Consumer<Card> listener) {
        failCheckListeners.add(listener);
    }

    public void registerSuccessCheckListener(Consumer<Card> listener) {
        successCheckListeners.add(listener);
    }

    public CompletableFuture<Boolean> submitAndCheck(Card card) {
        notifyListeners(submitListeners, card);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, TopUp> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                if (entry.getValue().submitAndCheck(card)) {
                    notifyListeners(successCheckListeners, card);
                    future.complete(true);
                    return;
                }
            }
            notifyListeners(failCheckListeners, card);
            future.complete(false);
        });
        return future;
    }

    public void complete(Card card) {
        notifyListeners(completeListeners, card);
    }
}
