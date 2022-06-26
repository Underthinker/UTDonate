package io.github.underthinker.utdonate.api.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardPrice implements Serializable {
    private final long value;
    private final String display;
}
