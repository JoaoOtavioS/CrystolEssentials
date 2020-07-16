package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;

import java.util.List;

public class Spawn extends RCommand {
    private final EssentialsConfig config;
    {
        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();
        addSubCommand(new SpawnSet());
    }

    @Override public String getCommand() { return "spawn"; }

    @Override public void perform() {
        if (config.getBoolean("warp-spawn") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }
        if (config.hasLocation("spawn"))
            asPlayer().teleport(config.getLocation("spawn"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() { return null; }

    private class SpawnSet extends RSubCommand {
        { setAliases("definir", "setar"); setPermission("crystolnetwork.gerente"); }

        @Override
        public String getSubCommand() {
            return "set";
        }

        @Override
        public void perform() {
            config.setLocation("spawn", asPlayer().getLocation());
        }

        @Override
        public List<String> tabComplete() {
            return null;
        }

    }

}
