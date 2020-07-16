package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;

import java.util.List;

public class Vip extends RCommand {
    private final EssentialsConfig config;
    {
        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();
        addSubCommand(new VipSet());
    }

    @Override public String getCommand() { return "vip"; }

    @Override public void perform() {
        if (!hasPermission("crystolnetwork.vip")) {
            sendNoMessage(Messages.ERRORNOVIP.getMessage());
            return;
        }
        if (config.getBoolean("warp-vip") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }

        if (config.hasLocation("vip"))
            asPlayer().teleport(config.getLocation("vip"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() { return null; }

    private class VipSet extends RSubCommand {
        { setAliases("definir", "setar"); setPermission("crystolnetwork.gerente"); }

        @Override
        public String getSubCommand() {
            return "set";
        }

        @Override
        public void perform() {
            config.setLocation("vip", asPlayer().getLocation());
        }

        @Override
        public List<String> tabComplete() {
            return null;
        }

    }

}
