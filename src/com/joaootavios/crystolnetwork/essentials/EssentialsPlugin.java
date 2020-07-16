package com.joaootavios.crystolnetwork.essentials;

import com.joaootavios.crystolnetwork.essentials.commands.warps.Shop;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Spawn;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Vip;
import com.joaootavios.crystolnetwork.essentials.experienceapi.ExperienceAPI;
import com.joaootavios.crystolnetwork.essentials.listeners.BadEventsListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EnderPearlListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EntityChangeBlockListener;
import com.joaootavios.crystolnetwork.essentials.listeners.WeatherChangeListener;
import me.joao.guerra.command.GuerraCommand;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import rcore.plugin.RPlugin;
import rcore.score.Assemble;
import rcore.score.AssembleAdapter;
import rcore.specificutils.ListUtil;
import rcore.util.ConfigManager;
import rcore.util.RU;

import java.util.List;

public class EssentialsPlugin extends RPlugin {

    private Server server;
    private BukkitScheduler bukkitScheduler;
    public static ConfigManager config;
    private ConfigManager scoreboard;
    public EssentialsPlugin() {
        super("CrystolEssentials", "JoaoOtavioS & WalkGS");
    }

    @Override
    public void onPreStart() {
        server = getServer();
        bukkitScheduler = server.getScheduler();
    }

    @Override
    public void onStart() {

        // Registrando comandos e eventos.
        registerDefaultConfig();
        registerScoreBoard();
        ExperienceAPI.loadAll();

        registerCommands();
        registerListeners();

    }

    @Override
    public void onStop() {
        bukkitScheduler.cancelAllTasks();
    }

    private void registerCommands() {
        if (config.getBoolean("warp-spawn") == true) registerCommand(new Spawn());
        if (config.getBoolean("warp-shop") == true) registerCommand(new Shop());
        if (config.getBoolean("warp-vip") == true) registerCommand(new Vip());
    }

    private void registerListeners() {
        setListeners(new BadEventsListener(), new EnderPearlListener(), new EntityChangeBlockListener(), new WeatherChangeListener());
    }

    private void registerDefaultConfig() {
        config = new ConfigManager(this, "config.yml");
        if (!config.contains("warp-vip")) {
            config.set("warp-spawn", true);
            config.set("warp-shop", true);
            config.set("warp-vip", false);
            config.set("disable-weather", true);
            config.set("disable-falling-blocks", true);
            config.set("disable-food-event", true);
            config.set("disable-bad-events", true);
            config.set("disable-enderpearl-cooldown", false);
            config.set("enderpearl-cooldown", 5L);

        }
        if (config.contains("spawn")) config.setLocation("spawn", config.getLocation("spawn"));
        if (config.contains("shop")) config.setLocation("shop", config.getLocation("shop"));
        if (config.contains("vip")) config.setLocation("vip", config.getLocation("vip"));

        config.save();
        config.load();
    }

    public void registerScoreBoard() {
        scoreboard = new ConfigManager(this, "scoreboard.yml");
        if (!scoreboard.contains("scoreboard-update-ticks")) {
            scoreboard.set("scoreboard-active", true);
            scoreboard.set("compatible-with-crystolguerra", false);
            scoreboard.setString("scoreboard-title", "&e&lCrystolNetwork");
            scoreboard.set("scoreboard-lines", ListUtil.getStringList(" ", " <f>Nome: <e><player>", " <f>Latência: <ping-color>", " ", " <f>Jogadores: <onlines>", " ", "<e>crystolnetwork.com"));
            scoreboard.set("scoreboard-update-ticks", 40);
        }
        scoreboard.save();
        scoreboard.load();

        if (scoreboard.getBoolean("scoreboard-active") == true) {
            Assemble score = new Assemble(this, new AssembleAdapter() {

                @Override public String getTitle(Player player) {
                    return scoreboard.getString("scoreboard-title");
                }
                @Override public List<String> getLines(Player player) {
                    if (scoreboard.getBoolean("compatible-with-crystolguerra") == true) {
                        if (!GuerraCommand.guerrapeoples.containsValue(player.getUniqueId())) return null;
                    }
                    if (RU.serverVersion.equals(".v1_8_R3.") || RU.serverVersion.equals("v1_8_R3")) {
                        int ping = ((CraftPlayer) player).getHandle().ping;
                        String pingcolor = "&a";
                        if (ping > 150) pingcolor = "&c";
                        String corfinal = pingcolor;

                        List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard-lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", ""+Bukkit.getOnlinePlayers().size()).replace("<ping>", ""+ping+"ms").replace("<ping-color>", "" + corfinal + ping+"ms"));
                        return stringlist;
                    } else {
                        List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard-lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", ""+Bukkit.getOnlinePlayers().size()).replace("<ping>", "&cOnly 1.8.8").replace("<ping-color>", "&cOnly 1.8.8"));
                        return stringlist;
                    }
                }
            });
            score.scoreUpdateTick = scoreboard.getInt("scoreboard-update-ticks");
        }
    }

}
