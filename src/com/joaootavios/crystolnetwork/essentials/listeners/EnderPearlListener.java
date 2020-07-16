package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.utils.CooldownAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import rcore.specificutils.PlayerUtil;

public class EnderPearlListener implements Listener {

    private final CooldownAPI cooldownAPI = new CooldownAPI();

    @EventHandler
    public void enviarEnderPearl(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity().getShooter();
        if (e.getEntityType() != EntityType.ENDER_PEARL) {
            return;
        }
        if (cooldownAPI.getCooldownRemaining(p.getUniqueId(), "enderpearl") > 0) {
            PlayerUtil.sendActionBar(p, "Aguarde " + cooldownAPI.getCooldownRemaining(p.getUniqueId(), "enderpearl") + " para tacar uma enderpearl novamente.");
            e.setCancelled(true);
            return;
        }
        cooldownAPI.setCooldown(p.getUniqueId(), "enderpearl", 3L);
    }
}
