package com.joaootavios.crystolnetwork.essentials;

import com.joaootavios.crystolnetwork.essentials.commands.staff.CrystolNetwork;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Shop;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Spawn;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Vip;
import com.joaootavios.crystolnetwork.essentials.experienceapi.ExperienceAPI;
import com.joaootavios.crystolnetwork.essentials.listeners.BadEventsListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EnderPearlListener;
import com.joaootavios.crystolnetwork.essentials.listeners.EntityChangeBlockListener;
import com.joaootavios.crystolnetwork.essentials.listeners.WeatherChangeListener;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import dev.walk.economy.Manager.Account;
import dev.walk.economy.Manager.AccountManager;
import dev.walk.economy.Util.EconomyUtils;
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
import rcore.util.Cooldown;
import rcore.util.RU;
import java.util.List;

public class EssentialsPlugin extends RPlugin {

    private Server server;
    private static long start;
    private BukkitScheduler bukkitScheduler;
    public static ConfigManager config;
    public static ConfigManager scoreboard;
    private static Cooldown delay = new Cooldown();

    public static String version = "alpha-build-01";
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

        start = delay.getCurrentTime();

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
        registerCommands(new CrystolNetwork());
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
            scoreboard.set("scoreboard-update-ticks", 40);
            scoreboard.set("compatible-with-crystolguerra", false);
            scoreboard.setString("scoreboard-title", "&e&lCrystolNetwork");
            scoreboard.set("scoreboard-lines", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &fJogadores: <onlines>", " "," &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
            scoreboard.set("compatible-with-factions", true);
            scoreboard.set("scoreboard-lines-hasfac", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &eFacção: <faction_name>", "  &f<faction_online>", "  &f<faction_power>", "  &f<faction_land>", " ", " &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
            scoreboard.set("scoreboard-lines-nofac", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &cSem facção.", " "," &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
        }
        scoreboard.save();
        scoreboard.load();

        final boolean compatible = RU.serverVersion.equals(".v1_8_R3.") || RU.serverVersion.equals("v1_8_R3");
        final boolean hasEconomyPlugin = getServer().getPluginManager().getPlugin("CrystolEconomy") != null;

        if (scoreboard.getBoolean("scoreboard-active") == true) {
            Assemble score = new Assemble(this, new AssembleAdapter() {
                @Override public String getTitle(Player player) {
                    return scoreboard.getString("scoreboard-title");
                }
                @Override public List<String> getLines(Player player) {
                    if (scoreboard.getBoolean("compatible-with-crystolguerra") == true) if (!GuerraCommand.guerrapeoples.containsValue(player.getUniqueId())) return null;

                    final int ping = (compatible ? ((CraftPlayer) player).getHandle().ping : -1);
                    final String pingcolor = (ping > 150 ? "&c" : "&a");
                    final double coins = (hasEconomyPlugin ? new AccountManager(player.getUniqueId()).getInstance().getMoney() : -1);

                    if (scoreboard.getBoolean("compatible-with-factions") == true) {
                        MPlayer mp = MPlayer.get(player.getUniqueId());
                        Faction faction = mp.getFaction();
                        if (mp.hasFaction()) {
                            List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard-lines-hasfac"));
                            stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor +ping + "ms")).replace("<faction_name>", faction.getColor()+"["+faction.getTag()+"] "+faction.getName()).replace("<faction_online>", faction.getOnlinePlayers().size()+"/"+faction.getMPlayers().size()).replace("<faction_power>", faction.getPowerRounded()+"/"+faction.getPowerMaxRounded()).replace("<faction_land>", ""+faction.getLandCount()).replace("<player_power>", mp.getPowerRounded()+"/"+mp.getPowerMaxRounded()).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")));
                            return stringlist;
                        } else {
                            List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard-lines-nofac"));
                                stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor +ping + "ms")).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")));
                            return stringlist;
                        }
                    } else {
                        // No Compatible Factions
                        List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard-lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor + ping + "ms")).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")));
                        return stringlist;
                    }
                }
            });
            score.scoreUpdateTick = scoreboard.getInt("scoreboard-update-ticks");
        }
    }

    public static String getServerUptime() {
        return delay.getTimeString(delay.getCurrentTime() - start);
    }

}
