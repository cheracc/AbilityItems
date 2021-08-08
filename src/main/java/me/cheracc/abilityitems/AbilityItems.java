package me.cheracc.abilityitems;

import me.cheracc.abilityitems.commands.AbilitiesCommand;
import me.cheracc.abilityitems.commands.SetAbility;
import me.cheracc.abilityitems.managers.AbilityManager;
import me.cheracc.abilityitems.managers.GuiManager;
import me.cheracc.abilityitems.managers.KeyManager;
import me.cheracc.abilityitems.managers.SettingsManager;
import me.cheracc.abilityitems.tools.Logr;
import me.cheracc.abilityitems.tools.Scheduler;
import me.cheracc.abilityitems.tools.Translator;
import me.cheracc.abilityitems.types.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class AbilityItems extends JavaPlugin {
    @Override
    public void onEnable() {
        Translator trans = new Translator();
        trans.load(this);
        Logr logr = new Logr(getLogger(), trans);
        Scheduler scheduler = new Scheduler(this);
        KeyManager keys = new KeyManager(this);

        SettingsManager settings = new SettingsManager(getDataFolder(), logr);
        PluginConfig mainConfig = settings.getConfig("config.yml");

        PluginConfig abilityConfig = settings.getConfig("abilities.yml");
        AbilityManager abilityManager = new AbilityManager(abilityConfig, logr, keys);
        abilityManager.registerListener(this);

        GuiManager guiManager = new GuiManager(abilityManager, trans);

        getCommand("setability").setExecutor(new SetAbility(abilityManager, guiManager));
        getCommand("abilities").setExecutor(new AbilitiesCommand(abilityManager, guiManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
