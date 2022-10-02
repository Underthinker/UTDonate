package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;
import org.jetbrains.annotations.NotNull;

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

    public TopUpManager(@NotNull UTDonateCore core) {
        this.core = core;
        topUpMap = new HashMap<>();
        submitListeners = new LinkedList<>();
        failCheckListeners = new LinkedList<>();
        successCheckListeners = new LinkedList<>();
        completeListeners = new LinkedList<>();
    }

    private void notifyListeners(List<Consumer<Card>> listeners, Card card) {
        core.getSchedulerFactory().runTask(() -> {
            for (Consumer<Card> listener : listeners) {
                listener.accept(card);
            }
        });
    }

    public void registerTopUp(@NotNull String name, @NotNull TopUp topUp) {
        topUpMap.put(name, topUp);
    }

    public void unregisterTopUp(@NotNull String name) {
        topUpMap.remove(name);
    }

    public void registerSubmitListener(@NotNull Consumer<Card> listener) {
        submitListeners.add(listener);
    }

    public void registerCompleteListener(@NotNull Consumer<Card> listener) {
        completeListeners.add(listener);
    }

    public void registerFailCheckListener(@NotNull Consumer<Card> listener) {
        failCheckListeners.add(listener);
    }

    public void registerSuccessCheckListener(@NotNull Consumer<Card> listener) {
        successCheckListeners.add(listener);
    }

    @NotNull
    public CompletableFuture<Boolean> submitAndCheck(@NotNull Card card) {
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

    public void complete(@NotNull Card card) {
        notifyListeners(completeListeners, card);
    }
}