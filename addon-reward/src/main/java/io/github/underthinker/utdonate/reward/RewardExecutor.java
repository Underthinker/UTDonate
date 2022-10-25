package io.github.underthinker.utdonate.reward;

import io.github.underthinker.utdonate.core.entity.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class RewardExecutor {
    protected final RewardAddon addon;

    protected RewardExecutor(RewardAddon addon) {
        this.addon = addon;
    }

    protected abstract void execute(Card card, List<String> rewards);

    protected abstract CompletableFuture<String> getOwnerName(Card card);

    public void execute(Card card) {
        getOwnerName(card).thenAccept(ownerName -> {
            if (ownerName == null) {
                return;
            }
            List<String> rewards = new ArrayList<>();
            for (String reward : addon.getConfig().getRewards().getOrDefault(card.getDenomination(), Collections.emptyList())) {
                rewards.add(reward
                        .replace("{owner}", ownerName)
                        .replace("{serial}", card.getSerial())
                        .replace("{pin}", card.getPin())
                        .replace("{price}", Integer.toString(card.getDenomination()))
                        .replace("{provider}", card.getProvider())
                );
            }
            execute(card, rewards);
        });
    }
}
