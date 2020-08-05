package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerInteractListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        final Player pl = e.getPlayer();
        if (isSpawner(e.getClickedBlock().getType()) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack mainHand = pl.getItemInHand();
            if (isEgg(mainHand) && !mainHand.equals(Material.AIR)) {
                e.setCancelled(true);
            }
        }
    }

    private final boolean isEgg(ItemStack item) {
        final String name = item == null ? "" : item.getType().toString();
        return name.contains("MONSTER_EGG") || name.endsWith("SPAWN_EGG");
    }

    private final boolean isSpawner(Material mat) {
        final String name = mat == null ? "" : mat.toString();
        return name.equals("MOB_SPAWNER") || name.equals("SPAWNER");
    }

}
