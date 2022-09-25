package io.github.underthinker.utdonate.core.scheduler;

public interface SchedulerFactory {
    SchedulerTask runTaskTimer(Runnable runnable, long delay, long period);

    SchedulerTask runTaskLater(Runnable runnable, long delay);

    default SchedulerTask runTask(Runnable runnable) {
        return runTaskLater(runnable, 0);
    }

    SchedulerTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

    SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay);

    default SchedulerTask runTaskAsynchronously(Runnable runnable) {
        return runTaskLaterAsynchronously(runnable, 0);
    }
}
