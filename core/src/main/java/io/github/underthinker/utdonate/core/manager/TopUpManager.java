package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.Card;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TopUpManager {
    private final Map<String, Predicate<Card>> topUpMap;
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

    public void registerTopUp(String name, Predicate<Card> topUp) {
        topUpMap.put(name, topUp);
    }

    public void registerSubmitListener(Consumer<Card> listener) {
        submitListeners.add(listener);
    }

    public void registerCompleteListener(Consumer<Card> listener) {
        completeListeners.add(listener);
    }

    private static void notifyListeners(List<Consumer<Card>> listeners, Card card) {
        listeners.forEach(listener -> listener.accept(card));
    }

    public CompletableFuture<Boolean> submitAndCheck(Card card) {
        notifyListeners(submitListeners, card);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, Predicate<Card>> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                if (entry.getValue().test(card)) {
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
