package com.joaootavios.crystolnetwork.essentials.api;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.api.enums.StaffType;
import org.bukkit.Bukkit;
import rcore.util.TXT;
import java.util.Arrays;

public class EssentialsAPI {

    final private static EssentialsPlugin plugin = EssentialsPlugin.getPlugin(EssentialsPlugin.class);

    public static void sendStaffMessage(StaffType cargo, String... messages) {
        String permission = "crystolnetwork." + cargo;
        if (cargo.equals(StaffType.all)) permission = "crystolnetwork.staff";

        final String fpermission = permission;
        Bukkit.getOnlinePlayers().forEach((p) -> {
            if (p.hasPermission(fpermission)) {
                p.sendMessage(TXT.parse(String.join("\n", Arrays.asList(messages))));
            }
        });
    }

    public static void sendVipMessage(String... messages) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("crystolnetwork.vip")) {
                    p.sendMessage(TXT.parse(String.join("\n", Arrays.asList(messages))));
                }
            });
        });
    }

}
