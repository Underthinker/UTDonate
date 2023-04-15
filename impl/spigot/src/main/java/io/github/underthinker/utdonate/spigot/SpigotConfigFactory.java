package io.github.underthinker.utdonate.spigot;

import io.github.underthinker.utdonate.core.config.ConfigFactory;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;

public class SpigotConfigFactory implements ConfigFactory {

    private final UTDonateSpigot plugin;

    public SpigotConfigFactory(UTDonateSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public <T> T createConfig(String name, Class<T> configClass) {
        return ConfigGenerator.newInstance(configClass, new BukkitConfig(plugin, name + ".yml"));
    }
}
