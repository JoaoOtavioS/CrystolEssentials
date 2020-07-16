package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;
import rcore.util.Sound;

import java.util.List;

public class Shop extends RCommand {

    { addSubCommand(new ShopSet());setAliases("loja"); }

    @Override public String getCommand() {
        return "shop";
    }
    @Override public void perform() {
        if (EssentialsPlugin.config.getBoolean("warp-shop") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }
        if (EssentialsPlugin.config.contains("shop"))
            asPlayer().teleport(EssentialsPlugin.config.getLocation("shop"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() {
        return null;
    }

    private class ShopSet extends RSubCommand {
        { setAliases("definir", "setar");setPermission("crystolnetwork.gerente"); }

        @Override public String getSubCommand() {
            return "set";
        }
        @Override public void perform() {
            sendTitle("", "&aLocalização definida.", 5, 30, 5);
            playSound(Sound.ENTITY_VILLAGER_YES, 1, 1);
            EssentialsPlugin.config.setLocation("shop", asPlayer().getLocation());
        }
        @Override public List<String> tabComplete() {
            return null;
        }
    }

}