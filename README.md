# CrystolEssentials
Um plugin essencial para o funcionamento do servidor.

## Sumário
* [Exemplo CooldownAPI](#uso-da-cooldownapi)
* [Exemplo ExperienceAPI](#uso-da-experienceapi)

## Uso da CooldownAPI
```java
public class Exemplo extends Listener{

    private final CooldownAPI cooldownAPI = new CooldownAPI();
    
    // Exemplo de uso do CooldownAPI
    @EventHandler
    public void onBreakDelay(BlockBreakEvent e) {
        // Adiciona um delay de 10s para quebrar outro bloco.
        
        final Player player = e.getPlayer();
        final UUID uuid = e.getPlayer().getUniqueId();

        if (cooldownAPI.getCooldownRemaining(uuid, "quebroubloco") > 0){
            e.setCancelled(true); 
            player.sendMessage("Aguarde " + cooldownAPI.getCooldownRemaining(uuid, "quebroubloco") + " para quebrar um bloco novamente.");
            return;
        }

        cooldownAPI.setCooldown(uuid, "quebroubloco", 10L); // é LONG e não INT
    }

}
```

## Uso da ExperienceAPI
```java
public class Exemplo extends Listener {

    // Exemplo de uso do ExperienceAPI
    @EventHandler
    public void onBreakXP(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        
        // Verificando se o evento foi cancelado.
        if (!e.isCancelled()) {

            // Verifica se o cara está no gamemode survival (evita abusos)
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                if (ExperienceAPI.addXpAndIsUpped(player, ExperienceAPI.LevelTypes.MINERAÇÃO, 1L)) {
                    player.sendMessage("Você evoluiu um nível em Mineração. [total: " + ExperienceAPI.getLevel(player, ExperienceAPI.LevelTypes.MINERAÇÃO) + "]");
                }

                player.sendMessage("Mineração: (" + ExperienceAPI.getXP(player, ExperienceAPI.LevelTypes.MINERAÇÃO) + "/" + ExperienceAPI.getTotalXpRemaining(player, ExperienceAPI.LevelTypes.MINERAÇÃO) + ") <e><l>+1 XP");
                if (ExperienceAPI.getLevel(player, ExperienceAPI.LevelTypes.MINERAÇÃO) > 100L) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10, 1));
                }
                // Recompensa de exemplo >> 100 leveis Mineração = HASTE.
            }
        }
    }
}
```

## Licença
[MIT](https://choosealicense.com/licenses/mit/)
