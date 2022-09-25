package io.github.underthinker.utdonate.core.entity;

import lombok.Value;
import lombok.With;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Value
public class Card implements Serializable {
    @With
    UUID cardId;
    UUID ownerId;
    String serial;
    String pin;
    int price;
    String provider;
    @With
    Date date;

    public static Card fromInput(CardInput input) {
        return new Card(UUID.randomUUID(), input.getOwnerId(), input.getSerial(), input.getPin(), input.getPrice(), input.getProvider(), new Date());
    }
}
