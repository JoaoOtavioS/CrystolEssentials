package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners;

import com.joaootavios.crystolnetwork.essentials.systems.StackMobs;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.SpawnerUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import rcore.specificutils.PlayerUtil;
import rcore.util.Sound;

public class SpawnerPlaceListener implements Listener {

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent e) {
        final Player p = e.getPlayer();

        if (e.getBlockPlaced().getType() == Material.MOB_SPAWNER) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (SpawnerUtils.isSpawner(e.getItemInHand())) {

                    final EntityType type = SpawnerUtils.getSpawnerType(e.getItemInHand());
                    final CreatureSpawner spawner = (CreatureSpawner) e.getBlockPlaced().getState();
                    spawner.setSpawnedType(type);

                    if (e.isCancelled()) {
                        e.setCancelled(true);
                        return;
                    }
                    spawner.setSpawnedType(spawner.getSpawnedType());
                }
            } else {
                e.setCancelled(true);
                PlayerUtil.sendActionBar(p, "<c>Você deve estar no modo 'sobrevivência' para colocar um spawner.");
                PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }
    }
}
