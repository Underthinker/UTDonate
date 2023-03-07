package io.github.underthinker.utdonate.reward.spigot;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.reward.RewardExecutor;
import io.github.underthinker.utdonate.reward.RewardExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SpigotRewardExecutor extends RewardExecutor {
    public SpigotRewardExecutor(RewardExpansion addon) {
        super(addon);
    }

    @Override
    protected void execute(Card card, List<String> rewards) {
        addon.getCore().getSchedulerFactory().runTask(() -> {
            for (String reward : rewards) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
            }
        });
    }

    @Override
    protected CompletableFuture<String> getOwnerName(Card card) {
        String ownerName;
        if (addon.getConfig().isUseOwnerNameOnly()) {
            ownerName = card.getOwnerName();
        } else {
            ownerName = Optional.ofNullable(card.getOwnerId())
                    .map(Bukkit::getOfflinePlayer)
                    .map(OfflinePlayer::getName)
                    .orElseGet(card::getOwnerName);
        }
        return CompletableFuture.completedFuture(ownerName);
    }
}
