package io.github.underthinker.utdonate.spigot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.underthinker.utdonate.core.json.JsonFactory;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpigotJsonFactory implements JsonFactory {
    private final Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }

    @Override
    public Map<String, Object> deserialize(String json) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(json, type);
        return Optional.ofNullable(map).orElseGet(Collections::emptyMap);
    }

    @Override
    public List<Map<String, Object>> deserializeAsList(String json) {
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        List<Map<String, Object>> list = gson.fromJson(json, type);
        return Optional.ofNullable(list).orElseGet(Collections::emptyList);
    }
}
