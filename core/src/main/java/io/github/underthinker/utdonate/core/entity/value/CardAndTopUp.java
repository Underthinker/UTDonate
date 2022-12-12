package io.github.underthinker.utdonate.core.entity.value;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;
import lombok.Value;

@Value
public class CardAndTopUp {
    String topUpName;
    TopUp topUp;
    Card card;
}
