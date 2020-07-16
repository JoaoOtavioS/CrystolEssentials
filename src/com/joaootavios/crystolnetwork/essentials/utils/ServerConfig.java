package com.joaootavios.crystolnetwork.essentials.utils;

import org.bukkit.plugin.Plugin;

import java.io.File;

public class ServerConfig {

    private final File file;
    private final EssentialsConfig essentialsConfig;

    public ServerConfig(final Plugin plugin) {
        file = new File(plugin.getDataFolder(), "config.yml");
        essentialsConfig = new EssentialsConfig(file.toPath());
    }

    public EssentialsConfig getEssentialsConfig() {
        return essentialsConfig;
    }

    public File getFile() {
        return file;
    }

}
