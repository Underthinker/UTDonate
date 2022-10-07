package io.github.underthinker.utdonate.sponge;

import com.google.inject.Inject;
import io.github.underthinker.utdonate.core.PlatformType;
import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.manager.CardStorageManager;
import io.github.underthinker.utdonate.core.manager.DonateAddonManager;
import io.github.underthinker.utdonate.core.manager.TopUpManager;
import lombok.Getter;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Getter
@Plugin("utdonate")
public class UTDonateSponge implements UTDonateCore {
    private final PluginContainer pluginContainer;
    private final Game game;
    private final Logger logger;
    private final File dataFolder;
    private final TopUpManager topUpManager;
    private final DonateAddonManager addonManager;
    private final SpongeJsonFactory jsonFactory;
    private final CardStorageManager cardStorageManager;
    private final SpongeConfigFactory configFactory;
    private final SpongeSchedulerFactory schedulerFactory;

    @Inject
    public UTDonateSponge(PluginContainer pluginContainer, Game game, @ConfigDir(sharedRoot = false) Path dataFolder) {
        this.pluginContainer = pluginContainer;
        this.game = game;
        this.logger = Logger.getLogger("UTDonate");
        this.dataFolder = dataFolder.toFile();
        this.topUpManager = new TopUpManager(this);
        this.addonManager = new DonateAddonManager(this, getClass().getClassLoader());
        this.jsonFactory = new SpongeJsonFactory();
        this.cardStorageManager = new CardStorageManager(this);
        this.configFactory = new SpongeConfigFactory(this);
        this.schedulerFactory = new SpongeSchedulerFactory(this);
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        addonManager.loadAddons();
    }

    @Listener
    public void onEnable(StartedEngineEvent<Server> event) {
        addonManager.enableAddons();
        addonManager.callPostEnable();
    }

    @Listener
    public void onDisable(StoppingEngineEvent<Server> event) {
        addonManager.disableAddons();
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.SPONGE;
    }
}
