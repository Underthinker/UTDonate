package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Value
public class Card {
    @Nullable
    UUID ownerId;
    @Nullable
    String ownerName;
    String serial;
    String pin;
    int price;
    String provider;
}
