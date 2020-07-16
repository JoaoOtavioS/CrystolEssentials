package com.joaootavios.crystolnetwork.essentials.listeners;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onRain(WeatherChangeEvent e) {
        if (EssentialsPlugin.config.getBoolean("disable-weather") == true) {
            e.setCancelled(e.toWeatherState());
        }
    }
}
