package com.joaootavios.crystolnetwork.essentials.systems.spawnersystem;

import com.joaootavios.crystolnetwork.essentials.systems.StackMobs;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsAPI;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rcore.command.RCommand;
import rcore.util.MakeItem;
import rcore.util.Sound;

import java.util.List;

public class SpawnerCommand extends RCommand {

    { setPermission("crystolnetwork.gerente"); }

    @Override public String getCommand() { return "givespawner"; }
    @Override public void perform() {
        int amount;
        final EntityType type;

        if (!isArgsLength(3)) {
            sendMessage("<c>Utilize /givespawner <jogador> <tipo> <quantidade>.");
            playSound(Sound.ENTITY_VILLAGER_YES, 1.0, 1.0);
            return;
        }

        Player toGive = getPlayer(argAt(0));
        if (toGive == null) {
            sendMessage("<7>'%s' <c>não está online.", argAt(0));
            playSound(Sound.ENTITY_VILLAGER_NO, 1.0, 1.0);
            return;
        }
        try { type = EntityType.valueOf(argAt(1).toUpperCase()); }

        catch (Exception e) {
            sendMessage("<7>'%s' <c>não é um tipo de spawner válido.", argAt(1));
            playSound(Sound.ENTITY_VILLAGER_NO, 1.0, 1.0);
            return;
        }
        if (!isValidInt(argAt(2))) {
            sendMessage("<7>'%s' <c>não é um número válido.", argAt(2));
            playSound(Sound.ENTITY_VILLAGER_NO, 1.0, 1.0);
            return;
        }

        amount = Integer.parseInt(argAt(2));

        String spawnerName = StackMobs.getNameForType(type);
        ItemStack spawner = new MakeItem(Material.MOB_SPAWNER).setName("<e>Gerador de " + spawnerName).addLoreList("<7>Tipo de spawner: " + type.name()).build();

        for (int i = 0; i < amount; i++) {
            if (toGive.getInventory().firstEmpty() == -1) {
                toGive.getLocation().getWorld().dropItemNaturally(toGive.getLocation(), spawner);
            } else {
                toGive.getInventory().addItem(spawner);
            }
        }

        toGive.updateInventory();
        playSound(toGive, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0, 1.0);
        EssentialsAPI.sendStaffMessage("<c>[Logs] <8>" + toGive.getName() + " <f>enviou <4>" + amount + " <f>sp de " + spawnerName + " <f>para <8>" + toGive.getName() + "<f>.");
    }
    @Override public List<String> tabComplete() { return null; }
}
