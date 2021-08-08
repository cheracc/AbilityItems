package me.cheracc.abilityitems.managers;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class KeyManager {
    private final JavaPlugin plugin;

    public KeyManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public NamespacedKey getNamespacedKey(String string) {
        return new NamespacedKey(plugin, string);
    }
}
