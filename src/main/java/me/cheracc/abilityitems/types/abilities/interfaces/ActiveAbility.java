package me.cheracc.abilityitems.types.abilities.interfaces;

import me.cheracc.abilityitems.events.PlayerUsingAbilityEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface ActiveAbility extends Listener {
    void doAbility(Player player, ItemStack item, Location interactionPoint);

    @EventHandler (ignoreCancelled = true, priority = EventPriority.MONITOR)
    default void listenForAbilityUsage(PlayerUsingAbilityEvent event) {
        if (event.getAbility().equals(this))
            doAbility(event.getPlayer(), event.getAbilityItem(), event.getInteractionPoint());
    }
}
