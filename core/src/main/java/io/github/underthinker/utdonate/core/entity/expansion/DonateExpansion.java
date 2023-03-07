package io.github.underthinker.utdonate.core.entity.expansion;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.listener.ListenerType;
import io.github.underthinker.utdonate.core.manager.DonateExpansionManager;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.common.ExpansionManager;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;
import me.hsgamer.hscore.expansion.extra.expansion.GetClassLoader;

import java.util.function.Consumer;

/**
 * The base class for expansions
 */
public class DonateExpansion implements Expansion, GetClassLoader, DataFolder {
    /**
     * Called when the expansion is enabled
     */
    public void onPostEnable() {
        // EMPTY
    }

    /**
     * Get the core
     *
     * @return the core
     */
    public UTDonateCore getCore() {
        ExpansionManager expansionManager = getExpansionClassLoader().getManager();
        if (!(expansionManager instanceof DonateExpansionManager)) {
            throw new IllegalStateException("ExpansionManager is not an instance of DonateExpansionManager");
        }
        return ((DonateExpansionManager) expansionManager).getCore();
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
        getCore().getListenerManager().registerListener(listenerType, getExpansionClassLoader().getDescription().getName() + "|" + name, consumer);
    }

    /**
     * Register a listener
     *
     * @param listenerType the type of the listener
     * @param name         the name of the listener
     * @param <T>          the type of the value passed to the consumer
     */
    public <T> void unregisterListener(ListenerType<T> listenerType, String name) {
        getCore().getListenerManager().unregisterListener(listenerType, getExpansionClassLoader().getDescription().getName() + "|" + name);
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
