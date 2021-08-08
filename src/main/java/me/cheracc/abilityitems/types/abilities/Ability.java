package me.cheracc.abilityitems.types.abilities;

import me.cheracc.abilityitems.events.PlayerUsingAbilityEvent;
import me.cheracc.abilityitems.types.abilities.configuration.ConfigOption;
import me.cheracc.abilityitems.types.abilities.configuration.ConfigOptionType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class Ability implements Serializable {
    @ConfigOption(type = ConfigOptionType.SHORT_STRING)
    protected String customName;
    @ConfigOption(type = ConfigOptionType.LONG_STRING)
    protected String description;

    public Ability(String customName, String description) {
        this.customName = customName;
        this.description = description;
    }

    public void execute(Player player, ItemStack item, Location interactionPoint) {
        new PlayerUsingAbilityEvent(player, this, item, interactionPoint).callEvent();
    }

    public String getAbilityName() {
        return customName;
    }

    public String getAbilityDescription() {
        return description;
    }
}
