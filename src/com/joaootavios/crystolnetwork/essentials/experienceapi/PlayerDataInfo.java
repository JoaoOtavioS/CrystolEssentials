package com.joaootavios.crystolnetwork.essentials.experienceapi;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.OfflinePlayer;
import rcore.util.GsonManager;

import java.math.BigDecimal;
import java.util.*;

public class PlayerDataInfo {

    public DataInfo select(OfflinePlayer player) {
        return select(player.getUniqueId());
    }

    public DataInfo select(UUID uuid) {
        return new DataInfo(uuid);
    }

    public class DataInfo {

        GsonManager gson;
        private final String decodeChar = "@-#$&&";
        private final UUID uuid;

        public DataInfo(UUID uuid) {
            this.uuid = uuid;
            gson = new GsonManager(EssentialsPlugin.getPlugin(EssentialsPlugin.class).getDataFolder() + "/DataInfos", uuid + "-datainfo").prepareGson();
        }

        public DataInfo set(String path, Object... values) {
            String datas = "";
            Iterator<Object> it = Arrays.asList(values).iterator();
            while (it.hasNext()) {
                datas += "" + it.next();
                if (it.hasNext())
                    datas += decodeChar;
            }
            if (gson.contains(path))
                gson.remove(path);
            gson.put(path, datas);
            return this;
        }

        public DataInfo set(String path, int index, Object value) {
            String data = "";
            List<DataProperties> dp = get(path);
            if (dp.isEmpty()) {
                Iterator<DataProperties> it = dp.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (index == i)
                        data += "" + value;
                    else
                        data += it.next().asString();
                    if (it.hasNext())
                        data += decodeChar;
                    i++;
                }
            } else
                data = "" + value;
            return set(path, data);
        }

        public boolean contains(String path) {
            return gson.contains(path);
        }

        public DataInfo remove(String path) {
            if (contains(path))
                gson.remove(path);
            return this;
        }

        public void save() {
            gson.save();
        }

        public List<DataProperties> get(String path) {
            String data = gson.get(path).asString();
            List<DataProperties> dp = new ArrayList<>();
            switch ("" + data.contains(decodeChar)) {
                case "true": {
                    String[] values = data.split(decodeChar);
                    if (values.length > 0) {
                        Iterator<String> it = Arrays.asList(values).iterator();
                        while (it.hasNext()) {
                            dp.add(new DataProperties(it.next()));
                        }
                    }
                }
                case "false": {
                    dp.add(new DataProperties(data));
                }
            }
            return dp;
        }

        class DataProperties {

            private final String data;

            public DataProperties(String data) {
                this.data = data;
            }

            public String get() {
                return data;
            }

            public String asString() {
                return data;
            }

            public Boolean asBoolean() {
                return Boolean.valueOf(data);
            }

            public Integer asInt() {
                return Integer.valueOf(data);
            }

            public Long asLong() {
                return Long.valueOf(data);
            }

            public Double asDouble() {
                return Double.valueOf(data);
            }

            public BigDecimal asBigDecimal() {
                return BigDecimal.valueOf(asDouble());
            }

        }
    }
}


