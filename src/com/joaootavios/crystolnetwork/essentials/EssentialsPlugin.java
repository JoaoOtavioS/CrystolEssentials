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
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import rcore.plugin.RPlugin;
import rcore.score.Assemble;
import rcore.score.AssembleAdapter;
import rcore.specificutils.ListUtil;
import rcore.specificutils.PlayerUtil;
import rcore.util.RU;

import java.util.List;

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
        registerScoreBoard();

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
            config.setString("scoreboard-title", "&e&lCrystolNetwork");
            config.setStringList("scoreboard-lines", ListUtil.getStringList(" ", " <f>Nome: <e><player>", " <f>Latência: <ping-color>ms", " "," <f>Status: <andamento>", " ", " <f>Vivos: <jogadoresvivos>", " ", "<e>crystolnetwork.com"));
            config.setInt("scoreboard-update-ticks", 20);
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

    public void registerScoreBoard() {
        if (config.getBoolean("scoreboard-active") == true) {

            Assemble score = new Assemble(this, new AssembleAdapter() {
                @Override public String getTitle(Player player) {
                    return config.getString("scoreboard-title");
                }

                @Override public List<String> getLines(Player player) {
                    if (RU.serverVersion.equals(".v1_8_R3.")) {
                        int ping = ((CraftPlayer) player).getHandle().ping;
                        String pingcolor = "&a";
                        if (ping > 150) pingcolor = "&c";
                        String corfinal = pingcolor;

                        List<String> stringlist = ListUtil.getColorizedStringList(config.getStringList("scoreboard-lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()));
                        return stringlist;
                    } else {
                        List<String> stringlist = ListUtil.getColorizedStringList(config.getStringList("scoreboard-lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()));
                        return stringlist;
                    }
                }
            });
            score.scoreUpdateTick = config.getInt("scoreboard-update-ticks");
        }
    }
}
