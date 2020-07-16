#CrystolEssentials

## Sumário
* [Exemplo CooldownAPI](#uso-da-cooldownapi)
* [Exemplo_ExperienceAPI](#uso-da-experienceapi)

##Uso da CooldownAPI
```java
import com.joaootavios.crystolnetwork.essentials.utils.CooldownAPI;
import org.bukkit.event.block.BlockBreakEvent;
import java.util.UUID;

class Example extends Listener{

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

        cooldownAPI.setCooldown(uuid, "quebroubloco", 10);
    }

}
```

##Uso da ExperienceAPI
```java
    
```

## Licença
[MIT](https://choosealicense.com/licenses/mit/)
