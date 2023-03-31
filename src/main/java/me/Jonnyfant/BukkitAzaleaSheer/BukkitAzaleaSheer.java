package me.Jonnyfant.BukkitAzaleaSheer;

import org.bukkit.plugin.java.JavaPlugin;
public class BukkitAzaleaSheer extends JavaPlugin { @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }
}
