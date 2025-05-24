package dev.smartshub.targetBar.bar;

import org.bukkit.boss.BossBar;

public class BarData {
    private final BossBar bossBar;
    private double health;
    private double maxHealth;
    private int timeLeft;

    public BarData(BossBar bossBar, double health, double maxHealth, int timeLeft) {
        this.bossBar = bossBar;
        this.health = health;
        this.maxHealth = maxHealth;
        this.timeLeft = timeLeft;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void decrementTime() {
        timeLeft--;
    }

    public void resetTime(int time) {
        this.timeLeft = time;
    }

    public void updateHealth(double newHealth) {
        this.health = newHealth;
    }

    public void updateMaxHealth(double newMaxHealth) {
        this.maxHealth = newMaxHealth;
    }

    public double getProgress() {
        if (maxHealth <= 0) return 0.0;
        return Math.max(0.0, Math.min(1.0, health / maxHealth));
    }
}

