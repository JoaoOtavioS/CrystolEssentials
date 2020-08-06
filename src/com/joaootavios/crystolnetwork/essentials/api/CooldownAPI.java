package com.joaootavios.crystolnetwork.essentials.api;

import org.bukkit.entity.Player;
import rcore.util.Cooldown;

import java.util.UUID;

public class CooldownAPI {
    private final Cooldown cooldown = new Cooldown();

    public void setCooldown(UUID uuid, String type, Long time) {
        UUIDMeta.setMetadata(uuid, type, Cooldown.getCurrentTime() + time);
    }

    public void setCooldown(Player player, String type, Long time) {
        UUIDMeta.setMetadata(player.getUniqueId(), type, Cooldown.getCurrentTime() + time);
    }

    public Long getCooldown(UUID uuid, String type) {
        if (!hasCooldown(uuid, type)) return -1L;
        return (Long) UUIDMeta.getMetadata(uuid, type);
    }

    public Long getCooldown(Player player, String type) {
        return getCooldown(player.getUniqueId(), type);
    }

    public Long getCooldownRemaining(UUID uuid, String type) {
        final Long cooldown = getCooldown(uuid, type);
        if (cooldown < 0) return -1L;
        return cooldown - Cooldown.getCurrentTime();
    }

    public Long getCooldownRemaining(Player player, String type) {
        return getCooldownRemaining(player.getUniqueId(), type);
    }

    public String getCooldownRemainingVerb(UUID uuid, String type) {
        return cooldown.getTimeString(getCooldownRemaining(uuid, type));
    }

    public String getCooldownRemainingVerb(Player player, String type) {
        return getCooldownRemainingVerb(player.getUniqueId(), type);
    }

    public boolean hasCooldown(UUID uuid, String type) {
        return UUIDMeta.containsData(uuid, type);
    }

}
