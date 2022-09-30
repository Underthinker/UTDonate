package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;

import java.util.UUID;

@Value
public class Card {
    UUID ownerId;
    String serial;
    String pin;
    int price;
    String provider;
}
