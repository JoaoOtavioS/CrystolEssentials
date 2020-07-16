package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;
import rcore.util.Sound;

import java.util.List;

public class Spawn extends RCommand {

    { addSubCommand(new SpawnSet()); }

    @Override public String getCommand() {
        return "spawn";
    }
    @Override public void perform() {
        if (EssentialsPlugin.config.getBoolean("warp-spawn") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }
        if (EssentialsPlugin.config.contains("spawn"))
            asPlayer().teleport(EssentialsPlugin.config.getLocation("spawn"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() {
        return null;
    }

    private class SpawnSet extends RSubCommand {
        { setAliases("definir", "setar"); setPermission("crystolnetwork.gerente"); }

        @Override public String getSubCommand() {
            return "set";
        }
        @Override public void perform() {
            sendTitle("", "&aLocalização definida.", 5, 30, 5);
            playSound(Sound.ENTITY_VILLAGER_YES, 1, 1);
            EssentialsPlugin.config.setLocation("spawn", asPlayer().getLocation());
        }
        @Override public List<String> tabComplete() {
            return null;
        }
    }

}
