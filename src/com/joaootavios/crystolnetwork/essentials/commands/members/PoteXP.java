package com.joaootavios.crystolnetwork.essentials.commands.members;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import rcore.command.RCommand;
import rcore.util.MakeItem;

import java.util.List;

public class PoteXP extends RCommand implements Listener {

    @Override
    public String getCommand() {
        return "potexp";
    }

    @Override
    public void perform() {

        if (asPlayer().getExp() == 0) {
            sendMessage("&cVocê não possuí nenhuma experiência.");
            return;
        } else if (asPlayer().getLevel() == 0){
            sendMessage("&cVocê não possuí nenhum level completo.");
            return;
        }

        if (hasNoArgs()) {
            sendMessage("&cDigite: /potexp <níveis> para compactar sua experiência.");
            return;
        }

        try {
            if (asPlayer().getLevel() >= Integer.parseInt(argAt(0))) {
                sendMessage("&aExperiência compactada com sucesso.");
                asPlayer().setLevel(asPlayer().getLevel() - Integer.parseInt(argAt(0)));
                asPlayer().getInventory().addItem(new MakeItem(Material.EXP_BOTTLE).setName("&ePote XP").addLoreList("&eExperiência: " + argAt(0)).build());
            }
        } catch (Exception e) {
            sendMessage("<7>%s <c>não é um número válido.", argAt(0));
            return;
        }
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
