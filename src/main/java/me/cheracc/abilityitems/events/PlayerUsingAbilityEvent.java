package me.cheracc.abilityitems.events;

import me.cheracc.abilityitems.types.abilities.Ability;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerUsingAbilityEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Ability ability;
    private final ItemStack abilityItem;
    private final Location interactionPoint;
    private boolean cancelled;

    public PlayerUsingAbilityEvent(@NotNull Player player, @NotNull Ability ability, ItemStack abilityItem, Location interactionPoint) {
        super(player);
        this.ability = ability;
        this.abilityItem = abilityItem;
        this.interactionPoint = interactionPoint;
        cancelled = false;
    }

    public @NotNull Ability getAbility() {
        return ability;
    }

    public ItemStack getAbilityItem() {
        return abilityItem;
    }

    public Location getInteractionPoint() {
        return interactionPoint;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = true;
    }
}
