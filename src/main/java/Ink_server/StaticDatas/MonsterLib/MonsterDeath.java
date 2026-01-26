package Ink_server.StaticDatas.MonsterLib;

import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.InkSoundFight.TickTimer;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class MonsterDeath implements Listener {
    public static void init() {
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new MonsterDeath(), plugin);
        plugin.getLogger().info("怪物掉落已重写");
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisappear(EntityRemoveFromWorldEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity living)) return;
        if (!TickTimer.isCombatant(living)) return;
        EntityDataManager.removeData(living.getUniqueId());
        if (TickTimer.team != null) {
            TickTimer.team.removeEntry(living.getUniqueId().toString());
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!TickTimer.isCombatant(entity)) return;
        var drops = event.getDrops();
        drops.clear();
        Entity killer = entity.getKiller();
        if (killer != null){
            PlayerInfoManager.getPlayerInfo(killer.getUniqueId()).addKillNum();
        }
        var pdc = entity.getPersistentDataContainer();
        String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
        MonsterDrop md = MonsterDrop.getById(id);
        if (md != null) {
            md.handle(drops);
        }
    }
}
