package io.github.underthinker.utdonate.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public final class Card implements Serializable {
    private final UUID cardID;
    private final UUID ownerID;
    private final String serial;
    private final String pin;
    private final CardPrice price;
    private final CardProvider provider;
}
