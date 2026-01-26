package Ink_server.NPCs;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.NPCID;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public class NPCHandler implements Listener {

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new NPCHandler(), plugin);
    }

    private NPCHandler(){}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof Villager villager))return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.NETHERITE_HOE) return;
        String id = villager.getPersistentDataContainer().get(ItemKeys.ID, PersistentDataType.STRING);
        NPCRegistry npc = NPCRegistry.getById(id);
        if (id == null || !NPCID.NPCMap.containsKey(id) || npc == null) return;
        npc.handle(player, villager);
    }

    @EventHandler
    public void onEntityLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            if (entity instanceof Villager villager) {
                // 强制清空并重新设置你的固定交易，防止原版逻辑覆盖
                villager.setRecipes(Collections.emptyList()); // 先清空
                // 确保它不会因为重启恢复了 AI
                villager.setAI(false);
            }
        }
    }
}
