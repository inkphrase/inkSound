package Ink_server.InkSoundFight;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.EntityTempData.Skills;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import Ink_server.StaticDatas.WeaponLib.WeaponRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类用于编写技能触发逻辑
 */
public class SkillEffectListener implements Listener {
    public static final String weaponCool = "weapon_cool";
    public static final String talent = "talent";
    public static final String talentCool = "talent_cool";
    public static final String talentAttack = "talent_attack";

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new  SkillEffectListener(), plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        var pdc = meta.getPersistentDataContainer();
        String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
        boolean enable = Boolean.TRUE.equals(pdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN));
        if (id == null || !enable) return;
        if (player.isSneaking()) {
            switch (player.getInventory().getHeldItemSlot()){
                case 0 -> {
                    UseSkills useSkills = UseSkills.getById(id);
                    if (useSkills == null) return;
                    useSkills.handle(player);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onShoot(EntityShootBowEvent event){
        if (!(event.getEntity() instanceof Player player))return;
        if (!player.isSneaking()) return;
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        var pdc = meta.getPersistentDataContainer();
        String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
        if (id == null) return;
        ShootSkills shootSkills = ShootSkills.getById(id);
        if (shootSkills == null) return;
        shootSkills.handle(id,player,arrow);

    }

    @EventHandler(ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event){
        var damager = event.getDamager();
        var target = event.getEntity();
        var damagerData = EntityDataManager.getData(damager);
        var targetData = EntityDataManager.getData(target);
        if (damagerData != null){
            for (Skills skills : damagerData.getSkillsMap().values()) {
                if (skills.getSkillType() == Skills.SkillType.ATTACK){
                    SkillAttack attack = SkillAttack.getById(skills.getId());
                    if (attack != null){
                        attack.handle(event);
                    }
                }
            }
        }
        if (targetData != null){
            for (Skills skills : targetData.getSkillsMap().values()) {
                if (skills.getSkillType() == Skills.SkillType.DEFENCE){
                    SkillDefence defence = SkillDefence.getById(skills.getId());
                    if (defence != null){
                        defence.handle(event);
                    }
                }
            }
        }

    }

    private enum UseSkills{
        SWORD1(WeaponRegistry.SWORD1){
            @Override
            public void handle(Player player){
                EntityData playerData = EntityDataManager.getData(player);
                if (playerData == null) return;
                var cools = playerData.cooldownMap;
                int current = Bukkit.getCurrentTick();
                Integer end = cools.get(weaponCool);
                if (end != null && end > current){
                    player.sendActionBar(Component.text("武器技冷却中  剩余: " + ((end - current) / 20) + " 秒")
                            .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                    return;
                }
                for (Entity entity : player.getNearbyEntities(5, 5, 5)){
                    if(!(entity instanceof LivingEntity living)) continue;
                    if (living instanceof Player) continue;
                    if (!TickTimer.isCombatant(living)) continue;
                    EntityData entityData = EntityDataManager.getData(living);
                    if (entityData == null) return;
                    living.setAI(false);
                    entityData.setSkillsMap("noAI", new Skills("noAI", null, -1, 60));
                    break;
                }
                Fight.setCool(playerData,weaponCool,200);
                player.sendActionBar(Component.text("武器技[镇邪] 已发动").color(NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
            }
        };

        private final String skillId;
        public static final Map<String, UseSkills> ID_MAP = new HashMap<>();
        static {
            for (UseSkills useSkills : values()) {
                ID_MAP.put(useSkills.skillId, useSkills);
            }
        }

        UseSkills(String id) { this.skillId = id; }

        private static UseSkills getById(String id) { return ID_MAP.get(id); }

        public abstract void handle(Player player);
    }

    private enum ShootSkills{
        BOW1(WeaponRegistry.BOW1){
            @Override
            public void handle(String id, Player player, Arrow arrow){
                EntityData playerData = EntityDataManager.getData(player);
                if (playerData == null) return;
                var cools = playerData.cooldownMap;
                int current = Bukkit.getCurrentTick();
                Integer end = cools.get(weaponCool);
                if (end != null && end > current){
                    player.sendActionBar(Component.text("武器技冷却中  剩余: " + ((end - current) / 20) + " 秒")
                            .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                    return;
                }
                var arrowPdc = arrow.getPersistentDataContainer();
                arrowPdc.set(ItemKeys.WEAPON_SKILL, PersistentDataType.STRING, id);
                Fight.setCool(playerData, weaponCool, 200);
                player.sendActionBar(Component.text("武器技[檀香] 已发动").color(NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true));
            }
        };

        private final String skillId;
        public static final Map<String, ShootSkills> ID_MAP = new HashMap<>();
        static {
            for (ShootSkills shootSkills : values()) {
                ID_MAP.put(shootSkills.skillId, shootSkills);
            }
        }

        ShootSkills(String id) { this.skillId = id; }

        private static ShootSkills getById(String id) { return ID_MAP.get(id); }

        public abstract void handle(String id, Player player, Arrow arrow);
    }

    //这里写职业天赋
    @EventHandler
    public void onSneaking(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if (!loc.getBlock().getRelative(BlockFace.DOWN).isSolid()) return;
        PlayerInfo info = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        EntityData playerData = EntityDataManager.getData(player);
        if (info == null || playerData == null) return;
        //弓箭手：一阶即可使用，双击下蹲获得后撤步
        if (info.getJob() == 1){
            if (!event.isSneaking()) return;
            var skills = playerData.getSkillsMap();
            int current = Bukkit.getCurrentTick();
            var cools = playerData.cooldownMap;
            Integer end = cools.get(talentCool);
            if (skills.containsKey(talent)){
                if (end != null && end > current) {
                    player.sendActionBar(Component.text("天赋能力冷却中  剩余: " + ((end - current) / 20) + " 秒")
                            .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                    return;
                }
                var direction = loc.getDirection().multiply(-1);
                direction.setY(0.2).normalize();
                player.setVelocity(direction.multiply(1.2));
                skills.remove(talent);
                cools.put(talentCool, 100 + current);
            }else {
                skills.put(talent, new Skills(talent, null, -1, 10));
            }

        }
        //战士：需解锁天赋，下蹲0.5秒内获得抵抗
        if (info.getJob() == 0) {
            var skills = playerData.getSkillsMap();
            if (!event.isSneaking()) {
                skills.remove(talent);
            } else {
                skills.put(talent, new Skills(talent, Skills.SkillType.DEFENCE, -1, 10));
            }
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event){
        if (event.getAction() != Action.LEFT_CLICK_BLOCK &&
        event.getAction() != Action.LEFT_CLICK_AIR) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        PlayerInfo info = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        if (info == null || info.getJob() != 1) return;
        int slot = player.getInventory().getHeldItemSlot();
        if (slot != 0) return;
        EntityData playerData = EntityDataManager.getData(player);
        int current = Bukkit.getCurrentTick();
        var cools = playerData.cooldownMap;
        Integer end = cools.get(talentCool);
        Location loc = player.getLocation();
        var skills = playerData.getSkillsMap();
        if (skills.containsKey(talent)){
            if (end != null && end > current) {
                player.sendActionBar(Component.text("天赋能力冷却中  剩余: " + ((end - current) / 20) + " 秒")
                        .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                return;
            }
            var direction = loc.getDirection().multiply(1);
            direction.setY(0.2).normalize();
            player.setVelocity(direction.multiply(1.2));
            skills.remove(talent);
            cools.put(talentCool, 100 + current);
        }else {
            skills.put(talent, new Skills(talent, null, -1, 10));
        }
    }
}
