package io.github.underthinker.utdonate.core;

import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;
import io.github.underthinker.utdonate.core.storage.CardStorage;

public interface UTDonateCore {
    SchedulerFactory getSchedulerFactory();

    CardStorage getPendingCardStorage();
}
