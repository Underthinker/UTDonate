package io.github.underthinker.utdonate.core;

import io.github.underthinker.utdonate.core.config.ConfigFactory;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;

public interface UTDonateCore {
    SchedulerFactory getSchedulerFactory();

    ConfigFactory getConfigFactory();

    TopUpManager getTopUpManager();

    DonateAddonManager getAddonManager();
}
