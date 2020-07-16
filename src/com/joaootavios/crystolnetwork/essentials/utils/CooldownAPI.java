package com.joaootavios.crystolnetwork.essentials.utils;

import rcore.util.Cooldown;

import java.util.UUID;

public class CooldownAPI {

    private void setCooldown(UUID uuid, String type, Long time) {
        UUIDMeta.setMetadata(uuid, type, Cooldown.getCurrentTime() + time);
    }

    private Long getCooldown(UUID uuid, String type) {
        if (!hasCooldown(uuid, type)) return -1L;
        return (Long) UUIDMeta.getMetadata(uuid, type);
    }

    private Long getCooldownRemaining(UUID uuid, String type){
        final Long cooldown = getCooldown(uuid, type);
        if (cooldown < 0) return -1L;
        return cooldown - Cooldown.getCurrentTime();
    }

    private boolean hasCooldown(UUID uuid, String type) {
        return UUIDMeta.containsData(uuid, type);
    }

}
