package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;

import java.util.Map;

@Value
public class CardData {
    long id;
    Card card;
    Map<String, String> data;
}
