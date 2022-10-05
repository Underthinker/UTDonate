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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TopUpManager {
    private final Map<String, TopUp> topUpMap;
    private final List<Consumer<Card>> submitListeners;
    private final List<BiConsumer<String, Card>> failCheckListeners;
    private final List<BiConsumer<String, Card>> successCheckListeners;
    private final List<Consumer<Card>> allFailCheckListeners;
    private final List<Consumer<Card>> completeListeners;
    private final List<Consumer<Card>> failListeners;
    private final UTDonateCore core;

    public TopUpManager(@NotNull UTDonateCore core) {
        this.core = core;
        topUpMap = new HashMap<>();
        submitListeners = new LinkedList<>();
        failCheckListeners = new LinkedList<>();
        successCheckListeners = new LinkedList<>();
        allFailCheckListeners = new LinkedList<>();
        completeListeners = new LinkedList<>();
        failListeners = new LinkedList<>();
    }

    private void notifyListeners(List<Consumer<Card>> listeners, Card card) {
        core.getSchedulerFactory().runTask(() -> {
            for (Consumer<Card> listener : listeners) {
                listener.accept(card);
            }
        });
    }

    private void notifyListeners(List<BiConsumer<String, Card>> listeners, String topUpName, Card card) {
        core.getSchedulerFactory().runTask(() -> {
            for (BiConsumer<String, Card> listener : listeners) {
                listener.accept(topUpName, card);
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

    public void registerFailListener(@NotNull Consumer<Card> listener) {
        failListeners.add(listener);
    }

    public void registerSuccessCheckListener(@NotNull BiConsumer<String, Card> listener) {
        successCheckListeners.add(listener);
    }

    public void registerSuccessCheckListener(@NotNull Consumer<Card> listener) {
        successCheckListeners.add((s, card) -> listener.accept(card));
    }

    public void registerFailCheckListener(@NotNull BiConsumer<String, Card> listener) {
        failCheckListeners.add(listener);
    }

    public void registerFailCheckListener(@NotNull Consumer<Card> listener) {
        failCheckListeners.add((s, card) -> listener.accept(card));
    }

    public void registerAllFailCheckListener(@NotNull Consumer<Card> listener) {
        allFailCheckListeners.add(listener);
    }

    @NotNull
    public CompletableFuture<Boolean> submitAndCheck(@NotNull Card card) {
        notifyListeners(submitListeners, card);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, TopUp> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                if (entry.getValue().submitAndCheck(card)) {
                    notifyListeners(successCheckListeners, topUpName, card);
                    future.complete(true);
                    return;
                } else {
                    notifyListeners(failCheckListeners, topUpName, card);
                }
            }
            notifyListeners(allFailCheckListeners, card);
            future.complete(false);
        });
        return future;
    }

    public void complete(@NotNull Card card) {
        notifyListeners(completeListeners, card);
    }

    public void fail(@NotNull Card card) {
        notifyListeners(failListeners, card);
    }
}
