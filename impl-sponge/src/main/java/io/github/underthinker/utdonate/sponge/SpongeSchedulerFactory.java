package io.github.underthinker.utdonate.sponge;

import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;
import io.github.underthinker.utdonate.core.scheduler.SchedulerTask;
import org.spongepowered.api.Game;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;

public class SpongeSchedulerFactory implements SchedulerFactory {
    private final UTDonateSponge plugin;

    public SpongeSchedulerFactory(UTDonateSponge plugin) {
        this.plugin = plugin;
    }

    private Scheduler getScheduler() {
        Game game = plugin.getGame();
        if (game.isServerAvailable()) {
            return game.server().scheduler();
        } else if (game.isClientAvailable()) {
            return game.client().scheduler();
        } else {
            throw new IllegalStateException("No server or client available");
        }
    }

    private Scheduler getAsyncScheduler() {
        return plugin.getGame().asyncScheduler();
    }

    @Override
    public SchedulerTask runTaskTimer(Runnable runnable, long delay, long period) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .interval(Ticks.of(period))
                .build();
        ScheduledTask scheduledTask = getScheduler().submit(task);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask runTaskLater(Runnable runnable, long delay) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .build();
        ScheduledTask scheduledTask = getScheduler().submit(task);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .interval(Ticks.of(period))
                .build();
        ScheduledTask scheduledTask = getAsyncScheduler().submit(task);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .build();
        ScheduledTask scheduledTask = getAsyncScheduler().submit(task);
        return scheduledTask::cancel;
    }
}
