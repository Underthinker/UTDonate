package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import me.hsgamer.hscore.common.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A card with extra data
 */
@Value
public class CardData {
    /**
     * The card
     */
    Card card;
    /**
     * The extra data
     */
    Map<String, String> data;

    /**
     * Deserialize the extra data
     *
     * @param list the list
     * @return the extra data
     */
    public static Map<String, String> deserializeData(List<String> list) {
        Map<String, String> data = new HashMap<>();
        for (String string : list) {
            String[] split = string.split("=", 2);
            data.put(split[0], split[1]);
        }
        return data;
    }

    /**
     * Deserialize the extra data
     *
     * @param array the array
     * @return the extra data
     */
    public static Map<String, String> deserializeData(String... array) {
        return deserializeData(Arrays.asList(array));
    }

    /**
     * Deserialize the card data
     *
     * @param map the map
     * @return the card data
     */
    public static CardData deserialize(Map<String, Object> map) {
        Card card = Card.deserialize(map);
        Map<String, String> data = Optional.ofNullable(map.get("data"))
                .map(o -> CollectionUtils.createStringListFromObject(o, true))
                .map(CardData::deserializeData)
                .orElseGet(HashMap::new);
        return new CardData(card, data);
    }

    /**
     * Serialize the extra data
     *
     * @return the list
     */
    public List<String> serializeData() {
        return data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList());
    }

    /**
     * Serialize the card data
     *
     * @return the map
     */
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(card.serialize());
        map.put("data", serializeData());
        return map;
    }
}
