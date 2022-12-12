package io.github.underthinker.utdonate.core.scheduler;

/**
 * A factory to schedule {@link SchedulerTask}
 */
public interface SchedulerFactory {
    /**
     * Schedule a task to run repeatedly
     *
     * @param runnable the task to run
     * @param delay    the delay before the first run
     * @param period   the delay between each run
     * @return the {@link SchedulerTask}
     */
    SchedulerTask runTaskTimer(Runnable runnable, long delay, long period);

    /**
     * Schedule a task to run later
     *
     * @param runnable the task to run
     * @param delay    the delay before the run
     * @return the {@link SchedulerTask}
     */
    SchedulerTask runTaskLater(Runnable runnable, long delay);

    /**
     * Schedule a task to run immediately
     *
     * @param runnable the task to run
     * @return the {@link SchedulerTask}
     */
    default SchedulerTask runTask(Runnable runnable) {
        return runTaskLater(runnable, 0);
    }

    /**
     * Schedule a task to run repeatedly asynchronously
     *
     * @param runnable the task to run
     * @param delay    the delay before the first run
     * @param period   the delay between each run
     * @return the {@link SchedulerTask}
     */
    SchedulerTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period);

    /**
     * Schedule a task to run later asynchronously
     *
     * @param runnable the task to run
     * @param delay    the delay before the run
     * @return the {@link SchedulerTask}
     */
    SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay);

    /**
     * Schedule a task to run immediately asynchronously
     *
     * @param runnable the task to run
     * @return the {@link SchedulerTask}
     */
    default SchedulerTask runTaskAsynchronously(Runnable runnable) {
        return runTaskLaterAsynchronously(runnable, 0);
    }
}
