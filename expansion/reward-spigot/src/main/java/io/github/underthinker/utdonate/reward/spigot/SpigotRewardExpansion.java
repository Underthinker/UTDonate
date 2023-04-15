package io.github.underthinker.utdonate.reward.spigot;

import io.github.underthinker.utdonate.reward.RewardExecutor;
import io.github.underthinker.utdonate.reward.RewardExpansion;

public class SpigotRewardExpansion extends RewardExpansion {
    @Override
    protected RewardExecutor getRewardExecutor() {
        return new SpigotRewardExecutor(this);
    }
}
