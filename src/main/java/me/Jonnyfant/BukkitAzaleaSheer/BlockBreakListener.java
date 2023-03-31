package me.Jonnyfant.BukkitAzaleaSheer;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        //https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/block/BlockBreakEvent.html
        if (p.getInventory().getItemInMainHand().getType().equals(Material.SHEARS) && p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH) == false) {
            if (event.getBlock().getType().equals(Material.FLOWERING_AZALEA)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DEAD_BUSH, 1));
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FLOWERING_AZALEA_LEAVES, 1));
                event.setDropItems(false);
            } else if (event.getBlock().getType().equals(Material.AZALEA)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DEAD_BUSH, 1));
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.AZALEA_LEAVES, 1));
                event.setDropItems(false);
            }
        }
    }
}
