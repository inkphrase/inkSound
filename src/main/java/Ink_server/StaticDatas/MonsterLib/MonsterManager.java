package Ink_server.StaticDatas.MonsterLib;

import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.EntityData;
import Ink_server.InkSoundFight.TickTimer;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MonsterManager {
    private static final InkSound plugin = InkSound.getInstance();
    private static final Map<String, MonsterData> monsterLib = new ConcurrentHashMap<>();

    // 生成怪物
    public static void spawnMonster(String id, Location location) {
        MonsterData monsterData = getMonsterLib().get(id);
        if (monsterData == null) return;
        if (location.getWorld() == null) return;

        // 生成实体
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(
                location,
                monsterData.getEntityType()
        );

        var pdc = entity.getPersistentDataContainer();
        pdc.set(ItemKeys.ID, PersistentDataType.STRING, id);

        // 加入怪物队伍
        Team monsterTeam = TickTimer.team;
        if (monsterTeam != null) {
            monsterTeam.addEntry(entity.getUniqueId().toString());
        }

        // 设置显示名称
        if (monsterData.getDisplayName() != null) {
            entity.customName(monsterData.getDisplayName());
            entity.setCustomNameVisible(true);
        }

        // 设置攻击力为0.001（保证有攻击能力）
        if (entity.getAttribute(Attribute.ATTACK_DAMAGE) != null) {
            Objects.requireNonNull(entity.getAttribute(Attribute.ATTACK_DAMAGE)).setBaseValue(0.001);
        }

        // 将怪物数据写入EntityData，属性会由TickTimer自动更新
        EntityData entityData = new EntityData();

        Map<String, Double> attributes = monsterData.getAttributes();

        // 直接穷举写入所有属性（性能最优，代码清晰）
        entityData.attackBaseSlot = attributes.getOrDefault(MonsterData.ATTACKBASE, 0.0);
        entityData.armorBaseSlot = attributes.getOrDefault(MonsterData.ARMORBASE, 0.0);
        entityData.healthBaseSlot = attributes.getOrDefault(MonsterData.HEALTHBASE, 100.0);
        entityData.attackPersBuff = attributes.getOrDefault(MonsterData.ATTACKPERS, 0.0);
        entityData.armorPersBuff = attributes.getOrDefault(MonsterData.ARMORPERS, 0.0);
        entityData.healthPersBuff = attributes.getOrDefault(MonsterData.HEALTHPERS, 0.0);
        entityData.speedSlot = attributes.getOrDefault(MonsterData.SPEED, 0.0);
        entityData.resistanceSlot = attributes.getOrDefault(MonsterData.RESISTANCE, 0.0);
        entityData.armorBreakSlot = attributes.getOrDefault(MonsterData.ARMORBREAK, 0.0);

        //写入实体管理系统
        EntityDataManager.addEntity(entity.getUniqueId(), entityData);

        //设置基本参数与技能
        MonsterStats ms = MonsterStats.getById(id);
        if (ms != null){
            ms.handle(entity);
        }else {
            plugin.getLogger().warning("怪物 " + id + " 属性未配置!");
        }

        // 立即更新一次属性（之后TickTimer会自动维护）
        TickTimer.dataUpdate(entity, entityData);

        if (entity.getAttribute(Attribute.MAX_HEALTH) != null) {
            entity.setHealth(Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getBaseValue());
        }

    }

    public static Map<String, MonsterData> getMonsterLib() {
        return Collections.unmodifiableMap(monsterLib);
    }

    public static MonsterData getMonster(String id) {
        return monsterLib.get(id);
    }

    public static void addMonster(MonsterData monsterData) {
        monsterLib.put(monsterData.getId(), monsterData);
    }
}