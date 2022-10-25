package io.github.underthinker.utdonate.reward;

import io.github.underthinker.utdonate.core.entity.addon.DonateAddon;
import io.github.underthinker.utdonate.reward.handler.SpigotRewardExecutor;
import io.github.underthinker.utdonate.reward.handler.SpongeRewardExecutor;

public class RewardAddon extends DonateAddon {
    private final RewardConfig config = createConfig("reward", RewardConfig.class);
    private RewardExecutor rewardExecutor;

    @Override
    public boolean onLoad() {
        switch (getCore().getPlatformType()) {
            case SPIGOT:
                rewardExecutor = new SpigotRewardExecutor(this);
                break;
            case SPONGE:
                rewardExecutor = new SpongeRewardExecutor(this);
                break;
            case UNKNOWN:
                return false;
        }
        return rewardExecutor != null;
    }

    @Override
    public void onEnable() {
        registerCompleteListener(rewardExecutor::execute);
    }

    public RewardConfig getConfig() {
        return config;
    }
}
