package io.github.underthinker.utdonate.core.entity.config;

import me.hsgamer.hscore.config.Config;

/**
 * A simple config interface
 */
public interface SimpleConfig {
    Config getConfig();

    void reloadConfig();
}
