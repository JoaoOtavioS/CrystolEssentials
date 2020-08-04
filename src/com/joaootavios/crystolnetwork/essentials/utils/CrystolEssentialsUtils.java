package com.joaootavios.crystolnetwork.essentials.utils;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import rcore.util.TXT;
import java.util.Arrays;

public class CrystolEssentialsUtils {

    public static void sendStaffMessage(String... messages) {
        Bukkit.getScheduler().runTaskAsynchronously(EssentialsPlugin.getPlugin(EssentialsPlugin.class), () -> {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("crystolnetwork.staff")) {
                    p.sendMessage(TXT.parse(String.join("\n", Arrays.asList(messages))));
                }
            });
        });
    }



}
