package me.cheracc.abilityitems.commands;

import me.cheracc.abilityitems.managers.AbilityManager;
import me.cheracc.abilityitems.managers.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AbilitiesCommand implements CommandExecutor {
    private final AbilityManager abilityManager;
    private final GuiManager guiManager;

    public AbilitiesCommand(AbilityManager abilityManager, GuiManager guiManager) {
        this.abilityManager = abilityManager;
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            guiManager.sendAbilitiesAdminGui((Player) sender);
        }
        return true;
    }
}
