package io.github.underthinker.utdonate.reward;

import io.github.underthinker.utdonate.core.entity.config.SimpleConfig;
import io.github.underthinker.utdonate.core.entity.config.converter.IntegerStringListMapConverter;
import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.hscore.config.annotation.StickyValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RewardConfig extends SimpleConfig {
    @StickyValue
    @ConfigPath(value = "rewards", converter = IntegerStringListMapConverter.class)
    default Map<Integer, List<String>> getRewards() {
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(10000, Collections.singletonList("give {owner} diamond 1"));
        map.put(20000, Collections.singletonList("give {owner} diamond 2"));
        return map;
    }

    @ConfigPath("use-owner-name-only")
    default boolean isUseOwnerNameOnly() {
        return false;
    }
}
