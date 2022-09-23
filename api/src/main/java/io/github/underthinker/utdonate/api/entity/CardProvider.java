package io.github.underthinker.utdonate.api.entity;

import lombok.Value;

import java.io.Serializable;

@Value
public class CardProvider implements Serializable {
    String value;
    String display;
}
