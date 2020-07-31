package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Tablist;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rcore.specificutils.PlayerUtil;
import rcore.util.ConfigManager;
import rcore.util.RU;
import rcore.util.TXT;

public class PlayerJoinListener implements Listener {

    final ConfigManager config = EssentialsPlugin.config;
    final boolean compatible = RU.serverVersion.equals(".v1_8_R3.") || RU.serverVersion.equals("v1_8_R3");
    final boolean titleactive = config.getBoolean("title.enable");
    final boolean tablistenable = config.getBoolean("tablist.enable");
    final boolean disablejoinmsg = config.getBoolean("disable.join-message");
    final boolean clearmessages = config.getBoolean("welcome-message.clear-chat");
    final boolean welcome = config.getBoolean("welcome-message.enable");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (disablejoinmsg)
        e.setJoinMessage(null);

        if (compatible && tablistenable)
        Tablist.sendTablist(e.getPlayer(), config.getStringList("tablist.header"), config.getStringList("tablist.footer"));
        PlayerUtil.sendTablist(e.getPlayer(), config.getStringList("tablist.header"), config.getStringList("tablist.footer"));

        if (titleactive)
            PlayerUtil.sendTitle(e.getPlayer(), config.getString("title.title"), config.getString("title.subtitle"), config.getInt("title.fadein"), config.getInt("title.stay"), config.getInt("title.fadeout"));

        if (clearmessages)
            e.getPlayer().sendMessage(StringUtils.repeat("", 100));

        if (welcome)
            e.getPlayer().sendMessage(TXT.parse(String.join("\n", config.getStringList("welcome-message.message"))));


    }

}
