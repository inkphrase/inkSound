package Ink_server.StaticDatas.MonsterLib;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MonsterData {
    // 怪物基础数据
    private final String id;
    private final EntityType entityType;
    private final Component displayName;
    private final Map<String, Double> attributes = new HashMap<>();

    // 初始化怪物属性名常量
    // 攻击生命护甲
    public static final String ATTACKBASE = "attackbase";
    public static final String ATTACKPERS = "attackpers";
    public static final String HEALTHBASE = "healthbase";
    public static final String HEALTHPERS = "healthpers";
    public static final String ARMORBASE = "armorbase";
    public static final String ARMORPERS = "armorpers";

    // 移动速度击退抗性破甲
    public static final String SPEED = "speed";
    public static final String RESISTANCE = "resistance";
    public static final String ARMORBREAK = "armorbreak";


    public MonsterData(String id, EntityType entityType, Component displayName) {
        this.id = id;
        this.entityType = entityType;
        this.displayName = displayName;
    }

    // 设置怪物属性
    public void setAttribute(String key, double value) {
        attributes.put(key, value);
    }

    // 获取怪物数据
    public String getId() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Map<String, Double> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}