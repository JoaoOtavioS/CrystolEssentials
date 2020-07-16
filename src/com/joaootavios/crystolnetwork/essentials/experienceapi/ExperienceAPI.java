package com.joaootavios.crystolnetwork.essentials.experienceapi;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import org.bukkit.OfflinePlayer;
import rcore.util.GsonManager;
import java.io.File;
import java.util.*;

public class ExperienceAPI {

    public static int levelPorcent = 60; //Porcentagem por nível..
    public static int topAmount = 10;
    public static Long levelUP = 1020L; //Requerimento de XP : Padrão -> 1020L
    public static Long levelLimit = 1000L; //Nível máximo.

    public static HashMap<UUID, PlayerExperience> playersExperiences = new HashMap<>();

    public static PlayerExperience get(UUID uuid){
        PlayerExperience pe;
        if (playersExperiences.containsKey(uuid))
            pe = playersExperiences.get(uuid);
        else {
            pe = new PlayerExperience(uuid);
            playersExperiences.put(uuid, pe);
            pe.load();
        }
        return pe;
    }

    public static void setXP(OfflinePlayer player, LevelTypes type, Long xp) {
        setXP(player.getUniqueId(), type, xp);
    }

    public static void setXP(UUID uuid, LevelTypes type, Long xp) {
        get(uuid).setExperience(type, xp);
    }

    public static void addXP(OfflinePlayer player, LevelTypes type, Long xp) {
        addXP(player.getUniqueId(), type, xp);
    }

    public static void addXP(UUID uuid, LevelTypes type, Long xp) {
        get(uuid).addExperience(type, xp);
    }

    public static Long getXP(OfflinePlayer player, LevelTypes type) {
        return getXP(player.getUniqueId(), type);
    }

    public static Long getXP(UUID uuid, LevelTypes type) {
        return get(uuid).getExperience(type);
    }

    public static void setLevel(OfflinePlayer player, LevelTypes type, Long levels) {
        setLevel(player.getUniqueId(), type, levels);
    }

    public static void setLevel(UUID uuid, LevelTypes type, Long levels) {
        get(uuid).setLevel(type, levels);
    }

    public static Long getLevel(OfflinePlayer player, LevelTypes type) {
        return getLevel(player.getUniqueId(), type);
    }

    public static Long getLevel(UUID uuid, LevelTypes type) {
        return get(uuid).getLevel(type);
    }

    public static Long getTotalXP(OfflinePlayer player) {
        return getTotalXP(player.getUniqueId());
    }

    public static Long getTotalXP(UUID uuid) {
        return get(uuid).getTotalExperience();
    }

    public static Long getXpRemaining() {
        return ((levelUP * levelPorcent) / 100);
    }

    public static Long getTotalXpRemaining(OfflinePlayer player, LevelTypes type) {
        return getTotalXpRemaining(player.getUniqueId(), type);
    }

    public static Long getTotalXpRemaining(UUID uuid, LevelTypes type) {
        return get(uuid).getExperienceLevelUp(type);
    }

    public static Long getTotalLevels(OfflinePlayer player) {
        return getTotalLevels(player.getUniqueId());
    }

    public static Long getTotalLevels(UUID uuid) {
        return get(uuid).getTotalLevels();
    }

    public static boolean addXpAndIsUpped(OfflinePlayer player, LevelTypes type, Long xp){
        return addXpAndIsUpped(player.getUniqueId(), type, xp);
    }

    public static boolean addXpAndIsUpped(UUID uuid, LevelTypes type, Long xp){
        Long beforeLevel = getLevel(uuid, type);
        addXP(uuid, type, xp);
        Long afterLevel = getLevel(uuid, type);
        return (afterLevel > beforeLevel);
    }

    public static HashMap<Integer, PlayerExperience> getTopsLevel(LevelTypes type){
        return getTopsLevel(type, 1, topAmount);
    }

    public static HashMap<Integer, PlayerExperience> getTopsLevel(LevelTypes type, int start, int stop){
        List<PlayerExperience.ExperienceComparator> comparables = new ArrayList<>();
        HashMap<Integer, PlayerExperience> tops = new HashMap<>();
        playersExperiences.values().forEach(it -> {
            PlayerExperience.ExperienceComparator comparator = new PlayerExperience.ExperienceComparator(it.getUUID(), it.getExperience(type));
            comparables.add(comparator);
        });
        Collections.sort(comparables);
        int i = start;
        for (PlayerExperience.ExperienceComparator comp : comparables){
            if (i > stop)
                break;
            UUID uuid = comp.getUUID();
            tops.put(i, get(uuid));
            i++;
        }
        return tops;
    }

    public static HashMap<Integer, PlayerExperience> getTopsTotalLevel(){
        return getTopsTotalLevel(1, topAmount);
    }

    public static HashMap<Integer, PlayerExperience> getTopsTotalLevel(int start, int stop){
        List<PlayerExperience.ExperienceComparator> comparables = new ArrayList<>();
        HashMap<Integer, PlayerExperience> tops = new HashMap<>();
        playersExperiences.values().forEach(it -> {
            PlayerExperience.ExperienceComparator comparator = new PlayerExperience.ExperienceComparator(it.getUUID(), it.getTotalExperience());
            comparables.add(comparator);
        });
        Collections.sort(comparables);
        int i = start;
        for (PlayerExperience.ExperienceComparator comp : comparables){
            if (i > stop)
                break;
            UUID uuid = comp.getUUID();
            tops.put(i, get(uuid));
            i++;
        }
        return tops;
    }

    public static Integer getRank(OfflinePlayer player) {
        return getRank(player, 1, topAmount);
    }

    public static Integer getRank(UUID uuid) {
        return getRank(uuid, 1, topAmount);
    }

    public static Integer getRank(OfflinePlayer player, int start, int stop) {
        return getRank(player.getUniqueId(), start, stop);
    }

    public static Integer getRank(UUID uuid, int start, int stop) {
        List<PlayerExperience.ExperienceComparator> comparables = new ArrayList<>();
        HashMap<UUID, Integer> tops = new HashMap<>();
        playersExperiences.values().forEach(it -> {
            PlayerExperience.ExperienceComparator comparator = new PlayerExperience.ExperienceComparator(it.getUUID(), it.getTotalExperience());
            comparables.add(comparator);
        });
        Collections.sort(comparables);
        Iterator<PlayerExperience.ExperienceComparator> ecs = comparables.iterator();
        int i = start;
        for (PlayerExperience.ExperienceComparator comp : comparables){
            if (i > stop)
                break;
            UUID uid = comp.getUUID();
            tops.put(uid, i);
            i++;
        }
        int rank = -1;
        if (tops.containsKey(uuid))
            rank = tops.get(uuid);
        return rank;
    }

    public static void loadAll(){
        GsonManager gson = new GsonManager(EssentialsPlugin.getPlugin(EssentialsPlugin.class).getDataFolder() + "/DataInfos", "");
        File[] files = gson.getFiles();
        if (files != null){
            for(File file : Arrays.asList(files)){
                String uuidString = file.getName().replace(".json", "").replace("-datainfo", "");
                UUID uuid = UUID.fromString(uuidString);
                ExperienceAPI.get(uuid);
            }
        }
    }

    public static List<LevelTypes> getLevelTypes(){
        return Arrays.asList(LevelTypes.values());
    }

    public enum LevelTypes {
        COMBATE,
        MINERAÇÃO,
        HERBALISMO
    }

}