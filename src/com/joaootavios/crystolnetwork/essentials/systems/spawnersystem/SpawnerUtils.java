package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import rcore.util.TXT;

import java.util.Iterator;

public class SpawnerUtils {

    public static boolean isSpawner(ItemStack item) {
        if (item.getType() != Material.MOB_SPAWNER) {
            return false;
        }
        try {
            Iterator<String> lore = item.getItemMeta().getLore().iterator();
            String tipo = TXT.parse("<7>Tipo de spawner: ");
            while (lore.hasNext()) {
                String next = lore.next();
                if (next.startsWith(tipo)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public static EntityType getSpawnerType(ItemStack item) {
        try {
            Iterator<String> lore = item.getItemMeta().getLore().iterator();
            String tipo = TXT.parse("<7>Tipo de spawner: ");
            while (lore.hasNext()) {
                String next = lore.next();
                if (next.startsWith(tipo)) {
                    return EntityType.valueOf(next.replace(tipo, ""));
                }
            }
            return EntityType.PIG;
        } catch (Exception e) {
        }
        return EntityType.PIG;
    }
}
