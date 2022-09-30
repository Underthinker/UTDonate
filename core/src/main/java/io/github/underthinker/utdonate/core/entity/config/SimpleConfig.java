package io.github.underthinker.utdonate.core.entity.config;

import me.hsgamer.hscore.config.Config;

public interface SimpleConfig {
    Config getConfig();

    void reloadConfig();
}
