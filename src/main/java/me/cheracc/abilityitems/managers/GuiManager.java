package me.cheracc.abilityitems.managers;

import dev.triumphteam.gui.guis.Gui;
import me.cheracc.abilityitems.guis.AbilitiesAdminGui;
import me.cheracc.abilityitems.guis.ChooseAbilityGui;
import me.cheracc.abilityitems.tools.Translator;
import me.cheracc.abilityitems.types.abilities.Ability;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class GuiManager {
    private final AbilityManager abilityManager;
    private final Translator trans;

    public GuiManager(AbilityManager abilityManager, Translator trans) {
        this.abilityManager = abilityManager;
        this.trans = trans;
    }

    public void sendChooseAbilityGui(Player player, Consumer<Ability> callback) {
        player.closeInventory();
        Gui gui = new ChooseAbilityGui(abilityManager.getLoadedAbilities(), callback);
        gui.open(player);
    }

    public void sendAbilitiesAdminGui(Player player) {
        player.closeInventory();
        Gui gui = new AbilitiesAdminGui(abilityManager, trans);
        gui.open(player);
    }
}
