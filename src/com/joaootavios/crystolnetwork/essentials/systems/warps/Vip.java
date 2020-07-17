package com.joaootavios.crystolnetwork.essentials.systems.warps;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;
import rcore.util.Sound;

import java.util.List;

import static me.joao.guerra.Main.config;

public class Vip extends RCommand {

    { addSubCommand(new VipSet()); }

    @Override
    public String getCommand() {
        return "vip";
    }

    @Override
    public void perform() {
        if (!hasPermission("crystolnetwork.vip")) {
            sendNoMessage(Messages.ERRORNOVIP.getMessage());
            return;
        }
        if (EssentialsPlugin.config.getBoolean("warp-vip") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }

        if (EssentialsPlugin.config.contains("vip"))
            asPlayer().teleport(EssentialsPlugin.config.getLocation("vip"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() {
        return null;
    }

    private class VipSet extends RSubCommand {
        { setAliases("definir", "setar");setPermission("crystolnetwork.gerente"); }

        @Override public String getSubCommand() {
            return "set";
        }
        @Override public void perform() {
            sendTitle("", "&aLocalização definida.", 5, 30, 5);
            playSound(Sound.ENTITY_VILLAGER_YES, 1, 1);
            EssentialsPlugin.config.setLocation("vip", asPlayer().getLocation());
            config.save();
        }
        @Override public List<String> tabComplete() {
            return null;
        }
    }

}
