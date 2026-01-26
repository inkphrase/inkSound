package Ink_server.EnableCheck;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.StaticDatas.WeaponLib.WeaponData;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.attribute.Attribute;

import java.util.Map;
import java.util.Objects;

public class AttributeCal {
    // 装备数据
    double attack_base_slot;
    double armor_base_slot;
    double health_base_slot;
    double attack_pers_slot;
    double armor_pers_slot;
    double health_pers_slot;
    double attack_final_slot;
    double armor_final_slot;
    double health_final_slot;
    double cools_slot;
    double speed_slot;
    double armor_break_slot;
    double env_defence_slot;
    double multiple_slot;
    double resistance_slot;
    double recycle_slot;
    double pierce_slot;

    static NamespacedKey enablekey = ItemKeys.ENABLE;
    static NamespacedKey id = ItemKeys.ID;

    public static final int[] slots = {0, 1, 5, 6, 7, 8, 36, 37, 38, 39, 40};
    public void calculate(Player player){
        //重置数值
        attack_base_slot     = 0.0;
        armor_base_slot      = 0.0;
        health_base_slot     = 0.0;

        attack_pers_slot     = 0.0;
        armor_pers_slot      = 0.0;
        health_pers_slot     = 0.0;

        attack_final_slot    = 0.0;
        armor_final_slot     = 0.0;
        health_final_slot    = 0.0;

        cools_slot           = 0.0;
        speed_slot           = 0.0;
        armor_break_slot     = 0.0;
        env_defence_slot     = 0.0;
        multiple_slot        = 0.0;
        resistance_slot      = 0.0;
        recycle_slot         = 0.0;
        pierce_slot          = 0.0;
        for (int i : slots){
            slotCal(player, i);
        }
        EntityData playerData = EntityDataManager.getData(player);
        if (playerData == null) {
            InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
            return;
        }
        playerData.attackBaseSlot   = attack_base_slot;
        playerData.armorBaseSlot    = armor_base_slot;
        playerData.healthBaseSlot   = health_base_slot;

        playerData.attackPersSlot   = attack_pers_slot;
        playerData.armorPersSlot    = armor_pers_slot;
        playerData.healthPersSlot   = health_pers_slot;

        playerData.attackFinalSlot  = attack_final_slot;
        playerData.armorFinalSlot   = armor_final_slot;
        playerData.healthFinalSlot  = health_final_slot;

        playerData.coolsSlot        = cools_slot;
        playerData.speedSlot        = speed_slot;

        playerData.armorBreakSlot   = armor_break_slot;
        playerData.envDefenceSlot   = env_defence_slot;

        playerData.multipleSlot     = multiple_slot;

        playerData.resistanceSlot   = resistance_slot;

        playerData.recycleSlot      = recycle_slot;
        playerData.pierceSlot       = pierce_slot;
    }

    private void slotCal(Player player, int i) {
        ItemStack item = player.getInventory().getItem(i);
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null)return;
        var pdc = meta.getPersistentDataContainer();
        if (pdc.has(enablekey)) {
            if (Objects.equals(pdc.get(enablekey, PersistentDataType.BOOLEAN), true)) {
                String weaponId = pdc.get(id, PersistentDataType.STRING);
                if (weaponId == null) return;
                WeaponData weaponDatas = WeaponManager.getWeaponLib().get(weaponId);
                if (weaponDatas == null) return;
                Map<String, Double> attributes = weaponDatas.getAttributes();
                if (attributes == null) return;
                if (attributes.getOrDefault(WeaponData.ENABLESLOT, -1.0) != i) return;
                attack_base_slot  += attributes.getOrDefault(WeaponData.ATTACKBASE, 0.0);
                armor_base_slot   += attributes.getOrDefault(WeaponData.ARMORBASE, 0.0);
                health_base_slot  += attributes.getOrDefault(WeaponData.HEALTHBASE, 0.0);

                attack_pers_slot  += attributes.getOrDefault(WeaponData.ATTACKPERS, 0.0);
                armor_pers_slot   += attributes.getOrDefault(WeaponData.ARMORPPERS, 0.0);
                health_pers_slot  += attributes.getOrDefault(WeaponData.HEALTHPERS, 0.0);

                attack_final_slot += attributes.getOrDefault(WeaponData.ATTACKFINAL, 0.0);
                armor_final_slot  += attributes.getOrDefault(WeaponData.ARMORFINAL, 0.0);
                health_final_slot += attributes.getOrDefault(WeaponData.HEALTHFINAL, 0.0);

                cools_slot        += attributes.getOrDefault(WeaponData.COOLS, 0.0);
                speed_slot        += attributes.getOrDefault(WeaponData.SPEED, 0.0);
                armor_break_slot  += attributes.getOrDefault(WeaponData.ARMORBREAK, 0.0);
                env_defence_slot  += attributes.getOrDefault(WeaponData.ENVDEFENCE, 0.0);
                multiple_slot     += attributes.getOrDefault(WeaponData.MULTIPLE, 0.0);
                resistance_slot   += attributes.getOrDefault(WeaponData.RESISTANCE, 0.0);
                recycle_slot      += attributes.getOrDefault(WeaponData.RECYCLE, 0.0);
                pierce_slot       += attributes.getOrDefault(WeaponData.PIERCE, 0.0);
            }
        }
    }

    public static void setSlotData(Player player){
        var datas = new AttributeCal();
        EntityData playerData = EntityDataManager.getData(player);
        if (playerData == null) {
            InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
            return;
        }
        datas.calculate(player);
        dataUpdate(player, playerData);
    }

    public static void setWhenBuff(LivingEntity entity) {
        EntityData entityData = EntityDataManager.getData(entity);
        if (entityData == null) return;

        dataUpdate(entity, entityData);
    }

    private static void dataUpdate(LivingEntity entity, EntityData entityData) {
        //缓存当前数据，用于判断是否改变
        double healthCache = entityData.getHealthData();
        double armorCache = entityData.getArmorData();
        double speedCache = entityData.getSpeed();
        double resistanceCache = entityData.getResistance();

        //属性重算
        entityData.calculateData();
         //属性发生变化，设置Attribute
        if (entity instanceof LivingEntity living) {
            if (isChanged(healthCache, entityData.getHealthData())) {
                Objects.requireNonNull(living.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(entityData.getHealthData());
            }
            if (isChanged(armorCache, entityData.getArmorData())) {
                Objects.requireNonNull(living.getAttribute(Attribute.ARMOR)).setBaseValue(entityData.getArmorData());
            }
            if (isChanged(speedCache, entityData.getSpeed())) {
                Objects.requireNonNull(living.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(0.1 * (1 + entityData.getSpeed() / 100));
            }
            if (isChanged(resistanceCache, entityData.getResistance())) {
                Objects.requireNonNull(living.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(entityData.getResistance());
            }
        }
    }

    private static boolean isChanged(double oldVal, double newVal) {
        return Math.abs(oldVal - newVal) > 0.01; // 变化超过 0.01 才认为真正改变了
    }
}
