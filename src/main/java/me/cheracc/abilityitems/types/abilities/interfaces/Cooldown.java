package me.cheracc.abilityitems.types.abilities.interfaces;

import me.cheracc.abilityitems.events.PlayerUsingAbilityEvent;
import me.cheracc.abilityitems.types.abilities.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface Cooldown extends Listener {
    int getCooldown();

    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    default void cancelIfOnCooldown(PlayerUsingAbilityEvent event) {
        if (this instanceof Ability && event.getAbility().equals(this)) {
            Player p = event.getPlayer();
            ItemStack item = event.getAbilityItem();

            if (p.hasCooldown(item.getType())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    default void applyCooldownWhenSuccessful(PlayerUsingAbilityEvent event) {
        if (this instanceof Ability && event.getAbility().equals(this)) {
            Player p = event.getPlayer();
            ItemStack item = event.getAbilityItem();

            p.setCooldown(item.getType(), 20 * getCooldown());
        }
    }
}
