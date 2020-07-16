package com.joaootavios.crystolnetwork.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import rcore.specificutils.PlayerUtil;

import java.sql.Timestamp;
import java.util.HashMap;

public class EnderPearlListener implements Listener {

    private HashMap<Player, Timestamp> cooldown = new HashMap<>();

    @EventHandler
    public void enviarEnderPearl(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity().getShooter();
        if (e.getEntityType() != EntityType.ENDER_PEARL) {
            return;
        }
        if (cooldown.containsKey(p) && cooldown.get(p).after(new Timestamp(System.currentTimeMillis()))) {
            e.setCancelled(true);
            PlayerUtil.sendActionBar();
            TXT.sendActionBar(p, TXT.parse("<c>Aguarde para poder lançar outra enderpearl."));
            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
            p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
            return;
        } else {
            cooldown.remove(p);
        }
        cooldown.put(p, new Timestamp(System.currentTimeMillis() + 1000 * (5)));
    }
}
