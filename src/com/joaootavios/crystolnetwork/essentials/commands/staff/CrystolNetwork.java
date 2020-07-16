package com.joaootavios.crystolnetwork.essentials.commands.staff;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rcore.command.RCommand;
import rcore.command.requirement.Req;
import rcore.inventory.Inv;
import rcore.specificutils.PlayerUtil;
import rcore.util.MakeItem;
import rcore.util.Sound;
import rcore.util.TXT;
import java.util.List;

public class CrystolNetwork extends RCommand {

    { setPermission("crystolnetwork.gerente"); }

    @Override public String getCommand() { return "crystolnetwork"; }

    Runtime r = Runtime.getRuntime();
    long memUsed = (r.totalMemory() - r.freeMemory()) / 1048576;

    @Override public void perform() {
        Inv inventory = new Inv(3 * 9, "Monitoramento");
        inventory.setItem(11, new MakeItem(Material.APPLE).setName("&eCrystolEssentials").addLoreList("&fEssencial como uma mãça.").build());
        inventory.setItem(13, new MakeItem(Material.EMPTY_MAP).setName("&eInformações:").addLoreList("", "&fPlugins: &7"+ Bukkit.getPluginManager().getPlugins().length, "&fMemória: &7"+ memUsed + "MB", "&fUptime: &7"+ EssentialsPlugin.getServerUptime(), " ", "&fVersão do servidor: &7"+ Bukkit.getVersion(), "&fVersão desta build: &7"+ EssentialsPlugin.version).build());
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
        inventory.setCancelClick(true);
        inventory.open(asPlayer());
    }

    @Override public List<String> tabComplete() { return null; }
}
