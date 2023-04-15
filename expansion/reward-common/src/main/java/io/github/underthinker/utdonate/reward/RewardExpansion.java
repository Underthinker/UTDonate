package io.github.underthinker.utdonate.reward;

import io.github.underthinker.utdonate.core.entity.expansion.DonateExpansion;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;

public abstract class RewardExpansion extends DonateExpansion {
    private final RewardConfig config = createConfig("reward", RewardConfig.class);
    private RewardExecutor rewardExecutor;

    protected abstract RewardExecutor getRewardExecutor();

    @Override
    public boolean onLoad() {
        rewardExecutor = getRewardExecutor();
        return rewardExecutor != null;
    }

    @Override
    public void onEnable() {
        registerListener(ListenerType.COMPLETE, "reward", rewardExecutor::execute);
    }

    @Override
    public void onDisable() {
        unregisterListener(ListenerType.COMPLETE, "reward");
    }

    public RewardConfig getConfig() {
        return config;
    }
}
