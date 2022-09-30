package io.github.underthinker.utdonate.core.config;

public interface ConfigFactory {
    <T> T createConfig(String name, Class<T> configClass);
}
