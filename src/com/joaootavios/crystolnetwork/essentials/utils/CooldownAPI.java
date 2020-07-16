package com.joaootavios.crystolnetwork.essentials.utils;

import rcore.util.Cooldown;

import java.util.UUID;

public class CooldownAPI {

    public void setCooldown(UUID uuid, String type, Long time) {
        UUIDMeta.setMetadata(uuid, type, Cooldown.getCurrentTime() + time);
    }

    public Long getCooldown(UUID uuid, String type) {
        if (!hasCooldown(uuid, type)) return -1L;
        return (Long) UUIDMeta.getMetadata(uuid, type);
    }

    public Long getCooldownRemaining(UUID uuid, String type) {
        final Long cooldown = getCooldown(uuid, type);
        if (cooldown < 0) return -1L;
        return cooldown - Cooldown.getCurrentTime();
    }

    public boolean hasCooldown(UUID uuid, String type) {
        return UUIDMeta.containsData(uuid, type);
    }

}
