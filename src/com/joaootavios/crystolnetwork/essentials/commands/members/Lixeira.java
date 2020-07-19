package com.joaootavios.crystolnetwork.essentials.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import rcore.command.RCommand;

import java.util.List;

public class Lixeira extends RCommand {

    { setAliases("lixeira"); }

    @Override
    public String getCommand() {
        return "lixo";
    }

    @Override
    public void perform() {
        Inventory inv = Bukkit.createInventory(null, 6 * 9, "Lixeira");
        asPlayer().openInventory(inv);
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
