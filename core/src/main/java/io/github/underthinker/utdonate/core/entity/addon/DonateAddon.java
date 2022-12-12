package io.github.underthinker.utdonate.core.entity.addon;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.addon.object.Addon;

import java.util.function.BiConsumer;
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
     * Register a listener called when a card is submitted
     *
     * @param listener the listener
     */
    public void registerSubmitListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerSubmitListener(listener);
    }

    /**
     * Register a listener called when a card is completed
     *
     * @param listener the listener
     */
    public void registerCompleteListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerCompleteListener(listener);
    }

    /**
     * Register a listener called when a card is failed
     *
     * @param listener the listener
     */
    public void registerFailListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerFailListener(listener);
    }

    /**
     * Register a listener called when a top-up provider accepts a card
     *
     * @param listener the listener
     */
    public void registerSuccessCheckListener(BiConsumer<String, Card> listener) {
        getCore().getTopUpManager().registerSuccessCheckListener(listener);
    }

    /**
     * Register a listener called when a top-up provider accepts a card submission
     *
     * @param listener the listener
     */
    public void registerSuccessCheckListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerSuccessCheckListener(listener);
    }

    /**
     * Register a listener called when a top-up provider rejects a card submission
     *
     * @param listener the listener
     */
    public void registerFailCheckListener(BiConsumer<String, Card> listener) {
        getCore().getTopUpManager().registerFailCheckListener(listener);
    }

    /**
     * Register a listener called when a top-up provider rejects a card submission
     *
     * @param listener the listener
     */
    public void registerFailCheckListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerFailCheckListener(listener);
    }

    /**
     * Register a listener called when all top-up providers reject a card submission
     *
     * @param listener the listener
     */
    public void registerAllFailCheckListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerAllFailCheckListener(listener);
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
