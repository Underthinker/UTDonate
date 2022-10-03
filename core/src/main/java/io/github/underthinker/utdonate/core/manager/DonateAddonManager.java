package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.addon.StorageAddon;
import io.github.underthinker.utdonate.core.entity.addon.TopUpAddon;
import lombok.Getter;
import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.addon.loader.ManifestAddonDescriptionLoader;
import me.hsgamer.hscore.addon.object.Addon;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

    @Override
    protected @NotNull Map<String, Addon> sortAndFilter(@NotNull Map<String, Addon> original) {
        Map<String, Addon> storageAddonMap = new HashMap<>();
        Map<String, Addon> topUpAddonMap = new HashMap<>();
        Map<String, Addon> otherAddonMap = new HashMap<>();
        original.forEach((name, addon) -> {
            if (addon instanceof StorageAddon) {
                storageAddonMap.put(name, addon);
            } else if (addon instanceof TopUpAddon) {
                topUpAddonMap.put(name, addon);
            } else {
                otherAddonMap.put(name, addon);
            }
        });
        Map<String, Addon> sortedMap = new LinkedHashMap<>();
        sortedMap.putAll(storageAddonMap);
        sortedMap.putAll(topUpAddonMap);
        sortedMap.putAll(otherAddonMap);
        return sortedMap;
    }
}
