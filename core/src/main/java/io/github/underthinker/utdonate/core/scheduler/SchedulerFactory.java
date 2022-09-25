package io.github.underthinker.utdonate.core.scheduler;

public interface SchedulerFactory {
    SchedulerTask runTask(Runnable runnable);

    SchedulerTask runTaskTimer(Runnable runnable, long delay, long period);

    SchedulerTask runTaskLater(Runnable runnable, long delay);

    SchedulerTask runTaskAsynchronously(Runnable runnable);

    SchedulerTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

    SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay);
}
