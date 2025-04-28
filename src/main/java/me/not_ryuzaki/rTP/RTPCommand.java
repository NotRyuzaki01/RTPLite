package me.not_ryuzaki.rTP;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player player) {

            new BukkitRunnable() {
                int countdown = 5;
                @Override
                public void run() {
                    if (countdown > 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eTeleporting in §c" + countdown + "§e seconds..."));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        countdown--;
                    } else {
                        Location randomLocation = TeleportUtils.generateLocation(player);
                        player.teleport(randomLocation);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aTeleported!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                        cancel();
                    }
                }
            }.runTaskTimer(RTP.getInstance(), 0L, 20L); // 20L = 1 second
        }
        return true;
    }
}