package com.joaootavios.crystolnetwork.essentials;

import com.joaootavios.crystolnetwork.essentials.commands.warps.Shop;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Spawn;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Vip;
import com.joaootavios.crystolnetwork.essentials.experienceapi.ExperienceAPI;
import com.joaootavios.crystolnetwork.essentials.listeners.BadEventsListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EnderPearlListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EntityChangeBlockListener;
import com.joaootavios.crystolnetwork.essentials.listeners.WeatherChangeListener;
import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitScheduler;
import rcore.plugin.RPlugin;

public class EssentialsPlugin extends RPlugin {

    public static EssentialsConfig config;
    private Server server;
    private BukkitScheduler bukkitScheduler;

    public EssentialsPlugin() {
        super("CrystolEssentials", "JoaoOtavioS & WalkGS");
    }

    @Override
    public void onPreStart() {
        server = getServer();
        bukkitScheduler = server.getScheduler();
        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();
    }

    @Override
    public void onStart() {

        // Registrando comandos e eventos.

        verifyDefaultConfig();
        registerCommands();
        registerListeners();
        ExperienceAPI.loadAll();

    }

    @Override
    public void onStop() {
        bukkitScheduler.cancelAllTasks();
    }

    private void verifyDefaultConfig() {
        if (!config.existBoolean("warp-vip")) {
            config.setBoolean("warp-spawn", true);
            config.setBoolean("warp-shop", true);
            config.setBoolean("warp-vip", false);
            config.setBoolean("disable-weather", true);
            config.setBoolean("disable-falling-blocks", true);
            config.setBoolean("disable-food-event", true);
            config.setBoolean("disable-bad-events", true);
            config.setBoolean("scoreboard-active", true);
        }
    }

    private void registerCommands() {
        if (config.getBoolean("warp-spawn") == true) registerCommand(new Spawn());
        if (config.getBoolean("warp-shop") == true) registerCommand(new Shop());
        if (config.getBoolean("warp-vip") == true) registerCommand(new Vip());
    }

    private void registerListeners() {
        setListeners(new BadEventsListener(), new EnderPearlListener(), new EntityChangeBlockListener(), new WeatherChangeListener());
    }

    public void startScoreBoard() {

    }
}
