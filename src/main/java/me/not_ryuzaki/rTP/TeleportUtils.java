package me.not_ryuzaki.rTP;

import org.bukkit.*;
import org.bukkit.block.Block;

import java.util.Random;

public class TeleportUtils {
    private static final int RADIUS = 25000;
    private static final Random random = new Random();

    public static Location findSafeLocation(World world) {
        while (true) {
            int x = random.nextInt(RADIUS * 2) - RADIUS;
            int z = random.nextInt(RADIUS * 2) - RADIUS;
            int y = world.getHighestBlockYAt(x, z);

            Location baseLoc = new Location(world, x + 0.5, y, z + 0.5);
            Block ground = baseLoc.getBlock();
            Block above1 = ground.getRelative(0, 1, 0);
            Block above2 = ground.getRelative(0, 2, 0);

            Material groundType = ground.getType();
            Material above1Type = above1.getType();
            Material above2Type = above2.getType();

            if (isSafeGround(groundType) && isAir(above1Type) && isAir(above2Type)) {
                return baseLoc.add(0, 1, 0); // teleport one block above ground
            }
        }
    }

    private static boolean isAir(Material mat) {
        return mat == Material.AIR || mat == Material.CAVE_AIR;
    }

    private static boolean isSafeGround(Material mat) {
        if (!mat.isSolid()) return false;

        return switch (mat) {
            case WATER, LAVA,
                 MAGMA_BLOCK, FIRE, CAMPFIRE,
                 CACTUS, POWDER_SNOW,
                 SWEET_BERRY_BUSH, DRIPSTONE_BLOCK -> false;
            default -> true;
        };
    }
}
