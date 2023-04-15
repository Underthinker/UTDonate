package io.github.underthinker.utdonate.test;

import io.github.underthinker.utdonate.core.entity.config.SimpleConfig;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface TestConfig extends SimpleConfig {
    @ConfigPath("test")
    default String getTestString() {
        return "test";
    }

    @ConfigPath("test-storage-type")
    default String getTestStorageType() {
        return "flatfile";
    }
}
