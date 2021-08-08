package me.cheracc.abilityitems.commands;

import me.cheracc.abilityitems.managers.AbilityManager;
import me.cheracc.abilityitems.managers.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SetAbility implements CommandExecutor {
    AbilityManager abilityManager;
    GuiManager guiManager;

    public SetAbility(AbilityManager abilityManager, GuiManager guiManager) {
        this.abilityManager = abilityManager;
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ItemStack item = p.getInventory().getItemInMainHand();

            if (item.getItemMeta() != null) {
                guiManager.sendChooseAbilityGui(p, ability -> abilityManager.saveAbilityToItem(ability, item));
            }
        }
        return true;
    }
}
