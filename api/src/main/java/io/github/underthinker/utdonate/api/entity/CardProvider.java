package io.github.underthinker.utdonate.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardProvider implements Serializable {
    private final String value;
    private final String display;
}
