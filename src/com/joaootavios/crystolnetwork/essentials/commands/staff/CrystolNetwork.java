package com.joaootavios.crystolnetwork.essentials.commands.staff;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import rcore.banners.Banners;
import rcore.command.RCommand;
import rcore.command.requirement.Req;
import rcore.inventory.Inv;
import rcore.specificutils.PlayerUtil;
import rcore.util.MakeItem;
import rcore.util.RU;
import rcore.util.Sound;
import rcore.util.TXT;
import java.util.List;

public class CrystolNetwork extends RCommand {

    { setPermission("crystolnetwork.gerente"); }

    @Override public String getCommand() { return "crystolnetwork"; }

    Runtime r = Runtime.getRuntime();
    long memUsed = (r.totalMemory() - r.freeMemory()) / 1048576;

    @Override public void perform() {
        Inv inventory = new Inv(6 * 9, "Monitoramento");
        inventory.setItem(11, new MakeItem(Material.APPLE).setName("&eCrystolEssentials").addLoreList("&fEssencial como uma mãça.").build());
        inventory.setItem(13, new MakeItem(Material.EMPTY_MAP).setName("&eInformações:").addLoreList("", "&fPlugins: &7"+ Bukkit.getPluginManager().getPlugins().length, "&fMemória: &7"+ memUsed + "MB", "&fUptime: &7"+ EssentialsPlugin.getServerUptime(), " ", "&fVersão do servidor: &7"+ RU.getServerVersion(), "&fVersão desta build: &7"+ EssentialsPlugin.version).build());
        inventory.setItem(15, new MakeItem(Material.BARRIER).setName("&aRecarregar configurações.").build(), (e) -> {
            Player p = (Player) e.getWhoClicked();
            p.closeInventory();
            EssentialsPlugin.config.load();
            EssentialsPlugin.config.save();
            EssentialsPlugin.scoreboard.load();
            EssentialsPlugin.scoreboard.save();
            PlayerUtil.sendTitle(p, "", "&eRecarregando arquivos..", 5, 30, 5);
            p.sendMessage(TXT.parse("&eScoreboard e demais configurações recarregadas."));
            PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_YES);
        });

        inventory.setItem(29, new MakeItem(Banners.getAlphabet("L", DyeColor.WHITE, DyeColor.BLACK)).hideAttributes().setName("&eChat local.").build(), (e) -> {
                Player p = (Player) e.getWhoClicked();
                p.closeInventory();
                if (EssentialsPlugin.config.getBoolean("chat-local-enable") == true) {
                    EssentialsPlugin.config.set("chat-local-enable", false);
                    PlayerUtil.sendTitle(p, "", "&cChat local desativado.", 5, 30, 5);
                } else {
                    EssentialsPlugin.config.set("chat-local-enable", true);
                    PlayerUtil.sendTitle(p, "", "&eChat local ativado.", 5, 30, 5);
                }
                EssentialsPlugin.config.save();
                EssentialsPlugin.config.load();
                PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setItem(30, new MakeItem(Banners.getAlphabet("G", DyeColor.WHITE, DyeColor.BLACK)).hideAttributes().setName("&eChat global.").build(), (e) -> {
            Player p = (Player) e.getWhoClicked();
            p.closeInventory();
            if (EssentialsPlugin.config.getBoolean("chat-global-enable") == true) {
                EssentialsPlugin.config.set("chat-global-enable", false);
                PlayerUtil.sendTitle(p, "", "&cChat global desativado.", 5, 30, 5);
            } else {
                EssentialsPlugin.config.set("chat-global-enable", true);
                PlayerUtil.sendTitle(p, "", "&eChat global ativado.", 5, 30, 5);
            }
            EssentialsPlugin.config.save();
            EssentialsPlugin.config.load();
            PlayerUtil.playSound(p, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setCancelClick(true);
        inventory.open(asPlayer());
    }

    @Override public List<String> tabComplete() { return null; }
}
