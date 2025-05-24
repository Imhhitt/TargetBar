package dev.smartshub.targetBar.bar;

import org.bukkit.scheduler.BukkitRunnable;

public class BarTicker extends BukkitRunnable {

    private final TargetBarHandler targetBarHandler;

    public BarTicker(TargetBarHandler targetBarHandler) {
        this.targetBarHandler = targetBarHandler;
    }

    @Override
    public void run() {
        targetBarHandler.tick();
    }
}
