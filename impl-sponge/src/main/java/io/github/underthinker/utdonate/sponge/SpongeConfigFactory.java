package io.github.underthinker.utdonate.sponge;

import io.github.underthinker.utdonate.core.config.ConfigFactory;
import me.hsgamer.hscore.config.configurate.ConfigurateConfig;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;

public class SpongeConfigFactory implements ConfigFactory {
    private final UTDonateSponge plugin;

    public SpongeConfigFactory(UTDonateSponge plugin) {
        this.plugin = plugin;
    }

    @Override
    public <T> T createConfig(String name, Class<T> configClass) {
        return ConfigGenerator.newInstance(configClass, new ConfigurateConfig(new File(plugin.getDataFolder(), name + ".hocon"), HoconConfigurationLoader.builder()));
    }
}
