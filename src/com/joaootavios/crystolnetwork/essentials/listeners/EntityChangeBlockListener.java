package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {

    @EventHandler
    public static void updateSand(EntityChangeBlockEvent e) {
        if (e.getEntity().getType().equals(EntityType.FALLING_BLOCK)) {
            if (EssentialsPlugin.config.getBoolean("disable-falling-blocks") == true) {
                e.getBlock().getState().update();
                e.getEntity().remove();
                e.getBlock().getState().update();
                e.setCancelled(true);
                e.getBlock().getState().update();
            }
        }
    }
}
