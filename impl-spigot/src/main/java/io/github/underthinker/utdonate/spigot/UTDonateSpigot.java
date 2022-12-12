package io.github.underthinker.utdonate.spigot;

import io.github.underthinker.utdonate.core.PlatformType;
import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.manager.CardStorageManager;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.ListenerManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import lombok.Getter;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;

@Getter
public class UTDonateSpigot extends BasePlugin implements UTDonateCore {
    private final SpigotSchedulerFactory schedulerFactory = new SpigotSchedulerFactory(this);
    private final SpigotConfigFactory configFactory = new SpigotConfigFactory(this);
    private final TopUpManager topUpManager = new TopUpManager(this);
    private final ListenerManager listenerManager = new ListenerManager(this);
    private final DonateAddonManager addonManager = new DonateAddonManager(this, getClassLoader());
    private final CardStorageManager cardStorageManager = new CardStorageManager(this);
    private final SpigotJsonFactory jsonFactory = new SpigotJsonFactory();

    @Override
    public void enable() {
        addonManager.loadAddons();
    }

    @Override
    public void postEnable() {
        addonManager.enableAddons();
        addonManager.callPostEnable();
    }

    @Override
    public void disable() {
        addonManager.disableAddons();
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.SPIGOT;
    }
}
