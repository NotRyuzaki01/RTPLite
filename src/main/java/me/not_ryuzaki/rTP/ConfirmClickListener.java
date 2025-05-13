package me.not_ryuzaki.rTP;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfirmClickListener implements Listener {

    private final JavaPlugin plugin;

    public ConfirmClickListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getView().getTitle().equals("Random Teleport")) {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;

            String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

            if (name.equalsIgnoreCase("Cancel")) {
                player.closeInventory();
                player.sendMessage("§cTeleport cancelled.");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cTeleport cancelled."));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }

            if (name.equalsIgnoreCase("Confirm")) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                player.closeInventory();
                startRtpCountdown(player);
            }
        }
    }

    private void startRtpCountdown(Player player) {
        final Location[] safeLocation = new Location[1]; // container for async access
        Location playerLoc = player.getLocation().clone();
        // Start generating location in background
        new BukkitRunnable() {
            @Override
            public void run() {
                safeLocation[0] = TeleportUtils.findSafeLocation(player.getWorld());
            }
        }.runTaskAsynchronously(RTP.getInstance());

        // Start countdown
        new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (player.getLocation().distanceSquared(playerLoc) > 0.1) {
                    player.sendMessage("§cTeleport cancelled because you moved!");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cTeleport cancelled because you moved!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    cancel();
                    return;
                }
                if (countdown > 0) {
                    TextComponent message = new TextComponent("Teleporting in ");
                    message.setColor(net.md_5.bungee.api.ChatColor.WHITE);

                    TextComponent seconds = new TextComponent(String.valueOf(countdown));
                    seconds.setColor(net.md_5.bungee.api.ChatColor.of("#0094FF"));

                    TextComponent suffix = new TextComponent("s");
                    suffix.setColor(net.md_5.bungee.api.ChatColor.of("#0094FF"));

                    message.addExtra(seconds);
                    message.addExtra(suffix);

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    countdown--;
                } else {
                    cancel();
                    if (safeLocation[0] != null) {
                        player.teleport(safeLocation[0]);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aTeleported!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                    } else {
                        player.sendMessage("§cStill finding a safe location, please try again.");
                    }
                }
            }
        }.runTaskTimer(RTP.getInstance(), 0L, 20L);
    }
}
