package io.github.underthinker.utdonate.thesieutoc;

import io.github.underthinker.utdonate.core.entity.config.SimpleConfig;
import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.hscore.config.annotation.converter.Converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TheSieuTocConfig extends SimpleConfig {

    @ConfigPath(value = "api.version", converter = ApiVersionConverter.class)
    default TheSieuTocApi.Version getApiVersion() {
        return TheSieuTocApi.Version.V1;
    }

    @ConfigPath("api.v1.key")
    default String getApiKey() {
        return "YOUR_API_KEY";
    }

    @ConfigPath("api.v1.secret")
    default String getApiSecret() {
        return "YOUR_API_SECRET";
    }

    @ConfigPath(value = "api.v1.checking.period", converter = TimeConverter.class)
    default long getCheckingPeriod() {
        return 10;
    }

    @ConfigPath("api.v2.key")
    default String getApiV2Key() {
        return "YOUR_API_V2_KEY";
    }

    @ConfigPath("api.v2.callback")
    default String getApiV2Callback() {
        return "https://example.com:8080/utdonate/thesieutoc/callback";
    }

    class ApiVersionConverter implements Converter {

        @Override
        public Object convert(Object raw) {
            return TheSieuTocApi.Version.from((String) raw);
        }

        @Override
        public Object convertToRaw(Object value) {
            return ((TheSieuTocApi.Version) value).name();
        }
    }

    class TimeConverter implements Converter {

        static final Pattern PATTERN = Pattern.compile("(\\d+)((:?tick|[smhd])?)");

        @Override
        public Object convert(Object raw) {
            String time = (String) raw;
            Matcher matcher = PATTERN.matcher(time);
            if (matcher.matches()) {
                int amount = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2);
                switch (unit) {
                    default:
                    case "tick":
                        return amount;
                    case "s":
                        return amount * 20;
                    case "m":
                        return amount * 20 * 60;
                    case "h":
                        return amount * 20 * 60 * 60;
                    case "d":
                        return amount * 20 * 60 * 60 * 24;
                }
            }
            throw new IllegalArgumentException("Invalid time format: " + time);
        }

        @Override
        public Object convertToRaw(Object value) {
            return value;
        }
    }


}
