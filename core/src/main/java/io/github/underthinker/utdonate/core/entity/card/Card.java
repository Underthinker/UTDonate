package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import lombok.With;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Value
public class Card implements Serializable {
    UUID ownerId;
    String serial;
    String pin;
    int price;
    String provider;
    String topUpName;
    @With
    Date date;
    @With
    List<CardProperty> cardProperties;

    public static Card fromInput(CardInput input, String topUpName, List<CardProperty> cardProperties) {
        return new Card(
                input.getOwnerId(),
                input.getSerial(),
                input.getPin(),
                input.getPrice(),
                input.getProvider(),
                topUpName,
                new Date(),
                cardProperties
        );
    }
}
