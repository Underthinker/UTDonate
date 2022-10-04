package io.github.underthinker.utdonate.reward.handler;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.reward.RewardAddon;
import io.github.underthinker.utdonate.reward.RewardExecutor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Optional;

public class SpigotRewardExecutor extends RewardExecutor {
    public SpigotRewardExecutor(RewardAddon addon) {
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
    protected String getOwnerName(Card card) {
        String ownerName;
        if (addon.getConfig().isUseOwnerNameOnly()) {
            ownerName = card.getOwnerName();
        } else {
            ownerName = Optional.ofNullable(card.getOwnerId())
                    .map(Bukkit::getOfflinePlayer)
                    .map(OfflinePlayer::getName)
                    .orElseGet(card::getOwnerName);
        }
        return ownerName;
    }
}
