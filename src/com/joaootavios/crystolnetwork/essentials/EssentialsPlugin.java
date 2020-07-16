package com.joaootavios.crystolnetwork.essentials;

import com.joaootavios.crystolnetwork.essentials.commands.warps.Shop;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Spawn;
import com.joaootavios.crystolnetwork.essentials.commands.warps.Vip;
import com.joaootavios.crystolnetwork.essentials.experienceapi.ExperienceAPI;
import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import rcore.plugin.RPlugin;
import rcore.score.Assemble;
import rcore.score.AssembleAdapter;
import rcore.specificutils.ListUtil;
import rcore.specificutils.PlayerUtil;

import java.util.List;

public class EssentialsPlugin extends RPlugin {

    private Server server;
    private BukkitScheduler bukkitScheduler;
    public static EssentialsConfig config;

    public EssentialsPlugin() {
        super("CrystolEssentials", "JoaoOtavioS & WalkGS");
    }

    @Override public void onPreStart() {
        server = getServer();
        bukkitScheduler = server.getScheduler();
        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();
    }

    @Override public void onStart() {

        // Registrando comandos e eventos.

        verifyDefaultConfig();
        registerCommands();
        registerListeners();
        ExperienceAPI.loadAll();

    }

    @Override public void onStop() {
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
        registerListeners();
    }

    public void startScoreBoard() {











        if (scoreboard.getBoolean("scoreboard-active")) {
            Assemble score = new Assemble(Main.getPlugin(), new AssembleAdapter() {
                @Override
                public String getTitle(Player player) {
                    return Main.scoreboard.getString("scoreboard-title");
                }

                @Override
                public List<String> getLines(Player player) {
                    if (!GuerraCommand.guerrapeoples.containsKey(player.getUniqueId())) {
                        return null;
                    } else {
                        if (SERVER_VERSION.SERVER_PACKAGE_VERSION.equals(".v1_8_R3.")) {
                            int ping = ((CraftPlayer) player).getHandle().ping;
                            String pingcolor = "&a";
                            if (ping > 150) pingcolor = "&c";
                            String corfinal = pingcolor;
                            if (config.getString("modo-de-jogo").equals("mambafactions")) PlayerUtil.sendActionBar(player, "&c&l[GUERRA] &fLatência: " + corfinal + ping + "ms&7 | &fStatus: " + andamento + " &7| &fFacções: " + GuerraCommand.guerrafactionssize.size() + " &7| &f Jogadores: " + GuerraCommand.guerrapeoples.size());
                            if (config.getString("modo-de-jogo").equals("free-for-all")) PlayerUtil.sendActionBar(player, "&c&l[GUERRA] &fLatência: " + corfinal + ping + "ms&7 | &fStatus: " + andamento + " &7| &f Jogadores: " + GuerraCommand.guerrapeoples.size());

                            List<String> stringlist = ListUtil.getColorizedStringList(Main.scoreboard.getStringList("scoreboard-lines"));
                            stringlist.replaceAll(a -> a.replace("<player>", player.getName()));
                            stringlist.replaceAll(a -> a.replace("<jogadoresvivos>", "" + GuerraCommand.guerrapeoples.size()));
                            stringlist.replaceAll(a -> a.replace("<andamento>", "" + andamento));
                            stringlist.replaceAll(a -> a.replace("<ping>", "" + ping));
                            stringlist.replaceAll(a -> a.replace("<ping-color>", "" + corfinal + ping));
                            stringlist.replaceAll(a -> a.replace("<facs>", "" + GuerraCommand.guerrafactionssize.size()));
                            return stringlist;
                        } else {
                            List<String> stringlist = ListUtil.getColorizedStringList(Main.scoreboard.getStringList("scoreboard-lines"));
                            stringlist.replaceAll(a -> a.replace("<player>", player.getName()));
                            stringlist.replaceAll(a -> a.replace("<jogadoresvivos>", "" + GuerraCommand.guerrapeoples.size()));
                            stringlist.replaceAll(a -> a.replace("<andamento>", "" + andamento));
                            stringlist.replaceAll(a -> a.replace("<ping>", "&cInsuportável"));
                            stringlist.replaceAll(a -> a.replace("<ping-color>", "&cInsuportável"));
                            stringlist.replaceAll(a -> a.replace("<facs>", "" + GuerraCommand.guerrafactionssize.size()));
                            return stringlist;
                        }
                    }
                }
            });

            if (scoreboard.getBoolean("force-score-send") == true) {
                score.scoreUpdateTick = 1;
            } else {
                score.scoreUpdateTick = Main.scoreboard.getInt("scoreboard-update-ticks");
            }
        }
    }
}
