package me.Jonnyfant.BukkitAzaleaSheer;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.material.Dispenser;

public class BlockBreakListener implements Listener {
    private final Double CHANCE_UNBREAKING_1 = 0.5;
    private final Double CHANCE_UNBREAKING_2 = 0.66;
    private final Double CHANCE_UNBREAKING_3 = 0.75;
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        //https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/block/BlockBreakEvent.html
        if (p.getInventory().getItemInMainHand().getType().equals(Material.SHEARS)) {
            if (sheerBlock(event.getBlock())) {
                //handleDurability(p.getInventory().getItemInMainHand());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        Bukkit.broadcastMessage("Block: " + event.getBlock().getType() + " Stack " + event.getItem().toString());
        if (event.getItem().getType().equals(Material.SHEARS)) {
            //weird stuff that needs to be done to get the Block the Dispenser is facing
            Dispenser d = (Dispenser) event.getBlock().getState().getData();
            Location l = event.getBlock().getLocation();
            double oldx = l.getX();
            double oldy = l.getY();
            double oldz = l.getZ();
            double newx = oldx + d.getFacing().getModX();
            double newy = oldy + d.getFacing().getModY();
            newy += 0.5;
            double newz = oldz + d.getFacing().getModZ();
            Bukkit.broadcastMessage("x " + oldx + ", " + newx);
            Bukkit.broadcastMessage("y " + oldy + ", " + newy);
            Bukkit.broadcastMessage("z " + oldz + ", " + newz);

            l.setX(l.getX() + d.getFacing().getModX());
            l.setX(l.getY() + d.getFacing().getModY());
            l.setX(l.getZ() + d.getFacing().getModZ());
            Block sheeredBlock = l.getBlock();
            Bukkit.broadcastMessage("Harvesting " + sheeredBlock.getType());
            sheeredBlock.setType(Material.GOLD_BLOCK);
            Bukkit.broadcastMessage("Position: " + l.toString());

            if (sheerBlock(sheeredBlock)) {
                handleDurability(event.getItem());
                event.setCancelled(true);
            }
        }
    }

    public boolean sheerBlock(Block b) {
        if (b.getType().equals(Material.FLOWERING_AZALEA)) {
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.DEAD_BUSH, 1));
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.FLOWERING_AZALEA_LEAVES, 1));
            return true;
        } else if (b.getType().equals(Material.AZALEA)) {
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.DEAD_BUSH, 1));
            b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.AZALEA_LEAVES, 1));
            return true;
        }
        return false;
    }

    public ItemStack handleDurability(ItemStack is) {
        Damageable meta = (Damageable) is.getItemMeta();
        Random random = new Random();
        if (is.containsEnchantment(Enchantment.DURABILITY)) {
            switch(is.getEnchantmentLevel(Enchantment.DURABILITY)) {
                case 1:
                    if(random.nextDouble()>CHANCE_UNBREAKING_1)
                        meta.setDamage(meta.getDamage() + 1);
                    break;
                case 2:
                    if(random.nextDouble()>CHANCE_UNBREAKING_2)
                        meta.setDamage(meta.getDamage() + 1);
                    break;
                case 3:
                    if(random.nextDouble()>CHANCE_UNBREAKING_3)
                        meta.setDamage(meta.getDamage() + 1);
                    break;
            }
        } else {
            meta.setDamage(meta.getDamage() - 1);
        }

        is.setItemMeta(meta);
        return is;
    }
}
