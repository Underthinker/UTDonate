package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import lombok.Getter;
import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.addon.loader.ManifestAddonDescriptionLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Logger;

public class DonateAddonManager extends AddonManager {
    @Getter
    private final UTDonateCore core;

    public DonateAddonManager(@NotNull UTDonateCore core, @NotNull Logger logger, @NotNull ClassLoader parentClassLoader) {
        super(new File(core.getDataFolder(), "addons"), logger, new ManifestAddonDescriptionLoader(), parentClassLoader);
        this.core = core;
    }

    public DonateAddonManager(@NotNull UTDonateCore core, @NotNull Logger logger) {
        super(new File(core.getDataFolder(), "addons"), logger, new ManifestAddonDescriptionLoader());
        this.core = core;
    }
}
