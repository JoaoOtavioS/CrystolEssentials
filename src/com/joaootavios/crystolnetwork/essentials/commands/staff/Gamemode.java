package com.joaootavios.crystolnetwork.essentials.commands.staff;

import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import rcore.banners.Banners;
import rcore.command.RCommand;
import rcore.inventory.Inv;
import rcore.specificutils.PlayerUtil;
import rcore.util.MakeItem;
import rcore.util.Sound;
import rcore.util.TXT;

import java.util.List;

public class Gamemode extends RCommand {

    { setPermission("crystolnetwork.moderador");setAliases("gm"); }

    @Override
    public String getCommand() {
        return "gamemode";
    }

    @Override
    public void perform() {
        if (hasNoArgs()) gamemodeMenu(asPlayer(), asPlayer());
        else {
            Player player = getPlayer(readArg());
            if (isPlayerNotOnline(player)) {
                sendMessage(Messages.ERRORPLAYEROFFLINE.getMessage());
                return;
            }
            gamemodeMenu(player, asPlayer());
        }
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }

    public void gamemodeMenu(Player p1, Player open_menu) {
        final String changed = "&eO modo de jogo de "+p1.getName()+" foi alterado.";
        Inv inventory = new Inv(1 * 9, "Modo de Jogo de @"+p1.getName());
        inventory.setItem(1, new MakeItem(Banners.getAlphabet("A", DyeColor.BLACK, DyeColor.WHITE)).setName("&eAventura").build(), (e) -> {
            p1.setGameMode(GameMode.ADVENTURE);
            open_menu.closeInventory();
            open_menu.sendMessage(TXT.parse(changed));
            PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setItem(2, new MakeItem(Banners.getAlphabet("S", DyeColor.BLACK, DyeColor.WHITE)).setName("&eSobrevivência").build(), (e) -> {
            p1.setGameMode(GameMode.SURVIVAL);
            open_menu.closeInventory();
            open_menu.sendMessage(TXT.parse(changed));
            PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setItem(3, new MakeItem(Banners.getAlphabet("C", DyeColor.BLACK, DyeColor.WHITE)).setName("&eCriativo").build(), (e) -> {
            if (!open_menu.hasPermission("crystolnetwork.gerente")) {
                open_menu.closeInventory();
                PlayerUtil.sendTitle(open_menu, "", "&cPermissão insuficiente.", 5, 30, 5);
                PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_NO);
            }
            p1.setGameMode(GameMode.CREATIVE);
            open_menu.closeInventory();
            open_menu.sendMessage(TXT.parse(changed));
            PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setItem(4, new MakeItem(Banners.getAlphabet("E", DyeColor.BLACK, DyeColor.WHITE)).setName("&eEspectador").build(), (e) -> {
            p1.setGameMode(GameMode.SPECTATOR);
            open_menu.closeInventory();
            open_menu.sendMessage(TXT.parse(changed));
            PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_YES);
        });
        inventory.setItem(6, new MakeItem(Banners.getAlphabet("I", DyeColor.BLACK, DyeColor.WHITE)).setName("&eInvencível").build(), (e) -> {
            open_menu.closeInventory();
            PlayerUtil.setAbilityIsInvulnerable(p1, !PlayerUtil.isAbilityInvulnerable(p1));
            if (PlayerUtil.isAbilityInvulnerable(p1))
                PlayerUtil.sendTitle(open_menu, "", "&aInvencíbilidade ativada.", 5, 30, 5);
            else
                PlayerUtil.sendTitle(open_menu, "", "&cInvencíbilidade desativada.", 5, 30, 5);
            PlayerUtil.playSound(open_menu, Sound.ENTITY_VILLAGER_YES);
        });
        PlayerUtil.playSound(open_menu, Sound.BLOCK_CHEST_OPEN);
        inventory.setCancelClick(true);
        inventory.open(open_menu);
    }

}