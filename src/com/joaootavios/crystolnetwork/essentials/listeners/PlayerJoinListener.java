package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Tablist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rcore.specificutils.PlayerUtil;
import rcore.util.RU;

import static org.bukkit.Bukkit.getServer;

public class PlayerJoinListener implements Listener {

    final boolean compatible = RU.serverVersion.equals(".v1_8_R3.") || RU.serverVersion.equals("v1_8_R3");
    final boolean titleactive = EssentialsPlugin.config.getBoolean("title-on-join") == true;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (compatible)
        Tablist.sendTablist(e.getPlayer(), EssentialsPlugin.config.getStringList("tablist-header"), EssentialsPlugin.config.getStringList("tablist-footer"));

        if (titleactive)
            PlayerUtil.sendTitle(e.getPlayer(), EssentialsPlugin.config.getString("title-on-join-title"), EssentialsPlugin.config.getString("title-on-join-subtitle"), EssentialsPlugin.config.getInt("title-on-join-fadein"), EssentialsPlugin.config.getInt("title-on-join-stay"), EssentialsPlugin.config.getInt("title-on-join-fadeout"));
    }

}
