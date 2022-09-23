package io.github.underthinker.utdonate.api.entity;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class Card implements Serializable {
    UUID cardID;
    UUID ownerID;
    String serial;
    String pin;
    int price;
    String provider;
}
