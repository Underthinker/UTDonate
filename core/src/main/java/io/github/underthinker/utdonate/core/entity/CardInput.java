package io.github.underthinker.utdonate.core.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class CardInput {
    UUID ownerId;
    String serial;
    String pin;
    int price;
    String provider;
}