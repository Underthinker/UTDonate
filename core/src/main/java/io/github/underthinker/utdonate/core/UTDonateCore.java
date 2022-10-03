package io.github.underthinker.utdonate.core;

import io.github.underthinker.utdonate.core.config.ConfigFactory;
import io.github.underthinker.utdonate.core.manager.CardStorageManager;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;

import java.io.File;
import java.util.logging.Logger;

public interface UTDonateCore {
    SchedulerFactory getSchedulerFactory();

    ConfigFactory getConfigFactory();

    TopUpManager getTopUpManager();

    DonateAddonManager getAddonManager();

    CardStorageManager getCardStorageManager();

    File getDataFolder();

    Logger getLogger();

    default PlatformType getPlatformType() {
        return PlatformType.UNKNOWN;
    }
}
