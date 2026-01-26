package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.Skills;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;



public enum SkillDefence {
    TALENT(SkillEffectListener.talent){
        @Override
        public void handle(EntityDamageByEntityEvent event){
            if (event.isCancelled() || !(event.getEntity() instanceof Player player)) return;
            EntityData playerData = EntityDataManager.getData(player);
            if (playerData == null) return;
            int current = Bukkit.getCurrentTick();
            var cools = playerData.cooldownMap;
            Integer end = cools.get(SkillEffectListener.talentCool);
            if (end != null && end > current) {
                player.sendActionBar(Component.text("天赋能力冷却中  剩余: " + ((end - current) / 20) + " 秒")
                        .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                return;
            }
            event.setDamage(0);
            var direction = player.getLocation().getDirection().multiply(-1);
            direction.setY(0.2).normalize();
            player.setVelocity(direction.multiply(0.7));
            playerData.cooldownMap.put(SkillEffectListener.talentCool, Bukkit.getCurrentTick() + 200);
            player.sendActionBar(Component.text("天赋能力[格挡架势] 已发动").color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
            playerData.getSkillsMap().put(SkillEffectListener.talentAttack,
                    new Skills(SkillEffectListener.talentAttack, Skills.SkillType.ATTACK, -1,-1));
            playerData.getSkillsMap().remove(SkillEffectListener.talent);

        }
    };

    private final String skillId;
    public static final Map<String, SkillDefence> ID_MAP = new HashMap<>();
    static {
        for (SkillDefence skillDefence : values()) {
            ID_MAP.put(skillDefence.skillId, skillDefence);
        }
    }

    SkillDefence(String id) { this.skillId = id; }

    public static SkillDefence getById(String id) { return ID_MAP.get(id); }

    public abstract void handle(EntityDamageByEntityEvent event);
}
