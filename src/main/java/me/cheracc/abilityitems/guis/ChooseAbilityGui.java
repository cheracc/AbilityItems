package me.cheracc.abilityitems.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.cheracc.abilityitems.types.abilities.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChooseAbilityGui extends Gui {
    Consumer<Ability> callback;

    public ChooseAbilityGui(List<Ability> abilityList, Consumer<Ability> callback) {
        super(1, "Choose an Ability", InteractionModifier.VALUES);
        this.callback = callback;
        setOutsideClickAction(e -> e.getWhoClicked().closeInventory());

        fillGui(abilityList);
    }

    private void fillGui(List<Ability> abilityList) {
        for (Ability a : abilityList) {
            addItem(abilityIcon(a));
        }
    }

    private GuiItem abilityIcon(Ability ability) {
        ItemBuilder icon = ItemBuilder.from(Material.BELL);
        icon.name(Component.text(ability.getAbilityName()).color(NamedTextColor.YELLOW));
        List<Component> lore = new ArrayList<>();

        icon.lore(lore);
        icon.flags(ItemFlag.values());
        return icon.asGuiItem(e -> {
            e.getWhoClicked().closeInventory();
            callback.accept(ability);
        });
    }
}
