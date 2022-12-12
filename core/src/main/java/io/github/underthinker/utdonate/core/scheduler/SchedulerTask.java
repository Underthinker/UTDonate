package io.github.underthinker.utdonate.core.scheduler;

/**
 * Represents a task that can be scheduled by the scheduler.
 */
public interface SchedulerTask {
    /**
     * Cancel the task
     */
    void cancel();
}
