package me.JonathanHoffmann.AzaleaSheer;

import org.bukkit.plugin.java.JavaPlugin;

public class AzaleaSheer extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }
}
