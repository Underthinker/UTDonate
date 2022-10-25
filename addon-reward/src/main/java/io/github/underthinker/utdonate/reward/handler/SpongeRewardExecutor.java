package io.github.underthinker.utdonate.reward.handler;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.reward.RewardAddon;
import io.github.underthinker.utdonate.reward.RewardExecutor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class SpongeRewardExecutor extends RewardExecutor {
    public SpongeRewardExecutor(RewardAddon addon) {
        super(addon);
    }

    @Override
    protected void execute(Card card, List<String> rewards) {
        addon.getCore().getSchedulerFactory().runTask(() -> {
            for (String reward : rewards) {
                try {
                    Sponge.server().commandManager().process(reward);
                } catch (CommandException e) {
                    addon.getCore().getLogger().log(Level.WARNING, e, () -> "Error while executing command: " + reward);
                }
            }
        });
    }

    @Override
    protected CompletableFuture<String> getOwnerName(Card card) {
        if (addon.getConfig().isUseOwnerNameOnly()) {
            return CompletableFuture.completedFuture(card.getOwnerName());
        } else {
            return Optional.ofNullable(card.getOwnerId())
                    .map(uuid -> Sponge.server().gameProfileManager().profile(uuid).thenApply(profile -> profile.name().orElse(card.getOwnerName())))
                    .orElse(CompletableFuture.completedFuture(card.getOwnerName()));
        }
    }
}
