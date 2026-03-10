package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.DotEffect;
import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.Skills;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 此类用于重写伤害逻辑，大多数非技能伤害都写入此类统一管理
 */
public class Fight implements Listener {
    private static final InkSound plugin = InkSound.getInstance();

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new Fight(), plugin);
        plugin.getLogger().info("伤害系统已初始化！");
    }

    private Fight(){}

    //伤害监听器，放到HIGHEST，忽略取消，用于重写伤害逻辑
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity))return;
        entity.setNoDamageTicks(0);
        entity.setMaximumNoDamageTicks(0);

        //取消所有非实体间伤害事件，遇到特殊的dot类，交给dot管理器处理
        cancelEffect(event);

        if (event.isCancelled()) return;

        //这里判断事件是否是实体间互相伤害，交给战斗系统编写逻辑
        if (!(event instanceof EntityDamageByEntityEvent entityEvent)) return;
        Entity damager = entityEvent.getDamager();
        Entity target = entityEvent.getEntity();

        EntityDamageByEntityEvent.DamageCause cause = entityEvent.getCause();

        //这里专门处理打击
        if (cause == EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK){
            if (!(target instanceof LivingEntity targetL) || !(damager instanceof LivingEntity damagerL)) return;
            //取消含有非战斗实体的事件
            if (!TickTimer.isCombatant(targetL) || !TickTimer.isCombatant(damagerL)) {
                event.setCancelled(true);
                return;
            }
            EntityData damagerData = EntityDataManager.getData(damager);
            EntityData targetData = EntityDataManager.getData(target);
            if (damagerData == null || targetData == null) {
                event.setCancelled(true);
                return;
            }
            //这里专门处理玩家打怪物
            if (damager instanceof Player player && !(target instanceof Player)){
                UUID damagerUUID = damager.getUniqueId();
                PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(damagerUUID);
                if (playerInfo == null) {
                    event.setCancelled(true);
                    return;
                }
                if (player.getAttackCooldown() < 0.95f) {
                    event.setDamage(0);
                    return; // 武器还在冷却中，拒绝攻击
                }
                //判断战士、主手槽位
                if (player.getInventory().getHeldItemSlot() != 0 || playerInfo.getJob() != 0) {
                    event.setDamage(0);
                    return;
                }
                //判断激活
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                //判断已完全装载
                if (mainHand.getType() == Material.AIR || !mainHand.hasItemMeta()) {
                    event.setDamage(0);
                    return;
                }
                var pdc = mainHand.getItemMeta().getPersistentDataContainer();
                boolean enable = Boolean.TRUE.equals(pdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN));
                if (!enable) {
                    event.setDamage(0);
                    return;
                }
                String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
                if (id == null) {
                    event.setDamage(0);
                    return;
                }

                double multiple = damagerData.getMultiple();
                double attack = damagerData.getAttackData();
                double armorBreak = damagerData.getArmorBreak();
                double armor = targetData.getArmorData();
                double damage = damageCal(attack, armor, armorBreak, 1 + multiple);
                event.setDamage(Math.max(damage, 0));
            }
            //这里专门处理怪物打玩家
            else if (target instanceof Player player && !(damager instanceof Player)){
                UUID targetUUID = player.getUniqueId();
                PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(targetUUID);
                if (playerInfo == null) {
                    event.setCancelled(true);
                    return;
                }
                double attack = damagerData.getAttackData();
                double armorBreak = damagerData.getArmorBreak();
                double armor = targetData.getArmorData();
                double damage = damageCal(attack, armor,armorBreak , 1);
                event.setDamage(Math.max(damage, 0));
            }
            //这里处理 PVP 为战役预留空间
            else if (damager instanceof Player playerDamager){
                Player playerTarget = (Player) target;
                UUID damagerUUID = playerDamager.getUniqueId();
                UUID targetUUID = playerTarget.getUniqueId();
                PlayerInfo damagerInfo = PlayerInfoManager.getPlayerInfo(damagerUUID);
                PlayerInfo targetInfo = PlayerInfoManager.getPlayerInfo(targetUUID);
                if (damagerInfo == null || targetInfo == null){
                    event.setCancelled(true);
                    return;
                }
                double attack = damagerData.getAttackData();
                double armorBreak = damagerData.getArmorBreak();
                double armor = targetData.getArmorData();
                double damage = damageCal(attack, armor, armorBreak, 1);
                event.setDamage(Math.max(damage, 0));
            }
            else event.setDamage(0);
        }

        //这里专门处理弹射物
        if (cause == EntityDamageByEntityEvent.DamageCause.PROJECTILE){
            //取消含有非战斗实体的事件
            if (!(damager instanceof Projectile projectile)) {
                event.setCancelled(true);
                return;
            }
            if (!(projectile.getShooter() instanceof LivingEntity shooter)) {
                event.setCancelled(true);
                return;
            }
            if (!(target instanceof LivingEntity targetL)){
                event.setCancelled(true);
                return;
            }
            if (!TickTimer.isCombatant(targetL) || !TickTimer.isCombatant(shooter)) {
                event.setCancelled(true);
                return;
            }
            if (!(entityEvent.getDamager() instanceof Arrow arrow)) {
                event.setCancelled(true);
                return;
            }

            //处理玩家被射击
            if (target instanceof Player player){
                PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
                if (playerInfo == null){
                    event.setCancelled(true);
                    return;
                }
                EntityData entityData = EntityDataManager.getData(player);
                if (entityData == null){
                    plugin.getLogger().warning("玩家 " + player.getName() + " 被战斗系统忽略!");
                    event.setCancelled(true);
                    return;
                }
                var pdc = arrow.getPersistentDataContainer();
                double attack = pdc.getOrDefault(ItemKeys.ATTACK, PersistentDataType.DOUBLE, 0.0);
                double armorBreak = pdc.getOrDefault(ItemKeys.ARMORBREAK, PersistentDataType.DOUBLE, 0.0);
                double multiple = pdc.getOrDefault(ItemKeys.MULTIPLE, PersistentDataType.DOUBLE, 0.0);
                double armor = entityData.getArmorData();
                double force = pdc.getOrDefault(ItemKeys.FORCE, PersistentDataType.DOUBLE, 0.0);
                attack = attack * Math.sqrt(force);
                double damage = damageCal(attack, armor, armorBreak, 2 + multiple);
                event.setDamage(Math.max(damage, 0));
            }
            else {
                EntityData entityData = EntityDataManager.getData(target);
                if (entityData == null){
                    event.setCancelled(true);
                    return;
                }
                var pdc = arrow.getPersistentDataContainer();
                double attack = pdc.getOrDefault(ItemKeys.ATTACK, PersistentDataType.DOUBLE, 0.0);
                double armorBreak = pdc.getOrDefault(ItemKeys.ARMORBREAK, PersistentDataType.DOUBLE, 0.0);
                double multiple = pdc.getOrDefault(ItemKeys.MULTIPLE, PersistentDataType.DOUBLE, 0.0);
                double armor = entityData.getArmorData();
                double force = pdc.getOrDefault(ItemKeys.FORCE, PersistentDataType.DOUBLE, 0.0);
                attack = attack * Math.sqrt(force);
                double damage = damageCal(attack, armor, armorBreak, 2 + multiple);
                event.setDamage(Math.max(damage, 0));
            }
        }

        //这里处理横扫
        if (cause == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK){
            if (!(target instanceof LivingEntity targetL) || !(damager instanceof LivingEntity damagerL)) return;
            //取消含有非战斗实体的事件
            if (!TickTimer.isCombatant(targetL) || !TickTimer.isCombatant(damagerL)) {
                event.setCancelled(true);
                return;
            }
            EntityData damagerData = EntityDataManager.getData(damager);
            EntityData targetData = EntityDataManager.getData(target);
            if (damagerData == null || targetData == null) {
                event.setCancelled(true);
                return;
            }
            //这里专门处理玩家打怪物
            if (damager instanceof Player player && !(target instanceof Player)){
                UUID damagerUUID = damager.getUniqueId();
                PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(damagerUUID);
                if (playerInfo == null) {
                    event.setCancelled(true);
                    return;
                }
                //判断战士、主手槽位
                if (player.getInventory().getHeldItemSlot() != 0 || playerInfo.getJob() != 0) {
                    event.setDamage(0);
                    return;
                }
                //判断激活
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                if (mainHand.getType() == Material.AIR || !mainHand.hasItemMeta()) {
                    event.setDamage(0);
                    return;
                }
                var pdc = mainHand.getItemMeta().getPersistentDataContainer();
                boolean enable = Boolean.TRUE.equals(pdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN));
                if (!enable) {
                    event.setDamage(0);
                    return;
                }
                String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
                if (id == null) {
                    event.setDamage(0);
                    return;
                }

                double multiple = damagerData.getMultiple();
                double attack = damagerData.getAttackData();
                double armorBreak = damagerData.getArmorBreak();
                double armor = targetData.getArmorData();
                double damage = damageCal(attack, armor, armorBreak, 0.5 + multiple / 2);
                event.setDamage(Math.max(damage, 0));
            }
        }
    }

    @EventHandler
    public void onBowing(PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)return;
        Player player = event.getPlayer();
        ItemStack mainHand = event.getItem();
        if (mainHand == null)return;
        if (mainHand.getType() != Material.BOW && mainHand.getType() != Material.CROSSBOW) return;
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        if (playerInfo == null) {
            event.setCancelled(true);
            return;
        }
        //判断职业和槽位
        if (player.getInventory().getHeldItemSlot() != 0 || playerInfo.getJob() != 1) {
            event.setCancelled(true);
            return;
        }
        if (!mainHand.hasItemMeta()) {
            event.setCancelled(true);
            return;
        }
        var pdc = mainHand.getItemMeta().getPersistentDataContainer();
        boolean enable = Boolean.TRUE.equals(pdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN));
        if (!enable) {
            event.setCancelled(true);
        }
    }

    //这个监听器处理实体射箭行为
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onShoot(EntityShootBowEvent event){
        LivingEntity entity = event.getEntity();
        //这里处理玩家，进行射击合法性检测
        if (entity instanceof Player player && event.getProjectile() instanceof Arrow arrow){
            PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
            if (playerInfo == null) {
                event.setCancelled(true);
                return;
            }
            //判断职业和槽位
            if (player.getInventory().getHeldItemSlot() != 0 || playerInfo.getJob() != 1) {
                event.setCancelled(true);
                return;
            }
            //判断激活
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if (mainHand.getType() == Material.AIR || !mainHand.hasItemMeta()) {
                event.setCancelled(true);
                return;
            }
            var pdc = mainHand.getItemMeta().getPersistentDataContainer();
            boolean enable = Boolean.TRUE.equals(pdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN));
            if (!enable) {
                event.setCancelled(true);
                return;
            }
            EntityData playerData = EntityDataManager.getData(player);
            if (playerData == null) {
                plugin.getLogger().severe("玩家 " + player.getUniqueId() + " 被战斗系统忽略!");
                event.setCancelled(true);
                return;
            }
            double multiple = playerData.getMultiple();
            double attack = playerData.getAttackData();
            double armorBreak = playerData.getArmorBreak();
            double force = event.getForce();
            int pierce = (int)playerData.getPierce();
            var arrowPdc = arrow.getPersistentDataContainer();
            arrowPdc.set(ItemKeys.ATTACK, PersistentDataType.DOUBLE, attack);
            arrowPdc.set(ItemKeys.ARMORBREAK, PersistentDataType.DOUBLE, armorBreak);
            arrowPdc.set(ItemKeys.MULTIPLE, PersistentDataType.DOUBLE, multiple);
            arrowPdc.set(ItemKeys.FORCE, PersistentDataType.DOUBLE, force);
            arrow.setPierceLevel(pierce);
            setShootSkill(playerData, arrowPdc);
        }
        //处理其他怪物
        else if (!(entity instanceof Player) && TickTimer.isCombatant(entity) && event.getProjectile() instanceof Arrow arrow) {
            EntityData entityData = EntityDataManager.getData(entity);
            if (entityData == null) {
                event.setCancelled(true);
                return;
            }
            double multiple = entityData.getMultiple();
            double attack = entityData.getAttackData();
            double armorBreak = entityData.getArmorBreak();
            var arrowPdc = arrow.getPersistentDataContainer();
            int pierce = (int)entityData.getPierce();
            arrowPdc.set(ItemKeys.ATTACK, PersistentDataType.DOUBLE, attack);
            arrowPdc.set(ItemKeys.ARMORBREAK, PersistentDataType.DOUBLE, armorBreak);
            arrowPdc.set(ItemKeys.MULTIPLE, PersistentDataType.DOUBLE, multiple);
            arrowPdc.set(ItemKeys.FORCE, PersistentDataType.DOUBLE, 1.0);
            arrow.setPierceLevel(pierce);
            setShootSkill(entityData, arrowPdc);
        }
        else event.setCancelled(true);
    }

    /**
     * 这里用于写入被动箭矢技能
     */
    private static void setShootSkill(EntityData entityData, PersistentDataContainer arrowPdc) {
        List<String> shootSkills = new ArrayList<>();
        for (Map.Entry<String, Skills> shootSkill : entityData.getSkillsMap().entrySet()){
            if (shootSkill.getValue().getSkillType() == Skills.SkillType.SHOOT){
                shootSkills.add(shootSkill.getKey());
            }
        }
        arrowPdc.set(ItemKeys.SKILLS, PersistentDataType.STRING, String.join(",",shootSkills));
    }

    //这里处理箭矢技能
    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        var pdc = arrow.getPersistentDataContainer();
        String skillString = pdc.get(ItemKeys.SKILLS, PersistentDataType.STRING);
        if (skillString == null) return;
        for (String skillId : skillString.split(",")) {
            SkillArrowHit skillArrowHit = SkillArrowHit.getById(skillId);
            if (skillArrowHit == null) continue;
            skillArrowHit.handle(event);
        }
        String weaponSkill = pdc.get(ItemKeys.WEAPON_SKILL, PersistentDataType.STRING);
        SkillArrowHit skillArrowHit = SkillArrowHit.getById(weaponSkill);
        if (skillArrowHit == null) return;
        skillArrowHit.handle(event);
    }

    private void cancelEffect(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        switch (cause) {
            // ========== 完全取消的伤害类型 ==========
            case CONTACT          -> event.setCancelled(true);  // 仙人掌等接触伤害
            case BLOCK_EXPLOSION  -> event.setCancelled(true);  // TNT等方块爆炸
            case ENTITY_EXPLOSION -> event.setCancelled(true);  // 苦力怕等实体爆炸
            case LIGHTNING        -> event.setCancelled(true);  // 雷击伤害
            case FALLING_BLOCK    -> event.setCancelled(true);  // 铁砧等下落方块
            case THORNS           -> event.setCancelled(true);  // 荆棘附魔反伤
            case FLY_INTO_WALL    -> event.setCancelled(true);  // 鞘翅撞墙
            case SONIC_BOOM       -> event.setCancelled(true);  // 监守者音爆
            case CAMPFIRE         -> event.setCancelled(true);  // 营火伤害 - 取消

            // ========== 转换为 DOT 的伤害类型 ==========
            case FIRE        -> handleFire(event);       // 火焰方块 → 火焰DOT
            case FIRE_TICK   -> handleFireTick(event);   // 着火    → 火焰DOT
            case LAVA        -> handleLava(event);       // 岩浆    → 高额火焰DOT
            case STARVATION  -> handleStarvation(event); // 饥饿    → 饥饿DOT
            case POISON      -> handlePoison(event);     // 中毒    → 中毒DOT
            case WITHER      -> handleWither(event);     // 凋零    → 凋零DOT
            case FREEZE      -> handleFreeze(event);     // 冰冻    → 冰冻DOT

            // ========== 保留原版逻辑的伤害类型 ==========
            case ENTITY_ATTACK       -> {} // 实体普通攻击 - 战斗系统处理
            case ENTITY_SWEEP_ATTACK -> {} // 横扫攻击 - 战斗系统处理
            case PROJECTILE          -> {} // 弹射物 - 战斗系统处理
            case SUFFOCATION         -> {} // 窒息 - 保留原版
            case FALL                -> {} // 摔落 - 保留原版
            case DROWNING            -> {} // 溺水 - 保留原版
            case VOID                -> {} // 虚空 - 保留原版
            case SUICIDE             -> {} // /kill命令 - 保留原版
            case MAGIC               -> {} // 魔法/瞬间伤害药水 - 保留原版
            case CUSTOM              -> {} // 自定义伤害 - 保留，插件内部使用
            case HOT_FLOOR           -> {} // 岩浆块 - 保留原版（小伤害，影响操作）
            case CRAMMING            -> {} // 实体挤压 - 保留原版
            case DRYOUT              -> {} // 干涸（海豚等） - 保留原版
            case KILL                -> {} // /kill命令 - 保留原版
            case WORLD_BORDER        -> {} // 世界边界 - 保留原版

            // ========== 默认处理 ==========
            default -> {
                // 如果有未处理的新伤害类型，记录警告
                if (event.getEntity() instanceof org.bukkit.entity.Player) {
                    plugin.getLogger().warning(
                            "未处理的伤害类型: " + cause + " - 实体: " + event.getEntity().getType()
                    );
                }
            }
        }
    }

    /**
     * 这里是dot伤害，在有dot效果的怪物攻击玩家时，会在给予状态时添加dot
     * 添加时会判定一次伤害量，覆盖较小者，下面的保底处理也是这样
     */
    //火焰方块，单次
    private void handleFire(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect fire = entityData.getDotEffectMap().get("fire");
        if (fire == null) {
            //增加一个微小但时间较长的火焰dot，伤害交由TickTimer判断
            fire = new DotEffect("fire", 1,
                    DotEffect.DotType.DAMAGE, 20,19
                    , null);
            fire.counter = 19;
            entityData.setDotEffect("fire", fire);
        }
        event.getEntity().setFireTicks(2);
        event.setCancelled(true);
    }

    //着火伤害，添加火焰dot
    private void handleFireTick(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect fire = entityData.getDotEffectMap().get("fire");
        if (fire == null) {
            //增加一个微小但时间较长的火焰dot，伤害交由TickTimer判断
            fire = new DotEffect("fire", 1,
                    DotEffect.DotType.DAMAGE, 20,200
                    , null);
            entityData.setDotEffect("fire", fire);
        }
        event.setCancelled(true);//取消这次火焰事件，伤害会交由tick执行
    }

    //岩浆，添加高额火焰dot
    private void handleLava(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect fire = entityData.getDotEffectMap().get("fire");
        double lavaAmount = 10;
        if (fire == null || fire.getAmount() < lavaAmount) {
            //增加一个严重且时间较长的火焰dot，伤害交由TickTimer判断
            fire = new DotEffect("fire", lavaAmount,
                    DotEffect.DotType.DAMAGE, 20,219
                    , null);
            fire.counter = 19;
            entityData.setDotEffect("fire", fire);
        }
        event.getEntity().setFireTicks(100);
        event.setCancelled(true);
    }

    //饥饿，添加饥饿dot
    private void handleStarvation(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect starvation = entityData.getDotEffectMap().get("starvation");
        if (starvation == null || starvation.getAmount() < 20) {
            starvation = new DotEffect("starvation", 20,
                    DotEffect.DotType.DAMAGE, 5, -1, null);
            //饥饿永不过期，只在检测到玩家并不饥饿时手动移除
            entityData.setDotEffect("starvation", starvation);
        }
        event.setCancelled(true);
    }

    //中毒，添加中毒dot
    private void handlePoison(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect poison = entityData.getDotEffectMap().get("poison");
        if (poison == null || poison.getAmount() < 1) {
            poison = new DotEffect("poison", 1,
                    DotEffect.DotType.DAMAGE, 25, 224, null);
            poison.counter = 24;
            entityData.setDotEffect("poison", poison);
        }
        event.setCancelled(true);
    }

    //凋零，添加凋零dot
    private void handleWither(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect wither = entityData.getDotEffectMap().get("wither");
        if (wither == null || wither.getAmount() < 2) {
            wither = new DotEffect("wither", 2,
                    DotEffect.DotType.DAMAGE, 40, 239, null);
            wither.counter = 39;
            entityData.setDotEffect("wither", wither);
        }
        event.setCancelled(true);
    }

    //冰冻，给冰冻dot
    private void handleFreeze(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;
        DotEffect freeze = entityData.getDotEffectMap().get("freeze");
        if (freeze == null || freeze.getAmount() < 1) {
            freeze = new DotEffect("freeze", 1,
                    DotEffect.DotType.DAMAGE, 19, 219, null);
            freeze.counter = 19;
            entityData.setDotEffect("freeze", freeze);
        }
        event.setCancelled(true);
    }

    //这个函数写入了技能伤害，伤害前标记实体，防止技能伤害重复检验
    //对目标实体造成伤害
    public static void doDamage(LivingEntity damager, LivingEntity target, double multiple) {
        if (!TickTimer.isCombatant(damager) || !TickTimer.isCombatant(target)) return ;
        EntityData damagerData = EntityDataManager.getData(damager);
        EntityData targetData = EntityDataManager.getData(target);
        if (damagerData == null || targetData == null) return;
        double attack = damagerData.getAttackData();
        double armorBreak = damagerData.getArmorBreak();
        double armor = targetData.getArmorData();
        double damage = damageCal(attack, armor, armorBreak, multiple);
        if (damage <= 0) return;
        target.damage(damage);
        if (damager instanceof Player player) {
            target.setKiller(player);
        }
    }

    //游戏统一的伤害公式
    public static double damageCal(double attack, double armor, double armorBreak, double multiple) {
        return (attack * 50 / (50 + armor - armorBreak)) * multiple + attack * 0.1;
    }

    public static double simpleDamageCal(LivingEntity damager, LivingEntity target) {
        if (!(TickTimer.isCombatant(damager) || !(TickTimer.isCombatant(target)))) return 0;
        EntityData damagerData = EntityDataManager.getData(damager);
        EntityData targetData = EntityDataManager.getData(target);
        double attack = damagerData.getAttackData();
        double armorBreak = damagerData.getArmorBreak();
        double armor = targetData.getArmorData();
        double multiple = damagerData.getMultiple();
        return (attack * 50 / (50 + armor - armorBreak)) * (1 + multiple) + attack * 0.1;
    }

    public static void setCool(EntityData playerData, String coolId, int cool) {
        double cools = playerData.getCools();
        cools = Math.min(50,cools);
        int yang = playerData.YANG == 9 ? 10 : playerData.YANG;
        double coolLimit = 1 - (cools + yang) / 100;
        playerData.cooldownMap.put(coolId, (int) (Bukkit.getCurrentTick() + (cool * coolLimit)));
    }

    //技能结束提示
    //注册冷却结束提示 临时
    public static final Map<String, Component> coolDownEndTip = new ConcurrentHashMap<>();
    static {
        final Component metal = Component.text("金元素阵法冷却结束").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC,false);
        coolDownEndTip.put("metal", metal);
        final Component weaponSkill = Component.text("武器技冷却结束").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC,false);
        coolDownEndTip.put(SkillEffectListener.weaponCool, weaponSkill);
        final Component talent = Component.text("天赋能力冷却结束").color(NamedTextColor.GREEN).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC,false);
        coolDownEndTip.put(SkillEffectListener.talentCool, talent);
    }
}
