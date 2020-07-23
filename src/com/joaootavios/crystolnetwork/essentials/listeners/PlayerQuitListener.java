package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    final boolean disablequitmsg = EssentialsPlugin.config.getBoolean("disable.quit-message") == true;

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (disablequitmsg)
            e.setQuitMessage(null);
    }

}
