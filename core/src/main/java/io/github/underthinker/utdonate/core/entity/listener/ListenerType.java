package io.github.underthinker.utdonate.core.entity.listener;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.value.CardAndTopUp;
import lombok.Value;

/**
 * A listener type for {@link io.github.underthinker.utdonate.core.manager.ListenerManager}
 *
 * @param <T> The type of the object that will be passed to the listener
 */
@Value
public class ListenerType<T> {
    /**
     * The type of listeners called when a card is submitted
     */
    public static final ListenerType<Card> SUBMIT = new ListenerType<>("submit", Card.class);
    /**
     * The type of listeners called when a card is completed
     */
    public static final ListenerType<Card> COMPLETE = new ListenerType<>("complete", Card.class);
    /**
     * The type of listeners called when a card is failed
     */
    public static final ListenerType<Card> FAIL = new ListenerType<>("fail", Card.class);
    /**
     * The type of listeners called when a top-up provider accepts a card submission
     */
    public static final ListenerType<CardAndTopUp> SUCCESS_SUBMIT = new ListenerType<>("success_submit", CardAndTopUp.class);
    /**
     * The type of listeners called when a top-up provider rejects a card submission
     */
    public static final ListenerType<CardAndTopUp> FAIL_SUBMIT = new ListenerType<>("fail_submit", CardAndTopUp.class);
    /**
     * The type of listeners called when all top-up providers reject a card submission
     */
    public static final ListenerType<Card> ALL_FAIL_SUBMIT = new ListenerType<>("all_fail_submit", Card.class);

    /**
     * The name of the listener type
     */
    String name;
    /**
     * The class of the object that will be passed to the listener
     */
    Class<T> type;
}
