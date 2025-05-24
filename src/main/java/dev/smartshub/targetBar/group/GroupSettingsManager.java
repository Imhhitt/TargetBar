package dev.smartshub.targetBar.group;

import org.bukkit.boss.BarColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class GroupSettingsManager {

    private final Map<String, GroupSettings> groups = new HashMap<>();
    private final GroupSettings fallback;

    public GroupSettingsManager(ConfigurationSection section) {
        if (!section.isConfigurationSection("groups")) {
            throw new IllegalArgumentException("No 'groups' section in config!");
        }

        ConfigurationSection groupsSection = section.getConfigurationSection("groups");
        GroupSettings defaultGroup = null;

        for (String key : groupsSection.getKeys(false)) {
            ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
            String title = groupSection.getString("title", "%hearts%");
            String permission = groupSection.getString("show-permission", "hearts.default.show");
            String colorStr = groupSection.getString("bar-color", "PINK");
            int duration = groupSection.getInt("duration", 5);

            BarColor color = BarColor.valueOf(colorStr.toUpperCase(Locale.ROOT));
            GroupSettings settings = new GroupSettings(title, permission, color, duration);

            groups.put(key, settings);
            if (key.equalsIgnoreCase("default")) {
                defaultGroup = settings;
            }
        }

        if (defaultGroup == null) {
            throw new IllegalStateException("You must define a 'default' group!");
        }

        this.fallback = defaultGroup;
    }

    public GroupSettings getGroupFor(Player player) {
        for (GroupSettings settings : groups.values()) {
            if (player.hasPermission(settings.getPermission())) {
                return settings;
            }
        }
        return fallback;
    }
}
