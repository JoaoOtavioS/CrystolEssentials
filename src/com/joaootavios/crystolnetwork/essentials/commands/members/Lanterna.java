package com.joaootavios.crystolnetwork.essentials.commands.members;

import com.joaootavios.crystolnetwork.essentials.utils.UUIDMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rcore.command.RCommand;
import rcore.util.Sound;

import java.util.List;

public class Lanterna extends RCommand {

    {setAliases("luz");}

    @Override
    public String getCommand() {
        return "lanterna";
    }

    @Override
    public void perform() {
        if (UUIDMeta.containsData(asPlayer().getUniqueId(), "lanterna")) {
            sendActionBar("<c>Você desativou o sistema de visão noturna.");
            asPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
        } else {
            sendActionBar("<e>Você ativou o sistema de visão noturna.");
            asPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
        }
        playSound(asPlayer(), Sound.ENTITY_VILLAGER_YES, 1, 1);
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
