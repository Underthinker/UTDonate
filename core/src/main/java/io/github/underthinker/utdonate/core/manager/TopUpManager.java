package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;
import io.github.underthinker.utdonate.core.entity.value.CardAndTopUp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The {@link TopUp} manager
 */
public class TopUpManager {
    private final Map<String, TopUp> topUpMap;
    private final UTDonateCore core;

    public TopUpManager(@NotNull UTDonateCore core) {
        this.core = core;
        topUpMap = new HashMap<>();
    }

    /**
     * Register the {@link TopUp}
     *
     * @param name  the name of the {@link TopUp}
     * @param topUp the {@link TopUp}
     */
    public void registerTopUp(@NotNull String name, @NotNull TopUp topUp) {
        topUpMap.put(name, topUp);
    }

    /**
     * Unregister the {@link TopUp}
     *
     * @param name the name of the {@link TopUp}
     */
    public void unregisterTopUp(@NotNull String name) {
        topUpMap.remove(name);
    }

    /**
     * Submit a top-up request for a card
     *
     * @param card The card to top-up
     * @return a future of a flag indicates if the request was submitted successfully
     */
    @NotNull
    public CompletableFuture<Boolean> submit(@NotNull Card card) {
        core.getListenerManager().notifyListeners(ListenerType.SUBMIT, card);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        core.getSchedulerFactory().runTaskAsynchronously(() -> {
            for (Map.Entry<String, TopUp> entry : topUpMap.entrySet()) {
                String topUpName = entry.getKey();
                TopUp topUp = entry.getValue();
                if (topUp.submit(card)) {
                    core.getListenerManager().notifyListeners(ListenerType.SUCCESS_SUBMIT, new CardAndTopUp(topUpName, topUp, card));
                    future.complete(true);
                    return;
                } else {
                    core.getListenerManager().notifyListeners(ListenerType.FAIL_SUBMIT, new CardAndTopUp(topUpName, topUp, card));
                }
            }
            core.getListenerManager().notifyListeners(ListenerType.ALL_FAIL_SUBMIT, card);
            future.complete(false);
        });
        return future;
    }

    /**
     * Mark a card as completed.
     * Used when the {@link TopUp} provider completes checking the card.
     *
     * @param card the {@link Card}
     */
    public void complete(@NotNull Card card) {
        core.getListenerManager().notifyListeners(ListenerType.COMPLETE, card);
    }

    /**
     * Mark a card as failed.
     * Used when the {@link TopUp} provider fails checking the card.
     *
     * @param card the {@link Card}
     */
    public void fail(@NotNull Card card) {
        core.getListenerManager().notifyListeners(ListenerType.FAIL, card);
    }
}
