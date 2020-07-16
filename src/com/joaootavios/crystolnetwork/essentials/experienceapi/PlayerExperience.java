package com.joaootavios.crystolnetwork.essentials.experienceapi;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.Serializable;
import java.util.*;

public class PlayerExperience implements Cloneable, Serializable {

    public static class ExperienceComparator implements Comparable<ExperienceComparator>{

        private UUID uuid;
        private Long experience;

        public ExperienceComparator(UUID uuid, Long experience){
            this.uuid = uuid;
            this.experience = experience;
        }

        public UUID getUUID() { return uuid; }

        public Long getExperience() { return experience; }

        @Override
        public int compareTo(ExperienceComparator comparable) {
            if (getExperience() < comparable.getExperience()){
                return 1;
            }
            if (getExperience() > comparable.getExperience()){
                return -1;
            }
            return 0;
        }
    }

    private UUID uuid;
    private HashMap<ExperienceAPI.LevelTypes, Long> experiences = new HashMap<>();
    private PlayerDataInfo playerDataBuilder = new PlayerDataInfo();
    private PlayerDataInfo.DataInfo playerData;

    public PlayerExperience(OfflinePlayer player){
        this.uuid = player.getUniqueId();
        playerData = playerDataBuilder.select(uuid);
    }
    public PlayerExperience(UUID uuid){
        this.uuid = uuid;
        playerData = playerDataBuilder.select(this.uuid);
    }

    public UUID getUUID() { return uuid; }

    public OfflinePlayer getPlayer() { return Bukkit.getOfflinePlayer(uuid); }

    public void setExperience(ExperienceAPI.LevelTypes type, Long experience) {
        Long exp = experience;
        if (exp < 0L)
            exp = 0L;
        experiences.replace(type, exp);
        playerData.set("XP-" + type.name(), exp).save();
    }

    public void addExperience(ExperienceAPI.LevelTypes type, Long experience){
        setExperience(type, getExperience(type) + experience);
    }

    public void removeExperience(ExperienceAPI.LevelTypes type, Long experience){
        setExperience(type, getExperience(type) - experience);
    }

    public Long getExperience(ExperienceAPI.LevelTypes type) {
        return experiences.get(type);
    }

    public Long getTotalExperience() {
        Long exp = 0L;
        Iterator<Long> experiences = this.experiences.values().iterator();
        while (experiences.hasNext())
            exp += experiences.next();
        return exp;
    }

    public void setLevel(ExperienceAPI.LevelTypes type, Long levels) {
        Long lvls = levels;
        if (lvls >= ExperienceAPI.levelLimit)
            lvls = ExperienceAPI.levelLimit;
        Long xp = ExperienceAPI.levelUP;
        if (lvls > 1)
            xp = ExperienceAPI.levelUP + ((ExperienceAPI.levelUP + ExperienceAPI.getXpRemaining()) * lvls);
        setExperience(type, xp);
    }

    public Long getLevel(ExperienceAPI.LevelTypes type) {
        Long xp = getExperience(type);
        Long level = 0L;
        if (xp > ExperienceAPI.levelUP) {
            level++;
            xp -= ExperienceAPI.levelUP;
        }
        level += xp / (ExperienceAPI.levelUP + ExperienceAPI.getXpRemaining());
        return (level >= ExperienceAPI.levelLimit ? ExperienceAPI.levelLimit : level);
    }

    public Long getTotalLevels() {
        Long levels = 0L;
        Iterator<ExperienceAPI.LevelTypes> levelsTypes = Arrays.asList(ExperienceAPI.LevelTypes.values()).iterator();
        while (levelsTypes.hasNext())
            levels += getLevel(levelsTypes.next());
        return levels;
    }

    public Long getExperienceLevelUp(ExperienceAPI.LevelTypes type) {
        Long level = getLevel(type);
        Long remaining = ExperienceAPI.levelUP;
        if (level > 0)
            remaining = ExperienceAPI.levelUP + ((ExperienceAPI.getXpRemaining() + ExperienceAPI.levelUP) * level);
        return remaining;
    }

    public void load(){
        Iterator<ExperienceAPI.LevelTypes> levelsTypes = Arrays.asList(ExperienceAPI.LevelTypes.values()).iterator();
        while (levelsTypes.hasNext()) {
            ExperienceAPI.LevelTypes type = levelsTypes.next();
            experiences.put(type, 0L);
            if (playerData.contains("XP-" + type.name())){
                experiences.replace(type, playerData.get("XP-" + type.name()).get(0).asLong());
            }
        }
    }

}

