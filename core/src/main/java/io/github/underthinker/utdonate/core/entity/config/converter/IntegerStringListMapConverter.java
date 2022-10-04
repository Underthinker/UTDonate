package io.github.underthinker.utdonate.core.entity.config.converter;

import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.annotation.converter.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IntegerStringListMapConverter implements Converter {
    @Override
    public Object convert(Object raw) {
        if (!(raw instanceof Map)) return null;
        Map<Integer, List<String>> map = new HashMap<>();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) raw).entrySet()) {
            try {
                map.put(Integer.parseInt(entry.getKey().toString()), CollectionUtils.createStringListFromObject(entry.getValue()));
            } catch (NumberFormatException ignored) {
                // IGNORED
            }
        }
        return map;
    }

    @Override
    public Object convertToRaw(Object value) {
        Map<String, Object> map = new HashMap<>();
        if (value instanceof Map) {
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                map.put(Objects.toString(entry.getKey(), "-1"), entry.getValue());
            }
        }
        return map;
    }
}
