##How to use CooldownAPI?

```java
import com.joaootavios.crystolnetwork.essentials.utils.CooldownAPI;import org.bukkit.event.block.BlockBreakEvent;

class Example extends Listener{

    private final CooldownAPI cooldownAPI = new CooldownAPI();
    
    // Exemplo de uso do CooldownAPI
    @EventHandler
    public void onBreakDelay(BlockBreakEvent e) {
        // Adiciona um delay de 10s para quebrar outro bloco.
        
        if (cooldownAPI.getCooldownRemaining(e.getPlayer().getUniqueId(), "quebroubloco") > 0){
            e.setCancelled(true); 
            e.getPlayer().sendMessage("Aguarde " + cooldownAPI.getCooldownRemaining(e.getPlayer().getUniqueId(), "quebroubloco") + " para quebrar um bloco novamente.");
            return;
        }

        cooldownAPI.setCooldown(e.getPlayer().getUniqueId(), "quebroubloco", 10);
    }

}
```
