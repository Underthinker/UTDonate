package io.github.underthinker.utdonate.core.entity.addon;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;
import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.addon.object.Addon;

import java.util.function.Consumer;

public class DonateAddon extends Addon {
    public UTDonateCore getCore() {
        AddonManager addonManager = getAddonManager();
        if (!(addonManager instanceof DonateAddonManager)) {
            throw new IllegalStateException("AddonManager is not an instance of DonateAddonManager");
        }
        return ((DonateAddonManager) addonManager).getCore();
    }

    public void registerTopUp(String name, TopUp topUp) {
        getCore().getTopUpManager().registerTopUp(name, topUp);
    }

    public void unregisterTopUp(String name) {
        getCore().getTopUpManager().unregisterTopUp(name);
    }

    public void registerSubmitListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerSubmitListener(listener);
    }

    public void registerCompleteListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerCompleteListener(listener);
    }

    public void registerFailCheckListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerFailCheckListener(listener);
    }

    public void registerSuccessCheckListener(Consumer<Card> listener) {
        getCore().getTopUpManager().registerSuccessCheckListener(listener);
    }

    public <T> T createConfig(String name, Class<T> clazz) {
        return getCore().getConfigFactory().createConfig(name, clazz);
    }

    public void completeCard(Card card) {
        getCore().getTopUpManager().complete(card);
    }
}
