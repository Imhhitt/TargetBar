package dev.smartshub.targetBar.group;

import org.bukkit.boss.BarColor;

public class GroupSettings {
    private final String title;
    private final String permission;
    private final BarColor color;
    private final int duration;

    public GroupSettings(String title, String permission, BarColor color, int duration) {
        this.title = title;
        this.permission = permission;
        this.color = color;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public BarColor getColor() {
        return color;
    }

    public int getDuration() {
        return duration;
    }
}
