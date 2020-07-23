package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class BadEventsListener implements Listener {

    @EventHandler
    public void disableBed(PlayerBedEnterEvent e) {
        if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void disableVehicle(VehicleEnterEvent e) {
        if (e.getEntered().getType() == EntityType.PLAYER) {
            if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void disableFire(BlockBurnEvent e) {
        if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void disableFireSpread(BlockIgniteEvent e) {
        if (e.getCause() == BlockIgniteEvent.IgniteCause.LAVA || e.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void disableIce(BlockFormEvent e) {
        if (e.getBlock().getType() == Material.WATER || e.getBlock().getType() == Material.STATIONARY_WATER) {
            if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void disableIceSwitchWater(BlockFadeEvent e) {
        if (e.getBlock().getType() == Material.ICE || e.getBlock().getType() == Material.SNOW || e.getBlock().getType() == Material.SNOW_BLOCK) {
            if (EssentialsPlugin.config.getBoolean("disable.bad-events") == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void disableSand(BlockPhysicsEvent e) {
        if (e.getBlock().getType().equals(Material.SAND) || e.getBlock().getType().equals(Material.ANVIL) || e.getBlock().getType().equals(Material.GRAVEL)) {
            if (EssentialsPlugin.config.getBoolean("disable.falling-blocks") == true) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void disableFoodChange(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (p.hasMetadata("god")) {
            e.setCancelled(true);
        } else {
            if (EssentialsPlugin.config.getBoolean("disable.food-event") == true) {
                e.setCancelled(true);
            }
        }
    }


}
