package com.joaootavios.crystolnetwork.essentials.commands.warps;

import com.joaootavios.crystolnetwork.essentials.services.EssentialsServices;
import com.joaootavios.crystolnetwork.essentials.utils.EssentialsConfig;
import com.joaootavios.crystolnetwork.essentials.utils.Messages;
import rcore.command.RCommand;
import rcore.command.subcommand.RSubCommand;

import java.util.List;

public class Shop extends RCommand {
    private final EssentialsConfig config;

    {
        config = EssentialsServices.getInstance().getServerConfig().getEssentialsConfig();
        addSubCommand(new ShopSet());
        setAliases("loja");
    }

    @Override
    public String getCommand() {
        return "shop";
    }

    @Override
    public void perform() {
        if (config.getBoolean("warp-shop") == false) {
            sendNoMessage(Messages.ERRORLOCDISABLE.getMessage());
            return;
        }
        if (config.hasLocation("shop"))
            asPlayer().teleport(config.getLocation("shop"));
        else
            sendNoMessage(Messages.ERRORLOC.getMessage());
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }

    private class ShopSet extends RSubCommand {
        {
            setAliases("definir", "setar");
            setPermission("crystolnetwork.gerente");
        }

        @Override
        public String getSubCommand() {
            return "set";
        }

        @Override
        public void perform() {
            config.setLocation("shop", asPlayer().getLocation());
        }

        @Override
        public List<String> tabComplete() {
            return null;
        }

    }

}