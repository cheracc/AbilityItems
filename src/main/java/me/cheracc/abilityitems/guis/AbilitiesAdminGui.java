package me.cheracc.abilityitems.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.cheracc.abilityitems.managers.AbilityManager;
import me.cheracc.abilityitems.tools.Translator;
import me.cheracc.abilityitems.types.abilities.Ability;
import me.cheracc.abilityitems.types.abilities.configuration.AbilityConfigurationOptions;
import me.cheracc.abilityitems.types.abilities.interfaces.ConfigurableAbility;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public class AbilitiesAdminGui extends Gui {
    AbilityManager abilityManager;
    Translator trans;

    public AbilitiesAdminGui(AbilityManager abilityManager, Translator trans) {
        super(1, "Showing All Abilities", InteractionModifier.VALUES);
        this.abilityManager = abilityManager;
        this.trans = trans;

        fillGui();
    }

    private void fillGui() {
        for (Ability ability: abilityManager.getBaseAbilities()) {
            if (ability instanceof ConfigurableAbility)
                addItem(baseAbilityIcon(ability));
            else
                addItem(abilityIcon(ability));
        }
        for (Ability ability: abilityManager.getLoadedAbilities()) {
            addItem(abilityIcon(ability));
        }
    }

    private GuiItem abilityIcon(Ability ability) {
        ItemBuilder icon = ItemBuilder.from(Material.NETHER_STAR);
        icon.name(trans.lateToComponent("&eAbility&f: %s", ability.getAbilityName()));
        icon.lore(Component.space());
        icon.lore(lore -> lore.add(trans.lateToComponent(ability.getAbilityDescription())));

        if (ability instanceof ConfigurableAbility) {
            icon.lore(lore -> lore.add(trans.lateToComponent("&bConfigured Options&f:")));

            AbilityConfigurationOptions options = ((ConfigurableAbility) ability).getConfigurationOptions();
            for (String opt : options.availableOptions()) {
                String value = options.getValue(opt).getValueAsString();
                String optionLine = "&f%s&7: %s";
                icon.lore(lore -> lore.add(trans.lateToComponent(optionLine, WordUtils.capitalize(opt), value)));
            }
        }
        return icon.asGuiItem();
    }

    private GuiItem baseAbilityIcon(Ability ability) {
        ItemBuilder icon = ItemBuilder.from(Material.ENCHANTED_GOLDEN_APPLE);
        icon.name(trans.lateToComponent("&eCreate New&f: %s", ability.getAbilityName()));
        icon.lore(Component.space());
        icon.lore(lore -> lore.add(trans.lateToComponent("&bConfigurable Options&f:")));

        AbilityConfigurationOptions options = ((ConfigurableAbility) ability).getConfigurationOptions();
        for (String opt : options.availableOptions()) {
            String value = options.getValue(opt).getValueAsString();
            String optionLine = "&f%s&7: %s";
            icon.lore(lore -> lore.add(trans.lateToComponent(optionLine, WordUtils.capitalize(opt), value)));
        }

        return icon.asGuiItem();
    }

}
