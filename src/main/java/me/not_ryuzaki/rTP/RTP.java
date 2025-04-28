package me.not_ryuzaki.rTP;

import org.bukkit.plugin.java.JavaPlugin;

public final class RTP extends JavaPlugin {
    private static RTP instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("rtp").setExecutor(new RTPCommand());
    }

    public static RTP getInstance(){
        return instance;
    }
}
