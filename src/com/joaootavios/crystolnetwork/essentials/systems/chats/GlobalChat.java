package com.joaootavios.crystolnetwork.essentials.systems.chats;

import com.crystolnetwork.offices.entity.PlayerBase;
import com.crystolnetwork.offices.services.OfficesServices;
import com.crystolnetwork.offices.services.SingletonService;
import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.CooldownAPI;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import com.massivecraft.factions.entity.MPlayer;
import dev.walk.economy.Manager.AccountManager;
import dev.walk.economy.Manager.Magnata;
import rcore.command.RCommand;
import rcore.specificutils.PlayerUtil;
import rcore.util.Sound;
import rcore.util.TXT;
import java.util.List;
import static org.bukkit.Bukkit.getServer;

public class GlobalChat extends RCommand {

    private final CooldownAPI cooldownAPI = new CooldownAPI();
    private final boolean hasOfficePlugin = getServer().getPluginManager().getPlugin("CrystolOffices") != null;
    private final boolean hasEconomyPlugin = getServer().getPluginManager().getPlugin("CrystolEconomy") != null;
    private OfficesServices services;
    private PlayerBase playerBase;

    { setAliases("global");}

    @Override
    public String getCommand() {
        return "g";
    }

    @Override
    public void perform() {

        if (EssentialsPlugin.config.getBoolean("chat-global-enable") == false) {
            PlayerUtil.playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO);
            sendMessage(Messages.GLOBALCHAT.getMessage());
            return;
        }

        if (hasNoArgs()) {
            PlayerUtil.playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO);
            sendMessage(Messages.GLOBALCHATCMD.getMessage());
            return;
        }

        if (services == null && hasOfficePlugin){
            services = SingletonService.getOrFill(OfficesServices.class);
            playerBase = services.getPlayerBase();
        }

        final String economy = (hasEconomyPlugin ? (new Magnata().isMagnata(new AccountManager(asPlayer().getUniqueId()).getInstance()) ? "<2>[$]" : "") : "&c[Economy not found]&r");
        final String cargo = (hasOfficePlugin ? playerBase.getUser(asPlayer()).getLargestGroup().getPrefix() : "&c[Offices not found]&r");

        if (cooldownAPI.getCooldownRemaining(asPlayer().getUniqueId(), "globalchat") > 0) {
            PlayerUtil.playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO);
            sendMessage("&cAguarde " + cooldownAPI.getCooldownRemainingVerb(asPlayer().getUniqueId(), "globalchat") + " para falar no chat.");
            return;
        }

        if (EssentialsPlugin.config.getBoolean("compatible-with-factions") == true) {
            final MPlayer mp = MPlayer.get(asPlayer().getUniqueId());
            final String factionTag = mp.hasFaction() ? "&7[&f" + mp.getFaction().getColor() + mp.getFactionTag() + "&7]" : "";
            final String factionRole = mp.hasFaction() ? "&7" + mp.getRole().getPrefix() : "";
            final String formatedMessage = TXT.parse("&7[G] &r"+ economy + " " + factionTag + " " + cargo + " " + factionRole + asPlayer().getName() + " &f» &e" + createString(getArgs(), 0));
            cooldownAPI.setCooldown(asPlayer().getUniqueId(), "globalchat", 10L);
            PlayerUtil.broadcast(formatedMessage);
        } else {
            final String formatedMessage = TXT.parse("&7[G] &r"+ economy + " " + cargo + " " + asPlayer().getName() + " &f» &e" + createString(getArgs(), 0));
            cooldownAPI.setCooldown(asPlayer().getUniqueId(), "globalchat", 10L);
            PlayerUtil.broadcast(formatedMessage);
        }
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
