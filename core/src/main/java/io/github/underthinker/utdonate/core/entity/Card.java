package io.github.underthinker.utdonate.core.entity;

import lombok.Value;
import lombok.With;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Value
public class Card implements Serializable {
    UUID cardID;
    UUID ownerID;
    String serial;
    String pin;
    int price;
    String provider;
    @With
    Date date;
    @With
    CardStatus status;
}
