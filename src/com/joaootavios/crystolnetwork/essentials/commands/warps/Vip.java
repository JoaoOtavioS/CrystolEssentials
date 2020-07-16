package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;

import java.util.List;

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
        @Override public void perform() { EssentialsPlugin.config.setLocation("vip", asPlayer().getLocation()); }
        @Override public List<String> tabComplete() { return null; }
    }

}
