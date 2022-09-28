package io.github.underthinker.utdonate.core.entity.card;

import lombok.Value;

import java.io.Serializable;

@Value
public class CardProperty implements Serializable {
    String name;
    String value;
}
