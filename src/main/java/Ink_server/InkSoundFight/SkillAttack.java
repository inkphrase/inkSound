package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.EntityDataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public enum SkillAttack {
    TALENT_ATTACK(SkillEffectListener.talentAttack){
        @Override
        public void handle(EntityDamageByEntityEvent event){
            var damager = event.getDamager();
            if (damager.isOnGround()) return;
            var target = event.getEntity();
            if (!(damager instanceof LivingEntity damagerL) ||
            !(target instanceof LivingEntity targetL)) return;
            var damagerData = EntityDataManager.getData(damagerL);
            var targetData = EntityDataManager.getData(targetL);
            if (damagerData == null || targetData == null) return;
            damagerData.getSkillsMap().remove(SkillEffectListener.talentAttack);
            Fight.doDamage(damagerL,targetL,3);
            damager.sendActionBar(Component.text("天赋能力[格挡反击] 已发动").color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
        }
    };

    private final String skillId;
    public static final Map<String, SkillAttack> ID_MAP = new HashMap<>();
    static {
        for (SkillAttack skillAttack : values()) {
            ID_MAP.put(skillAttack.skillId, skillAttack);
        }
    }

    SkillAttack(String id) { this.skillId = id; }

    public static SkillAttack getById(String id) { return ID_MAP.get(id); }

    public abstract void handle(EntityDamageByEntityEvent event);
}
