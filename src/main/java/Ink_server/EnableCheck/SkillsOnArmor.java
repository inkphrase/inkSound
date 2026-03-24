package Ink_server.EnableCheck;

import Ink_server.EntityTempData.BuffEffect;
import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public enum SkillsOnArmor {
    EAST_2_0("east20"){
        @Override
        public void enable(Player player) {
            EntityData playerData = EntityDataManager.getData(player);
            playerData.setBuffEffect(this.skillId, new BuffEffect("east20", 5, BuffEffect.BuffType.COOLS, -1));
        }
        @Override
        public void disable(Player player) {
            EntityData playerData = EntityDataManager.getData(player);
            playerData.getBuffEffectMap().remove(this.skillId);
        }
    };

    public final String skillId;
    public static final Map<String, SkillsOnArmor> ID_MAP = new HashMap<>();
    static {
        for (SkillsOnArmor skillsOnArmor : values()) {
            ID_MAP.put(skillsOnArmor.skillId, skillsOnArmor);
        }
    }

    SkillsOnArmor(String id) { this.skillId = id; }

    public static SkillsOnArmor getById(String id) { return ID_MAP.get(id); }

    public abstract void enable(Player player);

    public abstract void disable(Player player);

    public enum SeriesCheck{
        EAST_2_0("east20"){
            @Override
            public void check(SkillCheck skillCheck, String id){
                if (skillCheck.seriesCount.get(id) >= 2){
                    skillCheck.armorSkills.add(this.skillId);
                }
            }
        };

        public final String skillId;
        public static final Map<String, SeriesCheck> ID_MAP = new HashMap<>();
        static {
            for (SeriesCheck seriesCheck : values()) {
                ID_MAP.put(seriesCheck.skillId, seriesCheck);
            }
        }

        SeriesCheck(String id) { this.skillId = id; }

        public static SeriesCheck getById(String id) { return ID_MAP.get(id); }

        public abstract void check(SkillCheck skillCheck, String id);
    }
}
