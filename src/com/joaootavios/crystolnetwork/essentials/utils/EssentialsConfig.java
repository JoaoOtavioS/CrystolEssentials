package com.joaootavios.crystolnetwork.essentials.utils;

import com.google.gson.Gson;
import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import org.bukkit.Location;
import rcore.util.ConfigManager;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EssentialsConfig extends BukkitYamlConfiguration {

    private final Gson gson = new Gson();

    private final Map<String, String> strings = new LinkedHashMap<>();
    private final Map<String, Boolean> booleans = new LinkedHashMap<>();
    private final Map<String, Integer> ints = new LinkedHashMap<>();
    private final Map<String, String> locations = new LinkedHashMap<>();
    private ConfigManager stringlist;

    public EssentialsConfig(Path path) {
        super(path);
        stringlist = new ConfigManager(EssentialsPlugin.getPlugin(EssentialsPlugin.class), "stringlist.yml");
    }

    // String List
    public List<String> getStringList(String stringlist_name) {
        return stringlist.getStringList(stringlist_name);
    }

    public boolean setStringList(String path, Object key) {
        return stringlist.set(path, key);
    }

    // Strings
    public void setString(String field, String name) {
        strings.put(field, name);
    }

    public String getString(String field) {
        if (!strings.containsValue(field)) return null;
        return strings.get(field);
    }

    public boolean contaisString(String field) {
        if (!strings.containsValue(field)) return false;
        return true;
    }

    // Int
    public void setInt(String field, int ticks) {
        ints.put(field, ticks);
    }

    public Integer getInt(String field) {
        if (!ints.containsKey(field)) return null;
        return ints.get(field);
    }

    public Serializable existInt(String field) {
        if (ints.containsKey(field)) return true;
        return false;
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
