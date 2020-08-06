package com.joaootavios.crystolnetwork.essentials.systems.chats;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.api.CooldownAPI;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import dev.walk.economy.Manager.Account;
import dev.walk.economy.Manager.AccountManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import rcore.command.RCommand;
import rcore.specificutils.PlayerUtil;
import rcore.util.Sound;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Tell extends RCommand implements Listener {

    private final CooldownAPI cooldownAPI = new CooldownAPI();
    private final boolean hasEconomyPlugin = getServer().getPluginManager().getPlugin("CrystolEconomy") != null;
    @Override public String getCommand() {
        return "tell";
    }

    @Override public void perform() {

        if (asPlayer().hasMetadata("silenciado")) {
            sendMessage(Messages.ERRORMUTED.getMessage());
            return;
        }

        if (hasNoArgs()) {
            sendMessage(Messages.TELLCMD.getMessage());
            playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        if (hasEconomyPlugin) {
            final Account account = new AccountManager(asPlayer().getUniqueId()).getInstance();
            if (!hasPermission("crystolnetwork.ajudante")) {
                if (account.getMoney() < 250) {
                    sendMessage(Messages.NOHAVE250COINS.getMessage());
                    return;
                }
            }
        }

        if (isArgAtIgnoreCase(0, asPlayer().getName())) {
            sendMessage(Messages.TELLNOHAVEFRIENDS.getMessage());
            playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        if (PlayerUtil.hasMetadata(asPlayer(), "tell_desativado")) {
            sendMessage(asPlayer(), "<c>Você não pode enviar uma mensagem privada com o &4seu&c tell desativado.");
            return;
        }

        final Player target = getPlayer(argAt(0));
        if (target == null) {
            sendMessage("<7>%s<c> não está online ou está com tell desativado.", argAt(0));
            playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        if (target.hasMetadata("tell_desativado")) {
            sendMessage("<7>%s<c> não está online ou está com tell desativado.", argAt(0));
            playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        if (cooldownAPI.getCooldownRemaining(asPlayer().getUniqueId(), "tell_cooldown") > 0) {
            sendActionBar(asPlayer(), "&cAguarde " + cooldownAPI.getCooldownRemainingVerb(asPlayer().getUniqueId(), "tell_cooldown") + " para enviar uma mensagem privada novamente.");
            playSound(asPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;

        } else {

            cooldownAPI.setCooldown(asPlayer().getUniqueId(), "tell_cooldown", EssentialsPlugin.config.getLong("cooldown_tell"));
            PlayerUtil.setMetadata(EssentialsPlugin.getPlugin(EssentialsPlugin.class), target, "returntell", asPlayer());
            sendMessage(target, "<8>[Mensagem de " + asPlayer().getName() + "<8>]" + " <6>" + createString(getArgs(), 1));
            sendMessage("<8>[Mensagem para " + target.getName() + "<8>]" + " <6>" + createString(getArgs(), 1));
            sendTitle(target, "", Messages.TELLRECEIVE.getMessage(), 0, 20, 5);
            playSound(target, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            playSound(asPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            return;
        }
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}