package io.github.underthinker.utdonate.core.entity.topup;

import io.github.underthinker.utdonate.core.entity.card.CardProperty;
import lombok.Value;

import java.util.List;

@Value
public class TopUpSubmitResponse {
    boolean success;
    List<CardProperty> cardProperties;
}
