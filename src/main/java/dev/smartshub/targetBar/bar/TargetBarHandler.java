package dev.smartshub.targetBar.bar;

import dev.smartshub.targetBar.group.GroupSettings;
import dev.smartshub.targetBar.group.GroupSettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TargetBarHandler {

    private final Map<UUID, BarData> bars = new ConcurrentHashMap<>();
    private final GroupSettingsManager settingsManager;

    public TargetBarHandler(GroupSettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public void show(Player player, LivingEntity target) {
        UUID uuid = player.getUniqueId();
        GroupSettings settings = settingsManager.getGroupFor(player);

        double current = target.getHealth();
        double max = target.getMaxHealth();
        double percent = Math.max(0.0, Math.min(1.0, current / max));
        String title = settings.getTitle().replace("%hearts%", String.valueOf((int) current));

        BarData existing = bars.get(uuid);
        if (existing != null) {
            existing.updateHealth(current);
            existing.updateMaxHealth(max);
            existing.getBossBar().setProgress(existing.getProgress());
            existing.getBossBar().setTitle(title);
            existing.resetTime(settings.getDuration());
            return;
        }

        BossBar bar = Bukkit.createBossBar(title, settings.getColor(), BarStyle.SOLID);
        bar.setProgress(percent);
        bar.addPlayer(player);
        bar.setVisible(true);

        BarData data = new BarData(bar, current, max, settings.getDuration());
        bars.put(uuid, data);
    }

    public void tick() {
        bars.entrySet().removeIf(entry -> {
            BarData data = entry.getValue();
            data.decrementTime();
            if (data.getTimeLeft() <= 0) {
                data.getBossBar().removeAll();
                return true;
            }
            return false;
        });
    }
}
