package com.joaootavios.crystolnetwork.essentials.utils;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import rcore.util.TXT;
import java.util.List;


public class CrystolEssentialsUtils {

    public static void sendStaffMessage(String s) {
        Bukkit.getScheduler().runTaskAsynchronously(EssentialsPlugin.getPlugin(EssentialsPlugin.class), () -> {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("crystolnetwork.staff")) {
                    p.sendMessage(TXT.parse(s));
                }
            });
        });
    }

    public static void sendSpacedStaffMessage(String s) {
        Bukkit.getScheduler().runTaskAsynchronously(EssentialsPlugin.getPlugin(EssentialsPlugin.class), () -> {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (p.hasPermission("crystolnetwork.staff")) {
                    p.sendMessage(" ");
                    p.sendMessage(TXT.parse(s));
                    p.sendMessage(" ");
                }
            });
        });
    }

}
