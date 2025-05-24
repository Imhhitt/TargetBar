package dev.smartshub.targetBar.storage;

import dev.smartshub.targetBar.TargetBar;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private static final Map<String, Configuration> configs = new HashMap<>();
    private static TargetBar plugin;

    public static void init(TargetBar pluginInstance) {
        plugin = pluginInstance;
        load("config");
        load("lang");
    }

    private static void load(String name) {
        String fileName = fixName(name);
        File file = new File(plugin.getDataFolder(), fileName);
        Configuration config = new Configuration(plugin, file, fileName);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        configs.put(fileName, config);
    }

    public static void reload(String name) {
        String fileName = fixName(name);
        Configuration config = configs.get(fileName);
        if (config != null) {
            config.reloadFile();
        }
    }

    public static void save(String name) {
        String fileName = fixName(name);
        Configuration config = configs.get(fileName);
        if (config != null) {
            config.saveFile();
        }
    }

    public static Configuration get(String name) {
        return configs.get(fixName(name));
    }

    private static String fixName(String name) {
        return name.endsWith(".yml") ? name : name + ".yml";
    }
}
