package me.cheracc.abilityitems.types;

import me.cheracc.abilityitems.tools.Logr;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PluginConfig {
    protected final File configFile;
    private final FileConfiguration config;
    private final Logr logr;

    public PluginConfig(File file, Logr logr) {
        this.config = new YamlConfiguration();
        this.configFile = file;
        this.logr = logr;
    }

    public <T> T get(String configOption, Class<T> clas) {
        return config.getObject(configOption, clas);
    }

    public boolean getBool(String configOption) {
        return config.getBoolean(configOption);
    }

    public boolean getBool(String configOption, boolean defaultValue) {
        if (config.contains(configOption))
            return config.getBoolean(configOption);
        else
            return defaultValue;
    }

    public String getString(String configOption, String defaultValue) {
        if (config.contains(configOption))
            return config.getString(configOption);
        else
            return defaultValue;
    }

    public void set(String configOption, Object value) {
        config.set(configOption, value);
        logr.debug("Changed option %s in configuration file %s to %s", configOption, configFile.getName(), value);
        save();
    }

    private void save() {
        try {
            config.save(configFile);
            logr.debug("Saved configuration file %s", configFile.getName());
        } catch (IOException e) {
            logr.warn("Could not save configuration file %s", configFile.getName());
        }
    }

    public boolean load() {
        try {
            boolean newFile = false;
            if (!configFile.exists()) {
                logr.debug("Creating blank configuration file %s", configFile.getName());
                configFile.createNewFile();
                newFile = true;
            }
            config.load(configFile);
            if (!newFile)
                logr.debug("Loading configuration file %s", configFile.getName());
            return true;
        } catch (IOException | InvalidConfigurationException e) {
            logr.warn("Could not load configuration file %s");
            logr.warn(e.toString());
            return false;
        }
    }

    public int getInt(String configOption, int defaultValue) {
        return config.getInt(configOption, defaultValue);
    }

    public boolean isSet(String configOption) {
        return config.isSet(configOption);
    }

    public List<String> getStringList(String configOption) {
        return config.getStringList(configOption);
    }
}
