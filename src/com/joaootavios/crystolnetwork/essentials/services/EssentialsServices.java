package com.joaootavios.crystolnetwork.essentials.services;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.ServerConfig;
import com.walkgs.crystolnetwork.offices.utils.CachedCycle;
import org.bukkit.plugin.Plugin;

public class EssentialsServices {

    private final static CachedCycle.ICycle<EssentialsServices> cycle = new CachedCycle(EssentialsPlugin.getPlugin(EssentialsPlugin.class)).getOrCreate("EssentialsServices");
    private final Plugin plugin;
    private final ServerConfig serverConfig;

    protected EssentialsServices() {
        plugin = EssentialsPlugin.getPlugin(EssentialsPlugin.class);
        serverConfig = new ServerConfig(plugin);
    }

    public static CachedCycle.ICycle<EssentialsServices> getCycle() {
        return cycle;
    }

    public static EssentialsServices getInstance() {
        return cycle.getOrComputer(EssentialsServices::new);
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
