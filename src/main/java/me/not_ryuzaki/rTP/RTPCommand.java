package me.not_ryuzaki.rTP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            ConfirmGUI confirmGUI = new ConfirmGUI();
            confirmGUI.openRTPGUI(player);
            return true;
        }
        return false;
    }
}