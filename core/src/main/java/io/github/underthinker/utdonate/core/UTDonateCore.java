package io.github.underthinker.utdonate.core;

import io.github.underthinker.utdonate.core.config.ConfigFactory;
import io.github.underthinker.utdonate.core.json.JsonFactory;
import io.github.underthinker.utdonate.core.manager.CardStorageManager;
import io.github.underthinker.utdonate.core.manager.DonateExpansionManager;
import io.github.underthinker.utdonate.core.manager.ListenerManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;

import java.io.File;
import java.util.logging.Logger;

/**
 * The core
 */
public interface UTDonateCore {
    /**
     * Get the scheduler factory
     *
     * @return the scheduler factory
     */
    SchedulerFactory getSchedulerFactory();

    /**
     * Get the config factory
     *
     * @return the config factory
     */
    ConfigFactory getConfigFactory();

    /**
     * Get the {@link io.github.underthinker.utdonate.core.entity.topup.TopUp} manager
     *
     * @return the {@link io.github.underthinker.utdonate.core.entity.topup.TopUp} manager
     */
    TopUpManager getTopUpManager();

    /**
     * Get the listener manager
     */
    ListenerManager getListenerManager();

    /**
     * Get the expansion manager
     *
     * @return the expansion manager
     */
    DonateExpansionManager getExpansionManager();

    /**
     * Get the {@link io.github.underthinker.utdonate.core.entity.storage.CardStorage} manager.
     * Used to create {@link io.github.underthinker.utdonate.core.entity.storage.CardStorage} instances.
     *
     * @return the {@link io.github.underthinker.utdonate.core.entity.storage.CardStorage} manager
     */
    CardStorageManager getCardStorageManager();

    /**
     * Get the json factory
     *
     * @return the json factory
     */
    JsonFactory getJsonFactory();

    /**
     * Get the data folder of the core
     *
     * @return the folder
     */
    File getDataFolder();

    /**
     * Get the logger
     *
     * @return the logger
     */
    Logger getLogger();
}
