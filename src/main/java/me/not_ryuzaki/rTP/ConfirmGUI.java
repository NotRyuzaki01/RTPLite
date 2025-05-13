package me.not_ryuzaki.rTP;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ConfirmGUI {
    public static void openRTPGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Random Teleport");

        Material blockMaterial = Material.GRASS_BLOCK;

        ItemStack block = new ItemStack(blockMaterial);
        ItemMeta blockMeta = block.getItemMeta();
        blockMeta.setDisplayName(ChatColor.GREEN + "Over World");
        block.setItemMeta(blockMeta);

        Material red = Material.RED_STAINED_GLASS_PANE;
        ItemStack redPane = new ItemStack(red);
        ItemMeta redPaneMeta = redPane.getItemMeta();
        redPaneMeta.setDisplayName(ChatColor.RED + "Cancel");
        redPaneMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Click to cancel"));
        redPane.setItemMeta(redPaneMeta);

        Material green = Material.LIME_STAINED_GLASS_PANE;
        ItemStack greenPane = new ItemStack(green);
        ItemMeta greenPaneMeta = greenPane.getItemMeta();
        greenPaneMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        greenPaneMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Click to confirm"));
        greenPane.setItemMeta(greenPaneMeta);

        gui.setItem(11, redPane);
        gui.setItem(13, block);
        gui.setItem(15, greenPane);

        player.openInventory(gui);
    }
}
