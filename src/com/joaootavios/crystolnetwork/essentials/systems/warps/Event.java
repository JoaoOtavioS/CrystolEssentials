package com.joaootavios.crystolnetwork.essentials.systems.warps;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;
import rcore.util.Sound;
import java.util.List;

import static me.joao.guerra.Main.config;

public class Event extends RCommand {

    { addSubCommand(new EventSet()); setAliases("event");}

    @Override public String getCommand() {
        return "evento";
    }
    @Override public void perform() {
        if (EssentialsPlugin.config.getBoolean("warp.event") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }
        if (EssentialsPlugin.config.contains("event"))
            asPlayer().teleport(EssentialsPlugin.config.getLocation("event"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override public List<String> tabComplete() {
        return null;
    }

    private class EventSet extends RSubCommand {
        { setAliases("definir", "setar");setPermission("crystolnetwork.gerente"); }

        @Override public String getSubCommand() {
            return "set";
        }
        @Override public void perform() {
            sendTitle("", "&aLocalização definida.", 5, 30, 5);
            playSound(Sound.ENTITY_VILLAGER_YES, 1, 1);
            EssentialsPlugin.config.setLocation("event", asPlayer().getLocation());
            config.save();
        }
        @Override public List<String> tabComplete() {
            return null;
        }
    }

}