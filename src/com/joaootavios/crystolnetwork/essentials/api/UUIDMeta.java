package com.joaootavios.crystolnetwork.essentials.api;


import java.util.HashMap;
import java.util.UUID;

public class UUIDMeta {

    private static final HashMap<String, HashMap<String, Object>> uuid_meta = new HashMap<>();

    public static void setMetadata(UUID uuid, String key, Object value) {
        HashMap<String, Object> meta_value = getMetadataList(uuid);
        if (containsData(uuid, key)) {
            meta_value.replace(key, value);
        } else {
            meta_value.put(key, value);
        }
        uuid_meta.replace(uuid.toString(), meta_value);
    }

    public static boolean containsData(UUID uuid, String key) {
        return getMetadataList(uuid).containsKey(key);
    }

    public static void removeMetadata(UUID uuid, String key) {
        if (containsData(uuid, key)) {
            HashMap<String, Object> meta_value = getMetadataList(uuid);
            meta_value.remove(key);
            uuid_meta.replace(uuid.toString(), meta_value);
        }
    }

    public static HashMap<String, Object> getMetadataList(UUID uuid) {
        HashMap<String, Object> meta_value = uuid_meta.get(uuid.toString());
        if (meta_value == null) {
            meta_value = new HashMap<>();
            uuid_meta.put(uuid.toString(), meta_value);
        }
        return meta_value;
    }

    public static Object getMetadata(UUID uuid, String key) {
        HashMap<String, Object> meta_value = getMetadataList(uuid);
        if (meta_value.containsKey(key)) {
            return meta_value.get(key);
        }
        return null;
    }

    public static int getAmountData(UUID uuid) {
        try {
            return uuid_meta.get(uuid.toString()).size();
        } catch (Exception e) {
            return 0;
        }
    }

}
