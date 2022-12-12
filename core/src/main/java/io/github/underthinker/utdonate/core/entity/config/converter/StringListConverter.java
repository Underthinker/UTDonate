package io.github.underthinker.utdonate.core.entity.config.converter;

import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.annotation.converter.Converter;

/**
 * A converter for a list of strings
 */
public class StringListConverter implements Converter {
    @Override
    public Object convert(Object raw) {
        return CollectionUtils.createStringListFromObject(raw);
    }

    @Override
    public Object convertToRaw(Object value) {
        return value;
    }
}
