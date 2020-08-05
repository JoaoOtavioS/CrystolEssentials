package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners;

import com.joaootavios.crystolnetwork.essentials.systems.StackMobs;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import rcore.util.MakeItem;

import java.util.Random;

public class SpawnerExplosionListener implements Listener {

    private final Random random = new Random();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void explodindo(EntityExplodeEvent e) {

        for (Block block : e.blockList()) {
            if (block.getType() == Material.MOB_SPAWNER) {
                if (random.nextInt(5) <= 1) {

                    final CreatureSpawner spawner = (CreatureSpawner) block.getState();
                    final EntityType type = spawner.getSpawnedType();
                    final String spawnerName = StackMobs.getNameForType(type);
                    final ItemStack spawnerItem = new MakeItem(Material.MOB_SPAWNER).setName("<e>Gerador de " + spawnerName).addLoreList("<7>Tipo de spawner: " + type.name()).setAmount(1).build();

                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), spawnerItem);
                }
            }
        }
    }
}
