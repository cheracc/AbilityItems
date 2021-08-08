package me.cheracc.abilityitems.managers;

import me.cheracc.abilityitems.tools.Logr;
import me.cheracc.abilityitems.types.PluginConfig;
import me.cheracc.abilityitems.types.abilities.Ability;
import me.cheracc.abilityitems.types.abilities.AbilityDataType;
import me.cheracc.abilityitems.types.abilities.interfaces.ConfigurableAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class AbilityManager implements Listener {
    private final PluginConfig abilityConfig;
    private final KeyManager keys;
    private final List<Ability> baseAbilities;
    private final List<Ability> loadedAbilities;
    private final Logr logr;

    public AbilityManager(PluginConfig abilityConfig, Logr logr, KeyManager keys) {
        baseAbilities = new ArrayList<>();
        loadedAbilities = new ArrayList<>();
        this.abilityConfig = abilityConfig;
        this.logr = logr;
        this.keys = keys;
        loadDefaultAbilities();
    }

    public List<Ability> getLoadedAbilities() {
        return new ArrayList<>(loadedAbilities);
    }

    public List<Ability> getBaseAbilities() {
        return new ArrayList<>(baseAbilities);
    }

    public void registerListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Ability getAbilityFromItem(ItemStack item) {
        if (item.getItemMeta() == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (pdc.isEmpty())
            return null;

        return pdc.get(keys.getNamespacedKey("ability"), new AbilityDataType());
    }

    public ItemStack saveAbilityToItem(Ability ability, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(keys.getNamespacedKey("ability"), new AbilityDataType(), ability);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void listenForAbilities(PlayerInteractEvent event) {
        // detects when a player uses an ability item and executes the ability
            Player p = event.getPlayer();
            ItemStack item = p.getInventory().getItemInMainHand();
            Ability ability = getAbilityFromItem(item);

            if (item == null || ability == null) {
                item = p.getInventory().getItemInOffHand();
            }

            if (item == null || ability == null)
                return;

            event.setUseInteractedBlock(Event.Result.ALLOW);

            if (item.getType() != Material.SHIELD)
                event.setUseItemInHand(Event.Result.DENY);
            else
                event.setUseItemInHand(Event.Result.ALLOW);

            if (p.hasCooldown(item.getType()))
                return;

            ability.execute(p, item, event.getInteractionPoint());
    }

    private void loadDefaultAbilities() {
        Set<Class<?>> abilityClasses = new HashSet<>((new Reflections("me.cheracc.abilityitems.abilities", new SubTypesScanner(false))).getSubTypesOf(Ability.class));

        for (Class<?> c : abilityClasses) {
            if (c == null || Modifier.isAbstract(c.getModifiers()))
                continue;
            Constructor<?> constructor;
            Object o;
            try {
                if (Arrays.asList(c.getInterfaces()).contains(ConfigurableAbility.class)) {
                    constructor = c.getDeclaredConstructor(String.class, String.class, PluginConfig.class);
                    o = constructor.newInstance(c.getSimpleName(), "", abilityConfig);
                }
                else {
                    constructor = c.getDeclaredConstructor(String.class, String.class);
                    o = constructor.newInstance(c.getSimpleName(), "");
                }
            } catch (SecurityException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                logr.error("Could not find a valid constructor for class %s", c.getSimpleName());
                logr.error(e.toString());
                continue;
            }
            if (o instanceof Ability) {
                Ability ability = (Ability) o;
                logr.debug("Adding base ability %s", ability.getAbilityName());
                baseAbilities.add(ability);
            }
        }
    }
}
