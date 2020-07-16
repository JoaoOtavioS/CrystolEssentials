package com.joaootavios.crystolnetwork.essentials.commands.staff;

import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import rcore.command.RCommand;
import rcore.command.requirement.Req;

import java.util.List;

public class GamemodeCommand extends RCommand {

    private final EssentialsConfig config;

    @Override
    public String getCommand() {
        return null;
    }

    {

        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();

        setPermission(config.getString("permission-need")); setAliases("stop");
        addRequirement(Req.HAS_PERMISSION);
    }

    @Override
    public void perform() {

        if (config.getFactionsMode()){

        }
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
