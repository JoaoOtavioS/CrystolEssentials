package com.joaootavios.crystolnetwork.essentials.utils;

import com.google.gson.Gson;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import org.bukkit.Location;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class EssentialsConfig extends BukkitYamlConfiguration {

    private final Gson gson = new Gson();
    private final Map<String, Boolean> booleans = new LinkedHashMap<>();
    private final Map<String, String> locations = new LinkedHashMap<>();

    public EssentialsConfig(Path path) {
        super(path);
    }

    // Booleans
    public void setBoolean(String name, Boolean value) {
        unSetBoolean(name);
        booleans.put(name, value);
    }

    public boolean unSetBoolean(String name) {
        if (!booleans.containsKey(name)) return false;
        booleans.remove(name);
        return true;
    }

    public boolean getBoolean(String name) {
        if (!booleans.containsKey(name)) return false;
        return booleans.get(name);
    }

    public boolean existBoolean(String name) {
        return booleans.containsKey(name);
    }

    // Locations
    public void setLocation(String locationName, Location location) {
        removeLocation(locationName);
        locations.put(locationName, gson.toJson(location));
    }

    public boolean removeLocation(String locationName) {
        if (!hasLocation(locationName)) return false;
        locations.remove(locationName);
        return true;
    }

    public Location getLocation(String locationName) {
        if (!hasLocation(locationName)) return null;
        return gson.fromJson(locations.get(locationName), Location.class);
    }

    public boolean hasLocation(String locationName) {
        return locations.containsKey(locationName);
    }
}
