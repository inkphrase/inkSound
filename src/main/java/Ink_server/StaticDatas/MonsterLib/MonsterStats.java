package Ink_server.StaticDatas.MonsterLib;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.Skills;
import Ink_server.InkSound;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum MonsterStats {
    EAST_ZOMBIE_2(MonsterRegistry.EAST_ZOMBIE_2){
        @Override
        public void handle(LivingEntity entity){
            if (!(entity instanceof Zombie zombie)) return;
            zombie.setAdult();
            zombie.setCanBreakDoors(false);
            zombie.setCanPickupItems(false);
            zombie.getEquipment().setArmorContents(new ItemStack[4]);
            zombie.getEquipment().setItemInMainHand(null);
            zombie.getEquipment().setItemInOffHand(null);
        }
    },
    EAST_SKELETON_2(MonsterRegistry.EAST_SKELETON_2){
        @Override
        public void handle(LivingEntity entity){
            if (!(entity instanceof Skeleton skeleton))return;
            skeleton.setCanPickupItems(false);
            skeleton.getEquipment().setArmorContents(new ItemStack[4]);
            skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
            skeleton.getEquipment().setItemInOffHand(null);
        }
    },
    EAST_GHOSTBONE_2(MonsterRegistry.EAST_GHOSTBONE_2){
        @Override
        public void handle(LivingEntity entity){
            if (!(entity instanceof Stray stray)) return;
            stray.setCanPickupItems(false);
            stray.getEquipment().setArmorContents(new ItemStack[4]);
            stray.getEquipment().setItemInMainHand(null);
            stray.getEquipment().setItemInOffHand(null);
            EntityData strayData = EntityDataManager.getData(stray);
            if (strayData == null) {
                InkSound.getInstance().getLogger().warning("怪物数据没配置");
                return;
            }
            strayData.setSkillsMap(MonsterRegistry.EAST_GHOSTBONE_2, new Skills(
                    MonsterRegistry.EAST_GHOSTBONE_2, Skills.SkillType.PASSIVE,
                    200, -1));
        }
    };

    private final String id;
    private static final Map<String, MonsterStats> monsters = new HashMap<>();
    MonsterStats(String id){
        this.id = id;
    }
    static {
        for (MonsterStats monsterStats : MonsterStats.values()) {
            monsters.put(monsterStats.id, monsterStats);
        }
    }
    public static MonsterStats getById(String id){
        return monsters.get(id);
    }

    public String getId() {
        return id;
    }

    public abstract void handle(LivingEntity entity);
}
