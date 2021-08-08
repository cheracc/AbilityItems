package me.cheracc.abilityitems.abilities;

import me.cheracc.abilityitems.types.*;
import me.cheracc.abilityitems.types.abilities.*;
import me.cheracc.abilityitems.types.abilities.configuration.AbilityConfigurationOptions;
import me.cheracc.abilityitems.types.abilities.configuration.ConfigOption;
import me.cheracc.abilityitems.types.abilities.configuration.ConfigOptionType;
import me.cheracc.abilityitems.types.abilities.interfaces.ActiveAbility;
import me.cheracc.abilityitems.types.abilities.interfaces.ConfigurableAbility;
import me.cheracc.abilityitems.types.abilities.interfaces.Cooldown;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Teleport extends Ability implements Cooldown, ConfigurableAbility, ActiveAbility {
    private final AbilityConfigurationOptions configurationOptions;
    @ConfigOption(type = ConfigOptionType.SMALL_INTEGER)
    private int cooldown;
    @ConfigOption(type = ConfigOptionType.SMALL_INTEGER)
    private int maxDistance;
    @ConfigOption(type = ConfigOptionType.CYCLE)
    private boolean allowTeleportingIntoMidair;
    @ConfigOption(type = ConfigOptionType.CYCLE)
    private boolean leaveTracer;

    public Teleport(String customName, String description, PluginConfig abilitiesConfig) {
        super(customName, description);
        cooldown = 30;
        maxDistance = 20;
        allowTeleportingIntoMidair = false;
        leaveTracer = true;
        this.description = "Teleports a player some distance in the direction they are looking.";
        configurationOptions = new AbilityConfigurationOptions(this, abilitiesConfig);
    }

    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public AbilityConfigurationOptions getConfigurationOptions() {
        return configurationOptions;
    }

    @Override
    public void doAbility(Player player, ItemStack item, Location interactionPoint) {

    }
}
