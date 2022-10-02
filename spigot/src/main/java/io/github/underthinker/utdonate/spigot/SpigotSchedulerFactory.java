package io.github.underthinker.utdonate.spigot;

import io.github.underthinker.utdonate.core.scheduler.SchedulerFactory;
import io.github.underthinker.utdonate.core.scheduler.SchedulerTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class SpigotSchedulerFactory implements SchedulerFactory {
    private final UTDonateSpigot plugin;

    public SpigotSchedulerFactory(UTDonateSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public SchedulerTask runTaskTimer(Runnable runnable, long delay, long period) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
        return task::cancel;
    }

    @Override
    public SchedulerTask runTaskLater(Runnable runnable, long delay) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        return task::cancel;
    }

    @Override
    public SchedulerTask runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
        return task::cancel;
    }

    @Override
    public SchedulerTask runTaskLaterAsynchronously(Runnable runnable, long delay) {
        BukkitTask task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
        return task::cancel;
    }
}
