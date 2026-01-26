package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.EntityData;
import Ink_server.StaticDatas.MonsterLib.MonsterRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Stray;

import java.util.HashMap;
import java.util.Map;

public enum SkillsPassive {

    /**
     * 这里是怪物技能
     */
    EAST_GHOSTBONE_2(MonsterRegistry.EAST_GHOSTBONE_2){
        @Override
        public void handle(LivingEntity entity, EntityData entityData){
            if (!(entity instanceof Stray)) return;
            for (Player player : entity.getLocation().getNearbyPlayers(8)){
                if(!player.isDead()){
                    Location location = player.getLocation();
                    var yaw = player.getLocation().getYaw();
                    var x = location.getX();
                    var z = location.getZ();
                    x = x - Math.sin(Math.toRadians(yaw));
                    z = z + Math.cos(Math.toRadians(yaw));
                    location.setX(x);
                    location.setZ(z);
                    location.setYaw(180 - yaw);
                    location.setPitch(0);
                    entity.teleport(location);
                    player.sendMessage(Component.text("魔骨潜入幽影，突然袭击至你面前").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
                    break;
                }
            }
        }
    };


    private final String skillId;
    public static final Map<String, SkillsPassive> ID_MAP = new HashMap<>();
    static {
        for (SkillsPassive skillsPassive : values()) {
            ID_MAP.put(skillsPassive.skillId, skillsPassive);
        }
    }

    SkillsPassive(String id) { this.skillId = id; }

    public static SkillsPassive getById(String id) { return ID_MAP.get(id); }

    public abstract void handle(LivingEntity entity, EntityData entityData);
}
