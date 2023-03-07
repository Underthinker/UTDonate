package io.github.underthinker.utdonate.core.manager;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.expansion.DonateExpansion;
import io.github.underthinker.utdonate.core.entity.expansion.StorageExpansion;
import io.github.underthinker.utdonate.core.entity.expansion.TopUpExpansion;
import lombok.Getter;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.common.ExpansionClassLoader;
import me.hsgamer.hscore.expansion.common.ExpansionManager;
import me.hsgamer.hscore.expansion.manifest.ManifestExpansionDescriptionLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The addon manager
 */
public class DonateExpansionManager extends ExpansionManager {
    @Getter
    private final UTDonateCore core;

    public DonateExpansionManager(@NotNull UTDonateCore core, @NotNull ClassLoader parentClassLoader) {
        super(new File(core.getDataFolder(), "expansion"), new ManifestExpansionDescriptionLoader(), parentClassLoader);
        this.core = core;
        setSortAndFilterFunction(this::sortAndFilter);
    }

    public DonateExpansionManager(@NotNull UTDonateCore core) {
        super(new File(core.getDataFolder(), "expansion"), new ManifestExpansionDescriptionLoader());
        this.core = core;
        setSortAndFilterFunction(this::sortAndFilter);
    }

    public void callPostEnable() {
        call(DonateExpansion.class, DonateExpansion::onPostEnable);
    }

    private @NotNull Map<String, ExpansionClassLoader> sortAndFilter(@NotNull Map<String, ExpansionClassLoader> original) {
        Map<String, ExpansionClassLoader> storageMap = new HashMap<>();
        Map<String, ExpansionClassLoader> topUpMap = new HashMap<>();
        Map<String, ExpansionClassLoader> otherMap = new HashMap<>();
        original.forEach((name, expansionClassLoader) -> {
            Expansion expansion = expansionClassLoader.getExpansion();
            if (expansion instanceof StorageExpansion) {
                storageMap.put(name, expansionClassLoader);
            } else if (expansion instanceof TopUpExpansion) {
                topUpMap.put(name, expansionClassLoader);
            } else {
                otherMap.put(name, expansionClassLoader);
            }
        });
        Map<String, ExpansionClassLoader> sortedMap = new LinkedHashMap<>();
        sortedMap.putAll(storageMap);
        sortedMap.putAll(topUpMap);
        sortedMap.putAll(otherMap);
        return sortedMap;
    }
}
