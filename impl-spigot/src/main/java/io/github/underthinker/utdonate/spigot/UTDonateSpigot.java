package io.github.underthinker.utdonate.spigot;

import io.github.underthinker.utdonate.core.PlatformType;
import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.config.ConfigFactory;
import io.github.underthinker.utdonate.core.json.JsonFactory;
import io.github.underthinker.utdonate.core.manager.CardStorageManager;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;

public class UTDonateSpigot extends BasePlugin implements UTDonateCore {
    private final SpigotSchedulerFactory schedulerFactory = new SpigotSchedulerFactory(this);
    private final SpigotConfigFactory configFactory = new SpigotConfigFactory(this);
    private final TopUpManager topUpManager = new TopUpManager(this);
    private final DonateAddonManager donateAddonManager = new DonateAddonManager(this, getClassLoader());
    private final CardStorageManager cardStorageManager = new CardStorageManager(this);
    private final SpigotJsonFactory jsonFactory = new SpigotJsonFactory();

    @Override
    public void enable() {
        donateAddonManager.loadAddons();
    }

    @Override
    public void postEnable() {
        donateAddonManager.enableAddons();
        donateAddonManager.callPostEnable();
    }

    @Override
    public void disable() {
        donateAddonManager.disableAddons();
    }

    @Override
    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    @Override
    public ConfigFactory getConfigFactory() {
        return configFactory;
    }

    @Override
    public TopUpManager getTopUpManager() {
        return topUpManager;
    }

    @Override
    public DonateAddonManager getAddonManager() {
        return donateAddonManager;
    }

    @Override
    public CardStorageManager getCardStorageManager() {
        return cardStorageManager;
    }

    @Override
    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.SPIGOT;
    }
}
