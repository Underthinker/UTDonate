package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import me.hsgamer.hscore.common.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Value
public class CardData {
    Card card;
    Map<String, String> data;

    public static Map<String, String> deserializeData(List<String> list) {
        Map<String, String> data = new HashMap<>();
        for (String string : list) {
            String[] split = string.split("=", 2);
            data.put(split[0], split[1]);
        }
        return data;
    }

    public static Map<String, String> deserializeData(String... array) {
        return deserializeData(Arrays.asList(array));
    }

    public static CardData deserialize(Map<String, Object> map) {
        Card card = Card.deserialize(map);
        Map<String, String> data = Optional.ofNullable(map.get("data"))
                .map(o -> CollectionUtils.createStringListFromObject(o, true))
                .map(CardData::deserializeData)
                .orElseGet(HashMap::new);
        return new CardData(card, data);
    }

    public List<String> serializeData() {
        return data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.toList());
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(card.serialize());
        map.put("data", serializeData());
        return map;
    }
}
