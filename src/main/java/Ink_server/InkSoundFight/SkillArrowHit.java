package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.Skills;
import Ink_server.StaticDatas.WeaponLib.WeaponRegistry;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类用作编写箭矢命中时执行的技能逻辑
 */
public enum SkillArrowHit {
    BOW1(WeaponRegistry.BOW1){
        @Override
        public void handle(ProjectileHitEvent event){
            if (event.getHitEntity() == null || !(event.getHitEntity() instanceof  LivingEntity entity)) return;
            if (entity instanceof Player) return;
            EntityData entityData = EntityDataManager.getData(entity);
            if (entityData == null) return;
            entity.setAI(false);
            entityData.setSkillsMap("noAI", new Skills("noAI", null, -1, 60));
        }
    };

    private final String skillId;
    public static final Map<String, SkillArrowHit> ID_MAP = new HashMap<>();
    static {
        for (SkillArrowHit skillArrowHit : values()) {
            ID_MAP.put(skillArrowHit.skillId, skillArrowHit);
        }
    }

    SkillArrowHit(String id) { this.skillId = id; }

    public static SkillArrowHit getById(String id) { return ID_MAP.get(id); }

    public abstract void handle(ProjectileHitEvent event);
}
