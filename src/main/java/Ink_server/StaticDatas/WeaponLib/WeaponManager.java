package Ink_server.StaticDatas.WeaponLib;

import Ink_server.StaticDatas.ItemKeys;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static Ink_server.StaticDatas.ItemKeys.ATTACKSPEED;

public class WeaponManager {
    private static final Map<String, WeaponData> weaponLib = new ConcurrentHashMap<>();

    // 构建武器
    public static ItemStack weaponBuild(String id) {
        WeaponData weaponDatas = getWeaponLib().get(id);
        if (weaponDatas == null) return null;

        ItemStack item = new ItemStack(weaponDatas.getMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        meta.displayName(weaponDatas.getName() != null ?
                weaponDatas.getName() : Component.text(id));
        if (weaponDatas.getModel() != null){
            NamespacedKey modelKey = new NamespacedKey("inksound", weaponDatas.getModel());
            meta.setItemModel(modelKey);
        }
        List<Component> lore = new ArrayList<>(weaponDatas.getLore());
        meta.lore(lore);

        var pdc = meta.getPersistentDataContainer();
        // 设置必要的pdc：id 激活 附灵
        pdc.set(ItemKeys.ID, PersistentDataType.STRING, id);
        pdc.set(ItemKeys.ENABLE, PersistentDataType.BOOLEAN, false);
        pdc.set(ItemKeys.ENCHANT, PersistentDataType.DOUBLE, 0.0);
        meta.addItemFlags(ItemFlag.values());
        meta.setUnbreakable(true);

        // 设置攻速，针对战士职业
        if (weaponDatas.getAttributes().containsKey(WeaponData.ATTACKSPEED)) {
            meta.removeAttributeModifier(Attribute.ATTACK_SPEED);
            AttributeModifier modifier = new AttributeModifier(
                    ATTACKSPEED,
                    4 - weaponDatas.getAttributes().get(WeaponData.ATTACKSPEED),
                    AttributeModifier.Operation.ADD_NUMBER,
                    EquipmentSlotGroup.MAINHAND);
            meta.addAttributeModifier(Attribute.ATTACK_SPEED, modifier);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static Map<String, WeaponData> getWeaponLib() {
        return Collections.unmodifiableMap(weaponLib);
    }

    public static ItemStack getWeapon(String id){
        return weaponBuild(id);
    }

    public static void addWeapon(WeaponData weaponData){
        weaponLib.put(weaponData.getId(), weaponData);
    }
}