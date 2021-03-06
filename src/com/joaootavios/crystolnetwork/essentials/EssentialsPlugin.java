package com.joaootavios.crystolnetwork.essentials;

import com.joaootavios.crystolnetwork.essentials.commands.members.Lanterna;
import com.joaootavios.crystolnetwork.essentials.commands.members.Lixeira;
import com.joaootavios.crystolnetwork.essentials.commands.members.PoteXP;
import com.joaootavios.crystolnetwork.essentials.commands.staff.CrystolNetwork;
import com.joaootavios.crystolnetwork.essentials.commands.staff.Gamemode;
import com.joaootavios.crystolnetwork.essentials.listeners.*;
import com.joaootavios.crystolnetwork.essentials.systems.chats.GlobalChat;
import com.joaootavios.crystolnetwork.essentials.systems.chats.LocalChat;
import com.joaootavios.crystolnetwork.essentials.systems.chats.Tell;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.SpawnerCommand;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners.SpawnerBreakListener;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners.SpawnerExplosionListener;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners.SpawnerInteractListener;
import com.joaootavios.crystolnetwork.essentials.systems.spawnersystem.listeners.SpawnerPlaceListener;
import com.joaootavios.crystolnetwork.essentials.systems.warps.*;
import com.joaootavios.crystolnetwork.essentials.api.experienceapi.ExperienceAPI;
import com.joaootavios.crystolnetwork.essentials.systems.StackMobs;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
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

        if (config.getBoolean("warp.spawn") == true) registerCommand(new Spawn());
        if (config.getBoolean("warp.shop") == true) registerCommand(new Shop());
        if (config.getBoolean("warp.vip") == true) registerCommand(new Vip());
        if (config.getBoolean("warp.arena") == true) registerCommand(new Arena());
        if (config.getBoolean("warp.event") == true) registerCommand(new Event());
        if (config.getBoolean("spawnersystem.enable")) registerCommand(new SpawnerCommand());

        registerCommands(
                new CrystolNetwork(), new Gamemode(),
                new Lanterna(), new Lixeira(),
                new GlobalChat(), new Tell(), new PoteXP());
    }

    private void registerListeners() {
        if (config.getBoolean("stackmobs.enable") == true) setListener(new StackMobs());
        if (config.getBoolean("disable.enderpearl-cooldown") == false) setListener(new EnderPearlListener());
        if (config.getBoolean("spawnersystem.enable")) setListeners(new SpawnerBreakListener(), new SpawnerPlaceListener(), new SpawnerExplosionListener(), new SpawnerInteractListener());

        setListeners(new LocalChat(), new PlayerJoinListener(), new PlayerQuitListener());
        setListeners(new BadEventsListener(), new EntityChangeBlockListener(), new WeatherChangeListener());
    }

    private void registerDefaultConfig() {
        config = new ConfigManager(this, "config.yml");
        if (!config.contains("warp.vip")) {
            config.set("chat-global-enable", true);
            config.set("chat-local-enable", true);
            config.set("cooldown_tell", 3L);
            config.set("localchat-no-entity", false);
            config.set("localchat-no-entity-msg", "&cNão há jogadores próximos para ler sua mensagem.");
            config.set("warp.spawn", true);
            config.set("warp.shop", true);
            config.set("warp.vip", false);
            config.set("warp.arena", false);
            config.set("warp.event", false);
            config.set("compatible-with-factions", false);
            config.set("tablist.enable", true);
            config.set("tablist.header", ListUtil.getStringList(" ", "&e&lCRYSTOLNETWORK ", " "));
            config.set("tablist.footer", ListUtil.getStringList(" ", "&eIP: &fcrystolnetwork.com ", "&eWeb: &floja.crystolnetwork.com ", " "));
            config.set("title.enable", true);
            config.set("title.fadein", 10);
            config.set("title.stay", 40);
            config.set("title.fadeout", 10);
            config.set("title.title", "&e&lCrystolNetwork");
            config.set("title.subtitle", "&fEstá funcionando, corno!");
            config.set("welcome-message.enable", true);
            config.set("welcome-message.clear-chat", true);
            config.set("welcome-message.message", ListUtil.getStringList(" ", "&e&lCrystolNetwork", "", "&fSeja bem-vindo, cú de urubu <3", " "));
            config.set("spawnersystem.enable", true);
            config.set("stackmobs.enable", true);
            config.set("stackmobs.limit", 1000);
            config.set("disable.join-message", true);
            config.set("disable.quit-message", true);
            config.set("disable.experiencesystem", false);
            config.set("disable.weather", true);
            config.set("disable.falling-blocks", true);
            config.set("disable.food-event", true);
            config.set("disable.bad-events", true);
            config.set("disable.natural-spawn-mobs", true);
            config.set("disable.enderpearl-cooldown", false);
            config.set("disable.cooldown-enderpearl", 5L);
        }
        if (config.contains("spawn")) config.setLocation("spawn", config.getLocation("spawn"));
        if (config.contains("shop")) config.setLocation("shop", config.getLocation("shop"));
        if (config.contains("vip")) config.setLocation("vip", config.getLocation("vip"));
        if (config.contains("arena")) config.setLocation("arena", config.getLocation("arena"));

        config.save();
        config.load();
    }

    private void registerScoreBoard() {
        scoreboard = new ConfigManager(this, "scoreboard.yml");
        if (!scoreboard.contains("scoreboard.update-ticks")) {
            scoreboard.set("compatible.with-factions", false);
            scoreboard.set("compatible.with-crystolguerra", false);
            scoreboard.set("scoreboard.enable", true);
            scoreboard.set("scoreboard.update-ticks", 40);
            scoreboard.setString("scoreboard.title", "&e&lCrystolNetwork");
            scoreboard.set("scoreboard.lines", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &fJogadores: <onlines>", " "," &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
            scoreboard.set("scoreboard.lines-hasfac", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &eFacção: <faction_name>", "  &fOnlines: &7<faction_online>", "  &fPoder: &7<faction_power>", "  &fTerras: &7<faction_land>", " ", " &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
            scoreboard.set("scoreboard.lines-nofac", ListUtil.getStringList(" ", " &fNome: &e<player>", " &fLatência: <ping>", " ", " &cSem facção.", " "," &fMoedas: &a<coins>", " &fCash: &6<cash> ", " ", " &ecrystolnetwork.com"));
        }
        scoreboard.save();
        scoreboard.load();

        final boolean compatible = RU.serverVersion.equals(".v1_8_R3.") || RU.serverVersion.equals("v1_8_R3");
        final boolean hasEconomyPlugin = getServer().getPluginManager().getPlugin("CrystolEconomy") != null;
        final boolean hasFactionPlugin = getServer().getPluginManager().getPlugin("Factions") != null;

        if (scoreboard.getBoolean("scoreboard.enable") == true) {
            Assemble score = new Assemble(this, new AssembleAdapter() {
                @Override public String getTitle(Player player) {
                    return scoreboard.getString("scoreboard.title");
                }
                @Override public List<String> getLines(Player player) {
                    if (scoreboard.getBoolean("compatible.with-crystolguerra") == true) if (!GuerraCommand.guerrapeoples.containsValue(player.getUniqueId())) return null;

                    final int ping = (compatible ? ((CraftPlayer) player).getHandle().ping : -1);
                    final String pingcolor = (ping > 150 ? "&c" : "&a");
                    final double coins = (hasEconomyPlugin ? new AccountManager(player.getUniqueId()).getInstance().getMoney() : -1);

                    if (scoreboard.getBoolean("compatible.with-factions") == true) {
                        if (hasFactionPlugin) {
                            final MPlayer mp = MPlayer.get(player.getUniqueId());
                            final Faction faction = mp.getFaction();
                            if (mp.hasFaction()) {
                                List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard.lines-hasfac"));
                                stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor + ping + "ms")).replace("<faction_name>", faction.getColor() + "[" + faction.getTag() + "] " + faction.getName()).replace("<faction_online>", faction.getOnlinePlayers().size() + "/" + faction.getMPlayers().size()).replace("<faction_power>", faction.getPowerRounded() + "/" + faction.getPowerMaxRounded()).replace("<faction_land>", "" + faction.getLandCount()).replace("<player_power>", mp.getPowerRounded() + "/" + mp.getPowerMaxRounded()).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")).replace("<nivel>", "" + (ExperienceAPI.getTotalLevels(player) == 0 ? "&cNenhum" : ExperienceAPI.getTotalLevels(player))));
                                return stringlist;
                            } else {
                                List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard.lines-nofac"));
                                stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor + ping + "ms")).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")).replace("<nivel>", "" + (ExperienceAPI.getTotalLevels(player) == 0 ? "&cNenhum" : ExperienceAPI.getTotalLevels(player))));
                                return stringlist;
                            }
                        }

                        // No Compatible Factions
                        List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard.lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor + ping + "ms")).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")).replace("<nivel>", ""+(ExperienceAPI.getTotalLevels(player) == 0 ? "&cNenhum" : ExperienceAPI.getTotalLevels(player))));
                        return stringlist;

                    } else {

                        // No Compatible Factions
                        List<String> stringlist = ListUtil.getColorizedStringList(scoreboard.getStringList("scoreboard.lines"));
                        stringlist.replaceAll(a -> a.replace("<player>", player.getName()).replace("<onlines>", "" + Bukkit.getOnlinePlayers().size()).replace("<ping>", (ping == -1 ? "&cOnly 1.8.8" : pingcolor + ping + "ms")).replace("<coins>", (hasEconomyPlugin ? (coins > 0 ? new EconomyUtils().formatMoney(coins) : "&cNenhum") : "&cNot found")).replace("<nivel>", ""+(ExperienceAPI.getTotalLevels(player) == 0 ? "&cNenhum" : ExperienceAPI.getTotalLevels(player))));
                        return stringlist;
                    }
                }
            });
            score.scoreUpdateTick = scoreboard.getInt("scoreboard.update-ticks");
        }
    }

    public static String getServerUptime() {
        return delay.getTimeString(delay.getCurrentTime() - start);
    }

}
