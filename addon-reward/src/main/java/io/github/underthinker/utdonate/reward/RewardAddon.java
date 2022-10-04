package io.github.underthinker.utdonate.reward;

import io.github.underthinker.utdonate.core.PlatformType;
import io.github.underthinker.utdonate.core.entity.addon.DonateAddon;
import io.github.underthinker.utdonate.reward.handler.SpigotRewardExecutor;

public class RewardAddon extends DonateAddon {
    private final RewardConfig config = createConfig("reward", RewardConfig.class);

    @Override
    public boolean onLoad() {
        return getCore().getPlatformType() != PlatformType.UNKNOWN;
    }

    @Override
    public void onEnable() {
        RewardExecutor executor = null;
        if (getCore().getPlatformType() == PlatformType.SPIGOT) {
            executor = new SpigotRewardExecutor(this);
        }
        if (executor != null) {
            registerCompleteListener(executor::execute);
        }
    }

    public RewardConfig getConfig() {
        return config;
    }
}
