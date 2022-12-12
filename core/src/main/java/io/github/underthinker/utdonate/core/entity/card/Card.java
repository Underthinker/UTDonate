package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A card
 */
@Value
public class Card {
    /**
     * The unique id of the owner of the card
     */
    @Nullable
    UUID ownerId;
    /**
     * The name of the owner of the card
     */
    @Nullable
    String ownerName;
    /**
     * The serial number of the card
     */
    String serial;
    /**
     * The pin of the card
     */
    String pin;
    /**
     * The amount of money on the card
     */
    int denomination;
    /**
     * The provider of the card
     */
    String provider;

    /**
     * Deserializes a card from a value map
     *
     * @param map The value map
     * @return The deserialized card
     */
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

    /**
     * Serializes the card to a value map
     *
     * @return The serialized card
     */
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
