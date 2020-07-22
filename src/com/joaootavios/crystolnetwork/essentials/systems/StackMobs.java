package com.joaootavios.crystolnetwork.essentials.systems;

import com.joaootavios.crystolnetwork.essentials.EssentialsPlugin;
import com.joaootavios.crystolnetwork.essentials.experienceapi.ExperienceAPI;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import rcore.specificutils.PlayerUtil;
import rcore.util.MakeItem;
import rcore.util.Sound;
import rcore.util.TXT;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StackMobs implements Listener {
    private final List<EntityType> validMobs = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.PIG_ZOMBIE,
            EntityType.SKELETON,
            EntityType.IRON_GOLEM,
            EntityType.BLAZE,
            EntityType.WITHER_SKULL,
            EntityType.WITHER,
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.CHICKEN,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMAN,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.SNOWMAN,
            EntityType.PIG
    );
    Random r = new Random();

    private final String getNameForType(EntityType type) {
        if (type == EntityType.GUARDIAN) {
            return TXT.parse("<b>Guardião");
        }
        if (type == EntityType.WITHER_SKULL) {
            return TXT.parse("<b>Esqueleto Wither");
        }
        if (type == EntityType.ZOMBIE) {
            return TXT.parse("<b>Zumbi");
        }
        if (type == EntityType.HORSE) {
            return TXT.parse("<b>Cavalo");
        }
        if (type == EntityType.CREEPER) {
            return TXT.parse("<b>Creeper");
        }
        if (type == EntityType.SPIDER) {
            return TXT.parse("<b>Aranha");
        }
        if (type == EntityType.SLIME) {
            return TXT.parse("<b>Slime");
        }
        if (type == EntityType.GHAST) {
            return TXT.parse("<b>Ghast");
        }
        if (type == EntityType.PIG_ZOMBIE) {
            return TXT.parse("<b>Homem-porco Zumbi");
        }
        if (type == EntityType.ENDERMAN) {
            return TXT.parse("<b>Enderman");
        }
        if (type == EntityType.CAVE_SPIDER) {
            return TXT.parse("<b>Aranha da Caverna");
        }
        if (type == EntityType.BLAZE) {
            return TXT.parse("<b>Blaze");
        }
        if (type == EntityType.MAGMA_CUBE) {
            return TXT.parse("<b>Cubo de Magma");
        }
        if (type == EntityType.BAT) {
            return TXT.parse("<b>Morcego");
        }
        if (type == EntityType.WITCH) {
            return TXT.parse("<b>Bruxa");
        }
        if (type == EntityType.SKELETON) {
            return TXT.parse("<b>Esqueleto");
        }
        if (type == EntityType.ENDERMITE) {
            return TXT.parse("<b>Endermite");
        }
        if (type == EntityType.PIG) {
            return TXT.parse("<b>Porco");
        }
        if (type == EntityType.SHEEP) {
            return TXT.parse("<b>Ovelha");
        }
        if (type == EntityType.COW) {
            return TXT.parse("<b>Vaca");
        }
        if (type == EntityType.CHICKEN) {
            return TXT.parse("<b>Galinha");
        }
        if (type == EntityType.SQUID) {
            return TXT.parse("<b>Lula");
        }
        if (type == EntityType.WOLF) {
            return TXT.parse("<b>Lobo");
        }
        if (type == EntityType.MUSHROOM_COW) {
            return TXT.parse("<b>Vaca-cogumelo");
        }
        if (type == EntityType.OCELOT) {
            return TXT.parse("<b>Ocelote");
        }
        if (type == EntityType.HORSE) {
            return TXT.parse("<b>Cavalo");
        }
        if (type == EntityType.RABBIT) {
            return TXT.parse("<b>Coelho");
        }
        if (type == EntityType.VILLAGER) {
            return TXT.parse("<b>Aldeão");
        }
        if (type == EntityType.SILVERFISH) {
            return TXT.parse("<b>Traça");
        }
        if (type == EntityType.IRON_GOLEM) {
            return TXT.parse("<b>Golem de Ferro");
        }
        if (type == EntityType.SNOWMAN) {
            return TXT.parse("<b>Boneco de Neve");
        }
        return TXT.parse("<b>" + type.name());
    }

    private final Material getDropForEntity(EntityType type) {
        if (type == EntityType.GUARDIAN) {
            return Material.PRISMARINE_CRYSTALS;
        }
        if (type == EntityType.WITHER_SKULL) {
            return Material.SKULL;
        }
        if (type == EntityType.ZOMBIE) {
            return Material.ROTTEN_FLESH;
        }
        if (type == EntityType.CREEPER) {
            return Material.SULPHUR;
        }
        if (type == EntityType.SPIDER) {
            return Material.STRING;
        }
        if (type == EntityType.SLIME) {
            return Material.SLIME_BALL;
        }
        if (type == EntityType.GHAST) {
            return Material.GHAST_TEAR;
        }
        if (type == EntityType.PIG_ZOMBIE) {
            return Material.GOLD_NUGGET;
        }
        if (type == EntityType.ENDERMAN) {
            return Material.ENDER_PEARL;
        }
        if (type == EntityType.CAVE_SPIDER) {
            return Material.SPIDER_EYE;
        }
        if (type == EntityType.BLAZE) {
            return Material.BLAZE_ROD;
        }
        if (type == EntityType.MAGMA_CUBE) {
            return Material.MAGMA_CREAM;
        }
        if (type == EntityType.BAT) {
            return Material.LEATHER;
        }
        if (type == EntityType.WITCH) {
            return Material.STICK;
        }
        if (type == EntityType.SKELETON) {
            return Material.BONE;
        }
        if (type == EntityType.ENDERMITE) {
            return Material.ENDER_PEARL;
        }
        if (type == EntityType.PIG) {
            return Material.PORK;
        }
        if (type == EntityType.SHEEP) {
            return Material.MUTTON;
        }
        if (type == EntityType.COW) {
            return Material.LEATHER;
        }
        if (type == EntityType.CHICKEN) {
            return Material.FEATHER;
        }
        if (type == EntityType.SQUID) {
            return Material.LEATHER;
        }
        if (type == EntityType.WOLF) {
            return Material.LEATHER;
        }
        if (type == EntityType.MUSHROOM_COW) {
            return Material.LEATHER;
        }
        if (type == EntityType.OCELOT) {
            return Material.LEATHER;
        }
        if (type == EntityType.HORSE) {
            return Material.LEATHER;
        }
        if (type == EntityType.RABBIT) {
            return Material.LEATHER;
        }
        if (type == EntityType.VILLAGER) {
            return Material.EMERALD;
        }
        if (type == EntityType.SILVERFISH) {
            return Material.LEATHER;
        }
        if (type == EntityType.IRON_GOLEM) {
            return Material.IRON_INGOT;
        }
        if (type == EntityType.SNOWMAN) {
            return Material.SNOW_BALL;
        }
        return Material.AIR;
    }

    private final void setAI(LivingEntity entity, boolean hasAi) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
        handle.getDataWatcher().watch(15, (byte) (hasAi ? 0 : 1));
    }

    private final int[] getMaterialAmount(final Chunk chunk) {
        final int[] amount = new int[Material.values().length];
        final int minX = chunk.getX() << 4;
        final int minZ = chunk.getZ() << 4;
        final int maxX = minX | 15;
        final int maxY = chunk.getWorld().getMaxHeight();
        final int maxZ = minZ | 15;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = 0; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    ++amount[chunk.getBlock(x, y, z).getType().ordinal()];
                }
            }
        }
        return amount;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (EssentialsPlugin.config.getBoolean("enable-stackmobs") == false) return;

        Entity en = e.getEntity();
        if (e.getLocation().getWorld().getName() == "mina" || e.getLocation().getWorld().getName() == "vip") {
            e.setCancelled(true);
        }

        CreatureSpawnEvent.SpawnReason r = e.getSpawnReason();
        if (r.equals(CreatureSpawnEvent.SpawnReason.NATURAL) || r.equals(CreatureSpawnEvent.SpawnReason.CHUNK_GEN)) {
            if (EssentialsPlugin.config.getBoolean("disable-natural-spawn-mobs") == true) {
                e.setCancelled(true);
                return;
            }
        }
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) {
            return;
        }

        if (!validMobs.contains(en.getType())) return;
        if (en.getCustomName() != null) return;
        for (Entity near : en.getNearbyEntities(10, 10, 10)) {
            if (near.getType().equals(en.getType())) {
                if (near.getCustomName() != null) {
                    if (near.getCustomName().contains(getNameForType(near.getType()))) {
                        int amount = Integer.parseInt(near.getCustomName().substring(2, near.getCustomName().indexOf("x")));
                        amount++;
                        if (amount <= EssentialsPlugin.config.getInt("stackmobs-limit")) {
                            e.getEntity().remove();
                            near.setCustomName(TXT.parse("<7>" + amount + "x " + getNameForType(near.getType())));
                            return;
                        }
                    }
                }
            }
        }
        if (!validMobs.contains(en.getType())) return;
        /*
        if (Mob.trapped.containsKey(e.getEntity().getLocation().getChunk())) {
            if (Mob.trapped.get(e.getEntity().getLocation().getChunk()).containsKey(e.getEntityType())) {
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    en.teleport(Mob.trapped.get(e.getEntity().getLocation().getChunk()).get(e.getEntityType()));
                }, 4);
            }
        }
        */
        setAI((LivingEntity) en, false);
        en.setCustomName(TXT.parse("<7>1x " + getNameForType(en.getType())));
    }

    @EventHandler
    public void onCreatureDeath(EntityDeathEvent e) {
        if (EssentialsPlugin.config.getBoolean("enable-stackmobs") == false) return;

        Entity en = e.getEntity();
        if (e.getEntity() == null) return;
        if (!validMobs.contains(en.getType())) return;
        if (en.getCustomName() == null) return;
        if (en.getCustomName().contains(getNameForType(en.getType()))) {
            Entity killer = e.getEntity().getKiller();
            if (killer != null && killer.getType() == EntityType.PLAYER) {
                Player victim = e.getEntity().getKiller();
                int amountlevel = 1;
                int mob_name = Integer.parseInt(en.getCustomName().substring(2, en.getCustomName().indexOf("x")));
                int[] getSpawner = getMaterialAmount(en.getLocation().getChunk());
                e.getDrops().clear();

                if (victim.isOnline() && victim.hasMetadata("1por1")) {
                    mob_name--;
                    en.remove();

                    if (mob_name == 1) { en.remove();return; }

                    if (ExperienceAPI.addXpAndIsUpped(victim, ExperienceAPI.LevelTypes.COMBATE, 1L)) {
                        victim.sendMessage(TXT.parse("&eVocê evoluiu um nível em Combate. [total: " + ExperienceAPI.getLevel(victim, ExperienceAPI.LevelTypes.COMBATE) + "]"));
                        PlayerUtil.playSound(victim, Sound.ENTITY_VILLAGER_YES, 1, 1);
                    }
                    PlayerUtil.sendActionBar(victim, "&eCombate: (" + ExperienceAPI.getXP(victim, ExperienceAPI.LevelTypes.COMBATE) + "/" + ExperienceAPI.getTotalXpRemaining(victim, ExperienceAPI.LevelTypes.COMBATE) + ") <e><l>+1 XP");

                    World mundo = en.getLocation().getWorld();
                    Entity criatura = mundo.spawnEntity(en.getLocation(), en.getType());
                    criatura.setCustomName(TXT.parse("<7>" + mob_name + "x " + getNameForType(en.getType())));
                    setAI((LivingEntity) criatura, false);

                    victim.giveExp(2);
                    en.getLocation().getWorld().dropItem(en.getLocation(), new MakeItem(getDropForEntity(en.getType())).setAmount(getSpawner[Material.MOB_SPAWNER.ordinal()] * 2).build());

                } else {
                    en.remove();
                    if (mob_name == 1) {
                        en.remove();
                        return;
                    }
                    Long valor = mob_name + 1L;
                    if (ExperienceAPI.addXpAndIsUpped(victim, ExperienceAPI.LevelTypes.COMBATE, valor)) {
                        victim.sendMessage(TXT.parse("&eVocê evoluiu um nível em Combate. [total: " + ExperienceAPI.getLevel(victim, ExperienceAPI.LevelTypes.COMBATE) + "]"));
                        PlayerUtil.playSound(victim, Sound.ENTITY_VILLAGER_YES, 1, 1);
                    }
                    PlayerUtil.sendActionBar(victim, "&eCombate: (" + ExperienceAPI.getXP(victim, ExperienceAPI.LevelTypes.COMBATE) + "/" + ExperienceAPI.getTotalXpRemaining(victim, ExperienceAPI.LevelTypes.COMBATE) + ") <e><l>+" + mob_name    + " XP");

                    ItemStack itemhand = victim.getItemInHand();
                    victim.giveExp(mob_name * 2);

                    boolean dropBonus = false;
                    if (r.nextInt(9) == 8) {
                        PlayerUtil.sendTitle(victim,"", "&e&lDROP BÔNUS", 5, 30, 5);
                        PlayerUtil.playSound(victim, Sound.ENTITY_VILLAGER_YES, 1, 1);
                        dropBonus = true;
                    }
                    if (itemhand.hasItemMeta() && itemhand.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_MOBS)){
                        int enchantLevel = victim.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                        amountlevel = r.nextInt(enchantLevel) + 3;
                    }

                    en.getLocation().getWorld().dropItem(en.getLocation(), new MakeItem(getDropForEntity(en.getType())).setAmount(mob_name * amountlevel * getSpawner[Material.MOB_SPAWNER.ordinal()] + 1 * (dropBonus ? 15 : 3)).build());
                }
            }
        }
    }
}
