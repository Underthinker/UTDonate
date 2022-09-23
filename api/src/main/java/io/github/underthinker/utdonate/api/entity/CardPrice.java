package io.github.underthinker.utdonate.api.entity;

import lombok.Value;

import java.io.Serializable;

@Value
public class CardPrice implements Serializable {
    long value;
    String display;
}
