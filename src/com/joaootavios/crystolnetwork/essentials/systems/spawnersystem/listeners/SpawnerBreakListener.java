package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners;

import com.joaootavios.crystolnetwork.essentials.systems.StackMobs;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.SpawnerUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import rcore.specificutils.PlayerUtil;
import rcore.util.MakeItem;
import rcore.util.Sound;

public class SpawnerBreakListener implements Listener {

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.MOB_SPAWNER) {
            final Player p = e.getPlayer();
            final ItemStack item = p.getItemInHand();

            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (!e.isCancelled() & (item.hasItemMeta()) && (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) && (item.getItemMeta().getEnchantLevel(Enchantment.SILK_TOUCH) >= 1)) {
                    CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();

                    if (e.isCancelled()) {
                        e.setCancelled(true);
                        return;
                    }
                    e.setExpToDrop(0);
                    e.getBlock().getDrops().clear();

                    final EntityType type = spawner.getSpawnedType();
                    final String spawnerName = StackMobs.getNameForType(type);

                    if (e.getPlayer().getInventory().firstEmpty() == -1) {
                        PlayerUtil.sendActionBar(p, "<c>Você deve conter pelo menos 1 slot vazio em seu inventário.\"");
                        PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
                        e.setCancelled(true);
                    } else {
                        e.getPlayer().getInventory().addItem(new MakeItem(Material.MOB_SPAWNER).setName("<e>Gerador de " + spawnerName).addLoreList("<7>Tipo de spawner: " + type.name()).setAmout(1).build());
                    }
                }
            } else {
                e.setCancelled(true);
                PlayerUtil.sendActionBar(p, "<c>Você deve estar no modo 'sobrevivência' para colocar um spawner.");
                PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }
    }
}
