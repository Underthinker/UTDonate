package io.github.underthinker.utdonate.core.config;

/**
 * Factory for creating Config objects.
 */
public interface ConfigFactory {
    /**
     * Creates a new Config object.
     *
     * @param name        the name of the config
     * @param configClass the class of the config interface
     * @param <T>         the type of the config interface
     * @return the new Config object
     */
    <T> T createConfig(String name, Class<T> configClass);
}
