package me.cheracc.abilityitems.managers;

import me.cheracc.abilityitems.tools.Logr;
import me.cheracc.abilityitems.types.PluginConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private final Map<String, PluginConfig> configs;
    private final File pluginDataDirectory;
    private final Logr logr;

    public SettingsManager(File dataDirectory, Logr logr) {
        pluginDataDirectory = dataDirectory;
        configs = new HashMap<>();
        this.logr = logr;

        if (!pluginDataDirectory.exists())
            pluginDataDirectory.mkdirs();
    }

    public PluginConfig getConfig(String filename) {
        if (configs.containsKey(filename.toLowerCase()))
            return configs.get(filename.toLowerCase());

        File file = new File(pluginDataDirectory, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logr.warn("Unable to create config file %s", filename);
                logr.warn(e.toString());
            }
        }
        PluginConfig config = new PluginConfig(file, logr);
        config.load();
        configs.put(filename.toLowerCase(), config);

        return config;
    }
}
