package me.cheracc.abilityitems.tools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Translator {
    private FileConfiguration config;
    private File configFile;

    public void load(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "translations.yml");
        config = new YamlConfiguration();

        try {
            if (configFile.exists())
                config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String late(String string, Object... args) {
        return String.format(late(string), args);
    }


    public String late(String string) {
        if (string == null || string.isEmpty())
            return string;
        int stringAsKey = string.hashCode();
        String callingClass = getCaller()[0];
        String callingMethod = getCaller()[1];

        if (callingMethod.contains("$"))
            callingMethod = callingMethod.split("\\$")[1];

        if (config.contains(callingClass + "." + callingMethod + "." + stringAsKey))
            return config.getString(callingClass + "." + callingMethod + "." + stringAsKey);
        else {
            config.set(callingClass + "." + callingMethod + "." + stringAsKey, string);
        }
        saveFile();
        return string;
    }

    public Component lateToComponent(String string, Object... args) {
        String translated = late(string, args);

        if (translated.contains("&"))
            translated = ChatColor.translateAlternateColorCodes('&', translated);

        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();
        return serializer.deserialize(translated).decoration(TextDecoration.ITALIC, false);
    }

    private String[] getCaller() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int i = 1;

        for (; i < elements.length; i++) {
            StackTraceElement e = elements[i];
            if (!e.getClassName().equals(Translator.class.getName()))
                break;
        }
        String[] fullClassName = elements[i].getClassName().split("\\.");
        return new String[]{fullClassName[fullClassName.length - 1], elements[i].getMethodName()};
    }
}
