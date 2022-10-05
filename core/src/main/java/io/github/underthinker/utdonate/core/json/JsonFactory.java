package io.github.underthinker.utdonate.core.json;

import java.util.List;
import java.util.Map;

public interface JsonFactory {
    String serialize(Object object);

    Map<String, Object> deserialize(String json);

    List<Map<String, Object>> deserializeAsList(String json);
}
