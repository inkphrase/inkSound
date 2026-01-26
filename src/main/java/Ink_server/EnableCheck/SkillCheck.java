package Ink_server.EnableCheck;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.Skills;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.WeaponLib.WeaponRegistry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

/**
 * 此类集成了所有被动技能的激活重检测
 */
public class SkillCheck {
    private static final Set<String> slot0Skills = new HashSet<>();

    static {
    }

    //这里检查某个槽位的技能
    public static void SlotSkillCheck(int i, EntityData entityData, ItemStack item){
        switch (i){
            case 0 -> handleSlot0(entityData, item);
        }
    }

    //这里检查一号位
    private static void handleSlot0(EntityData entityData, ItemStack item){
        if (item == null || entityData == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        var pdc = meta.getPersistentDataContainer();
        String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
    }

    private static void clearSlot0(EntityData entityData){
        for (String s : slot0Skills) {
        }
    }
}
