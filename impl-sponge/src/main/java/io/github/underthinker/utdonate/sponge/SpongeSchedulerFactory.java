package io.github.underthinker.utdonate.sponge;

import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;
import io.github.underthinker.utdonate.core.scheduler.SchedulerTask;
import org.spongepowered.api.scheduler.ScheduledTask;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;

public class SpongeSchedulerFactory implements SchedulerFactory {
    private final UTDonateSponge plugin;

    public SpongeSchedulerFactory(UTDonateSponge plugin) {
        this.plugin = plugin;
    }

    @Override
    public SchedulerTask runTaskTimer(Runnable runnable, long delay, long period) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .interval(Ticks.of(period))
                .build();
        ScheduledTask scheduledTask = plugin.getGame().server().scheduler().submit(task);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask runTaskLater(Runnable runnable, long delay) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .build();
        ScheduledTask scheduledTask = plugin.getGame().server().scheduler().submit(task);
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
        ScheduledTask scheduledTask = plugin.getGame().asyncScheduler().submit(task);
        return scheduledTask::cancel;
    }

    @Override
    public SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        Task task = Task.builder()
                .plugin(plugin.getPluginContainer())
                .execute(runnable)
                .delay(Ticks.of(delay))
                .build();
        ScheduledTask scheduledTask = plugin.getGame().asyncScheduler().submit(task);
        return scheduledTask::cancel;
    }
}
