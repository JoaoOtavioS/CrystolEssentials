package com.joaootavios.crystolnetwork.essentials.utils;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import rcore.util.TXT;
import java.util.List;

public class CrystolEssentialsUtils {

    public static void sendStaffMessage(List<String> messages) {
        Bukkit.getScheduler().runTaskAsynchronously(EssentialsPlugin.getPlugin(EssentialsPlugin.class), () -> {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("crystolnetwork.staff")) {
                    p.sendMessage(TXT.parse(String.join("\n", messages)));
                }
            });
        });
    }

}
