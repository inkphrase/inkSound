package Ink_server.InkSoundFight;

import Ink_server.EconomySystem.AdventureAssociation;
import Ink_server.EconomySystem.DeliveryStation;
import Ink_server.EconomySystem.GlobalData;
import Ink_server.EnableCheck.AttributeCal;
import Ink_server.EntityTempData.*;
import Ink_server.InkSound;
import Ink_server.StaticDatas.Factions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import java.util.Objects;
import java.util.UUID;

public class TickTimer {
    private static final InkSound plugin = InkSound.getInstance();
    public static final Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(Factions.MONSTERS);

    private final BukkitTask task;

    private int tickCount = 0;
    private int cleanCount = 0;
    private static final int REPORT_INTERVAL = 1200;

    public TickTimer(){
        plugin.getLogger().info("数据更新任务已启动！");
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tickUpdate, 0L, 1L);
    }

    private void tickUpdate() {
        //计时
        tickCount++;
        cleanCount++;
        int currentTick = Bukkit.getCurrentTick();
        long startTime = System.currentTimeMillis();
        Boolean shouldUpdateDelivery = DeliveryStation.shouldUpdate();
        Boolean shouldUpdateAdventure = AdventureAssociation.shouldUpdate();
        String time = GlobalData.getDay() + " " + GlobalData.getHour();
        if (shouldUpdateDelivery){
            plugin.getLogger().info("跑商更新，时间：" + currentTick);
            plugin.getServer().broadcast(Component.text("=================")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                                 .append(Component.text("皇城镖局")
                                         .color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC,false))
                                 .append(Component.text("=================")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("乾坤流转，万象更新。商贸之事，民生之本。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("然四海商路多不平。艰难险阻，路途崎岖。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("崇山峻岭，常有狰狞妖物环伺，辎重难输。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("怪石嶙峋，各地州府民生凋敝，积货难运。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("兹尔皇城镖局，承押送之委托，利万民之生计。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("今特发此榜，号召天下身手不凡之游侠志士。")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("凡能护送货物平安抵境者，加官进爵，重金酬谢！")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("险峻之道，方查民生困苦；危难之际，方显英雄本色！")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("皇城镖局 " + time + " 宣")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))
                    .append(Component.newline())
                                 .append(Component.text("========================================")
                                         .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))

            );
        }
        World mainWorld = Bukkit.getWorld("world");
        if (mainWorld == null) return;
        for (LivingEntity entity : mainWorld.getLivingEntities()) {
            if (!isCombatant(entity)) {
                if (cleanCount >= 100){
                    EntityDataManager.cleanupEntity(entity);
                }
                continue;
            }
            UUID uuid = entity.getUniqueId();
            if (!entity.isValid() || entity.isDead()) {
                if (team != null) {
                    team.removeEntries(uuid.toString());
                }
                EntityDataManager.removeData(uuid);
                continue;
            }
            EntityData entityData = EntityDataManager.getData(entity);
            if (entityData == null) continue;
            // 处理冷却提示 (仅针对玩家)
            if (entity instanceof Player player) {
                if (!entityData.cooldownMap.isEmpty()) {
                    for (var entry : entityData.cooldownMap.entrySet()) {
                        if (entry.getValue() != null && entry.getValue() != 0 && entry.getValue() == currentTick) {
                            Component msg = Fight.coolDownEndTip.get(entry.getKey());
                            if (msg != null) player.sendActionBar(msg);
                        }
                    }
                }
                //这里更新跑商的时间
                if (shouldUpdateDelivery){
                    DeliveryStation.deliveryUpdate(player);
                }
                if (shouldUpdateAdventure){
                    AdventureAssociation.adventureUpdate(player);
                }
            }
            dataUpdate(entity, entityData);
        }
        long endTime = System.currentTimeMillis();  // 结束计时
        long duration = endTime - startTime;
        if (duration > 10) {
            plugin.getLogger().warning("任务处理时间过长: " + duration + " ms");
        }
        if (tickCount >= REPORT_INTERVAL) {
            plugin.getLogger().info("数据更新任务 定时汇报 本次处理耗时: " + duration + " ms");
            tickCount = 0;
        }
        if (cleanCount >= 100) cleanCount = 0;
    }

    public static void dataUpdate(LivingEntity entity, EntityData entityData) {
        var skillMap = entityData.getSkillsMap();
        if (skillMap.containsKey("noAI")){
            if (skillMap.get("noAI").isExpired()){
                entity.setAI(true);
            }
        }
        entityData.getDotEffectMap().values().removeIf(DotEffect::isExpired);
        entityData.getBuffEffectMap().values().removeIf(BuffEffect::isExpired);
        skillMap.values().removeIf(Skills::isExpired);
        if (!entityData.getDotEffectMap().isEmpty()) {
            dotTickUpdate(entity ,entityData);
        }
        buffTickUpdate(entity, entityData);
        skillTickUpdate(entity, entityData);
    }

    public void disable(){
        if (task != null){
            task.cancel();
            plugin.getLogger().info("数据更新任务已关闭！");
        }
    }

    public static void dotTickUpdate(LivingEntity entity, EntityData entityData) {
        // 使用迭代器安全删除
        var iterator = entityData.getDotEffectMap().entrySet().iterator();

        while (iterator.hasNext()) {
            var entry = iterator.next();
            String dotName = entry.getKey();
            DotEffect dot = entry.getValue();

            switch (dot.getDotType()){
                case DAMAGE -> {
                    // 检查 DOT 是否应该继续存在
                    boolean shouldRemove = false;

                    switch (dotName) {
                        case "fire" -> {
                            // 检查实体是否还在着火状态
                            if (entity.getFireTicks() <= 0) {
                                shouldRemove = true;
                            } else {
                                // 执行火焰 DOT 伤害
                                applyEnvironmentDamage(entity, dot, entityData);
                            }
                        }
                        case "freeze" -> {
                            // 检查实体是否还在冰冻状态
                            if (entity.getFreezeTicks() <= 0) {
                                shouldRemove = true;
                            } else {
                                applyEnvironmentDamage(entity, dot, entityData);
                            }
                        }
                        case "poison" -> {
                            // 检查实体是否还有中毒效果
                            if (entity instanceof LivingEntity living) {
                                if (!living.hasPotionEffect(org.bukkit.potion.PotionEffectType.POISON)) {
                                    shouldRemove = true;
                                } else {
                                    applyEnvironmentDamage(entity, dot, entityData);
                                }
                            }
                        }
                        case "wither" -> {
                            // 检查实体是否还有凋零效果
                            if (entity instanceof LivingEntity living) {
                                if (!living.hasPotionEffect(org.bukkit.potion.PotionEffectType.WITHER)) {
                                    shouldRemove = true;
                                } else {
                                    applyEnvironmentDamage(entity, dot, entityData);
                                }
                            }
                        }
                        case "starvation" -> {
                            // 检查玩家是否还饥饿
                            if (entity instanceof Player player) {
                                if (player.getFoodLevel() > 0) {
                                    shouldRemove = true;
                                } else {
                                    applyEnvironmentDamage(entity, dot, entityData);
                                }
                            }
                        }
                        default ->
                            // 其他 DOT 直接执行伤害，依赖过期时间
                            applyDotDamage(entity, dot);
                    }

                    // 使用迭代器安全移除
                    if (shouldRemove) {
                        iterator.remove();
                    }
                }
                case HEAL ->  {
                    if (entity instanceof LivingEntity living) {
                        if (dot.shouldEffect()){
                            living.heal(dot.getAmount());
                        }
                    }
                }
            }


        }
    }

    /**
     * 应用 DOT 伤害
     */
    private static void applyDotDamage(LivingEntity entity, DotEffect dot) {
        if (!(entity instanceof LivingEntity living)) return;

        // 检查是否到了执行伤害的时间（根据 interval）
        if (dot.shouldEffect()) {
            LivingEntity effector = dot.getEffector();
            living.damage(dot.getAmount());
            if (effector != null && effector.isValid() && effector instanceof Player player) {
                // 如果有施加者且为玩家，记录伤害来源
                living.setKiller(player);
            }
        }
    }
    /**
     * 环境伤害，可被装备能力抵挡
     */
    private static void applyEnvironmentDamage(LivingEntity entity, DotEffect dot, EntityData entityData) {
        if (!(entity instanceof LivingEntity living)) return;

        // 检查是否到了执行伤害的时间
        if (dot.shouldEffect()) {
            double envDefence = 0;
            if (entity instanceof  Player player){
                if (Objects.equals(dot.getId(), "fire")){
                    player.playSound(entity, Sound.ENTITY_PLAYER_HURT_ON_FIRE, 1.0f, 1.0f);
                }
                envDefence = entityData.getEnvDefence();
            }
            living.damage(dot.getAmount() * (1 - Math.max(envDefence, 0.8)));
        }
    }


    /**
     * 本方法用于更新buff数据，任何增加buff的行为均建议调用
     */
    public static void buffTickUpdate(LivingEntity entity, EntityData entityData){
        //重新计算属性加成
        entityData.clearBuff();
        for (BuffEffect buffEffect : entityData.getBuffEffectMap().values()) {
            switch (buffEffect.getBuffType()){
                case ATTACK_BASE ->  entityData.attackBaseBuff  += buffEffect.getAmount();
                case ARMOR_BASE  ->  entityData.armorBaseBuff   += buffEffect.getAmount();
                case HEALTH_BASE ->  entityData.healthBaseBuff  += buffEffect.getAmount();
                case ATTACK_PERS ->  entityData.attackPersBuff  += buffEffect.getAmount();
                case ARMOR_PERS  ->  entityData.armorPersBuff   += buffEffect.getAmount();
                case HEALTH_PERS ->  entityData.healthPersBuff  += buffEffect.getAmount();
                case ATTACK_FINAL->  entityData.attackFinalBuff += buffEffect.getAmount();
                case ARMOR_FINAL ->  entityData.armorFinalBuff  += buffEffect.getAmount();
                case HEALTH_FINAL->  entityData.healthFinalBuff += buffEffect.getAmount();
                case COOLS       ->  entityData.coolsBuff       += buffEffect.getAmount();
                case SPEED       ->  entityData.speedBuff       += buffEffect.getAmount();
            }
        }
        AttributeCal.setWhenBuff(entity);
    }

    private static void skillTickUpdate(LivingEntity entity, EntityData entityData){
        for (java.util.Map.Entry<String, Skills> entry : entityData.getSkillsMap().entrySet()) {
            String skillId = entry.getKey();
            Skills skill = entry.getValue();
            if (skill.getSkillType() == Skills.SkillType.PASSIVE) {
                if (skill.shouldEffect()) {
                    SkillsPassive skillsPassive = SkillsPassive.getById(skillId);
                    if (skillsPassive != null) {
                        skillsPassive.handle(entity, entityData);
                    }
                }
            }
        }

    }

    public static boolean isCombatant(LivingEntity entity) {
        if (entity instanceof Player) return true;
        return EntityDataManager.hasData(entity.getUniqueId());
    }
}

