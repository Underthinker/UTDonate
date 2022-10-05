package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Value
public class Card {
    @Nullable
    UUID ownerId;
    @Nullable
    String ownerName;
    String serial;
    String pin;
    int denomination;
    String provider;

    public static Card deserialize(Map<String, Object> map) {
        return new Card(
                Optional.ofNullable(map.get("ownerId")).map(Objects::toString).map(UUID::fromString).orElse(null),
                Optional.ofNullable(map.get("ownerName")).map(Objects::toString).orElse(null),
                Objects.toString(map.get("serial")),
                Objects.toString(map.get("pin")),
                Integer.parseInt(Objects.toString(map.get("denomination"))),
                Objects.toString(map.get("provider"))
        );
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("ownerId", ownerId == null ? null : ownerId.toString());
        map.put("ownerName", ownerName);
        map.put("serial", serial);
        map.put("pin", pin);
        map.put("denomination", Integer.toString(denomination));
        map.put("provider", provider);
        return map;
    }
}
