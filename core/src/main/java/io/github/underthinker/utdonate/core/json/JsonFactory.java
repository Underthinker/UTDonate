package io.github.underthinker.utdonate.core.json;

import java.util.List;
import java.util.Map;

/**
 * A factory for Json operations
 */
public interface JsonFactory {
    /**
     * Serialize an object to a Json string
     *
     * @param object The object to serialize
     * @return The Json string
     */
    String serialize(Object object);

    /**
     * Deserialize a Json string to a map
     *
     * @param json The Json string to deserialize
     * @return The map
     */
    Map<String, Object> deserialize(String json);

    /**
     * Deserialize a Json string to a list of maps
     *
     * @param json The Json string to deserialize
     * @return The list of maps
     */
    List<Map<String, Object>> deserializeAsList(String json);
}
