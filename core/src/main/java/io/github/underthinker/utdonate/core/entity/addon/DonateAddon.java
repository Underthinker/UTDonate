package io.github.underthinker.utdonate.core.entity.addon;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.addon.object.Addon;

import java.util.function.Consumer;

/**
 * The base class for addons
 */
public class DonateAddon extends Addon {
    /**
     * Get the core
     *
     * @return the core
     */
    public UTDonateCore getCore() {
        AddonManager addonManager = getAddonManager();
        if (!(addonManager instanceof DonateAddonManager)) {
            throw new IllegalStateException("AddonManager is not an instance of DonateAddonManager");
        }
        return ((DonateAddonManager) addonManager).getCore();
    }

    /**
     * Register a listener
     *
     * @param listenerType the type of the listener
     * @param name         the name of the listener
     * @param consumer     the listener
     * @param <T>          the type of the value passed to the consumer
     */
    public <T> void registerListener(ListenerType<T> listenerType, String name, Consumer<T> consumer) {
        getCore().getListenerManager().registerListener(listenerType, getDescription().getName() + "|" + name, consumer);
    }

    /**
     * Register a listener
     *
     * @param listenerType the type of the listener
     * @param name         the name of the listener
     * @param <T>          the type of the value passed to the consumer
     */
    public <T> void unregisterListener(ListenerType<T> listenerType, String name) {
        getCore().getListenerManager().unregisterListener(listenerType, getDescription().getName() + "|" + name);
    }

    /**
     * Creates a new Config object.
     *
     * @param name        the name of the config
     * @param configClass the class of the config interface
     * @param <T>         the type of the config interface
     * @return the new Config object
     */
    public <T> T createConfig(String name, Class<T> configClass) {
        return getCore().getConfigFactory().createConfig(name, configClass);
    }
}
