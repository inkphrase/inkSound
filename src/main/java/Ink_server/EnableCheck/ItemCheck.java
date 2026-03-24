package Ink_server.EnableCheck;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import Ink_server.StaticDatas.WeaponLib.WeaponData;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.List;
import Ink_server.StaticDatas.ItemKeys;


public class ItemCheck {
    static Map<Integer, String> upChar = new HashMap<>();
    static {
        upChar.put(0,"一");upChar.put(1,"二");upChar.put(5,"六");upChar.put(6,"七");upChar.put(7,"八");upChar.put(8,"九");
        upChar.put(40,"副");upChar.put(36,"甲");upChar.put(37,"甲");upChar.put(38,"甲");upChar.put(39,"甲");
    }
    static NamespacedKey enablekey = ItemKeys.ENABLE;
    static NamespacedKey id = ItemKeys.ID;
    static NamespacedKey ench = ItemKeys.ENCHANT;
    public static void allCheck(Player player) {
        //遍历背包
        for (int i = 0; i <= 40; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || !item.hasItemMeta()) {continue;}
            //跳过不可激活
            var meta = item.getItemMeta();
            if (meta == null) continue;
            var pdc = meta.getPersistentDataContainer();
            String newId = pdc.get(id, PersistentDataType.STRING);
            if (newId == null) continue;
            WeaponData weaponData = WeaponManager.getWeaponLib().get(newId);
            if (weaponData == null || weaponData.getAttributes() == null || !weaponData.getAttributes().containsKey(WeaponData.ENABLESLOT)) continue;
            //在激活位时设置物品为激活
            doCheck(player, i, item, meta, pdc, weaponData.getAttributes());
        }
        AttributeCal.setSlotData(player);
        SkillCheck.skillUpdate(player);
        if (player.isOp()){
            player.sendMessage("执行遍历激活！");
        }
    }

    public static void theSlotCheck(Player player, int i, ItemStack oldone, ItemStack newone) {
        ItemStack item = player.getInventory().getItem(i);
        if (!upChar.containsKey(i)) {
            if (item == null || !item.hasItemMeta()) {return;}
            var meta = item.getItemMeta();
            if (meta == null) return;
            var pdc = meta.getPersistentDataContainer();
            boolean currentEnable = Boolean.TRUE.equals(pdc.get(enablekey, PersistentDataType.BOOLEAN));
            if (currentEnable) {
                String newId = pdc.get(id, PersistentDataType.STRING);
                WeaponData weaponDatas = WeaponManager.getWeaponLib().get(newId);
                Map<String, Double> attributes = (weaponDatas != null) ? weaponDatas.getAttributes() : null;
                if (attributes == null || !attributes.containsKey(WeaponData.ENABLESLOT)) return;
                double slot = attributes.get(WeaponData.ENABLESLOT);
                pdc.set(enablekey, PersistentDataType.BOOLEAN, false);
                loreSet(meta, false, slot);
                item.setItemMeta(meta);
            }
            return;
        }
        boolean hasOld = oldone != null && oldone.hasItemMeta();
        boolean hasNew = newone != null && newone.hasItemMeta();
        if (!hasOld && !hasNew) return;
        var oldMeta = (hasOld) ? oldone.getItemMeta() : null;
        var newMeta = (hasNew) ? newone.getItemMeta() : null;
        //拦截弩上膛、激活变动
        if (oldMeta != null && newMeta != null) {
            var oldPdc = oldMeta.getPersistentDataContainer();
            var newPdc = newMeta.getPersistentDataContainer();
            String oldId = oldPdc.get(id, PersistentDataType.STRING);
            String newId = newPdc.get(id, PersistentDataType.STRING);
            Double oldEnchant = oldPdc.get(ench, PersistentDataType.DOUBLE);
            Double newEnchant = newPdc.get(ench, PersistentDataType.DOUBLE);
            if (oldEnchant != null && oldEnchant.equals(newEnchant)) {
                if (newId != null && newId.equals(oldId)) {
                    //只看仅检测弓弩
                    if (newId.contains("bow")) {
                        //比较新旧激活状态是否相同，同时排除空指针
                        boolean oldEnable = Boolean.TRUE.equals(oldPdc.get(enablekey, PersistentDataType.BOOLEAN));
                        boolean newEnable = Boolean.TRUE.equals(newPdc.get(enablekey, PersistentDataType.BOOLEAN));
                        if (oldEnable == newEnable) return;
                    }//新旧id相同，但旧不激活，新激活时拦截；即拦截设置物品为激活状态时的槽位变动
                    if (Boolean.FALSE.equals(oldPdc.get(enablekey, PersistentDataType.BOOLEAN)) &&
                            Boolean.TRUE.equals(newPdc.get(enablekey, PersistentDataType.BOOLEAN))) return;
                }
            }
        }

        //newstack不可激活时，检查oldstack，old已激活时立即计算数值，old未激活或不可激活时直接拦截
        if (item == null || !item.hasItemMeta()) {
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        var meta = item.getItemMeta();
        if (meta == null) {
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        var pdc = meta.getPersistentDataContainer();
        String newId = pdc.get(id, PersistentDataType.STRING);
        if (newId == null) {
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        WeaponData weaponData = WeaponManager.getWeaponLib().get(newId);
        if (weaponData == null) {
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        Map<String, Double> attributes = weaponData.getAttributes();
        if (attributes == null || !attributes.containsKey(WeaponData.ENABLESLOT)){
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        //在激活位时设置物品为激活
        if (!doCheck(player, i, item, meta, pdc, attributes)) {
            //无论新物品是否需要进行激活更改，都需要检测一次旧物品
            oldCheck(player, oldMeta,i);
            SkillCheck.skillUpdate(player);
            return;
        }
        AttributeCal.setSlotData(player);
        SkillCheck.skillUpdate(player);
        if (player.isOp()){
            player.sendMessage("执行对槽位激活！");
        }
    }

    private static void oldCheck(Player player, ItemMeta oldMeta, int i) {
        if (oldMeta == null) return;
        var oldPdc = oldMeta.getPersistentDataContainer();
        if (Boolean.TRUE.equals(oldPdc.get(enablekey, PersistentDataType.BOOLEAN))){
            String oldId = oldPdc.get(id, PersistentDataType.STRING);
            WeaponData weaponData = WeaponManager.getWeaponLib().get(oldId);
            double oldSlot = weaponData.getAttributes().get(WeaponData.ENABLESLOT);
            if (oldSlot != i) return;
            AttributeCal.setSlotData(player);
            if (player.isOp()){
                player.sendMessage("[oldcheck]执行对槽位激活！");
            }
        }
    }

    private static boolean doCheck(Player player, int i, ItemStack item, ItemMeta meta,
                                   PersistentDataContainer pdc, Map<String, Double> attributes) {
        double slot = attributes.get(WeaponData.ENABLESLOT);
        //检测物品移向非激活位
        double levelLimit = attributes.getOrDefault(WeaponData.LEVELLIMIT, 0.0);
        double joblimit = attributes.getOrDefault(WeaponData.JOBLIMIT, 3.0);
        //获取物品现在应是的激活状态
        boolean enable = false;
        boolean currentEnable = Boolean.TRUE.equals(pdc.get(enablekey, PersistentDataType.BOOLEAN));
        if (i == slot) {
            // 等级必须满足
            if (player.getLevel() >= levelLimit) {
                var playerJob = PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getJob();
                // 职业匹配 或 通用职业（3.0）
                enable = (joblimit == playerJob || joblimit == 3.0);
            }
        }
        if (!enable){
            /*
            如果物品本就不且不应该激活（物品在非激活位间移动、新的物品无法被该玩家激活）拦截lore修改，数值计算交给oldcheck
             */
            if (!currentEnable) return false;
            /*
            如果物品原本已激活但不应该激活，去除激活状态，拦截计算，因为物品在从被激活位移走时就已经至少触发oldcheck了
            在这里只进行lore计算，并交由oldcheck计算从现在物品落下的格子移走的物品是否应该计算
             */
            pdc.set(enablekey, PersistentDataType.BOOLEAN, false);
            loreSet(meta, false, slot);
            item.setItemMeta(meta);
            return false;
        }


        //物品本就应该激活，且目前已激活时，不修改lore直接计算属性
        if (currentEnable) return true;
        //设置新的激活状态
        pdc.set(enablekey, PersistentDataType.BOOLEAN, true);
        //修改激活lore
        loreSet(meta, true, slot);
        item.setItemMeta(meta);
        return true;

    }

    private static List<Component> getComponents(ItemMeta meta) {
        List<Component> lore = meta.hasLore() ? meta.lore() : new ArrayList<>();
        if (lore == null) {lore = new ArrayList<>();}
        return lore;
    }

    private static void loreSet(ItemMeta meta, boolean enable, double slot) {
        List<Component> lore = getComponents(meta);
        Component enableLore = (enable) ?
                Component.text("属性已激活").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false) :
                Component.text("激活位 [" + upChar.get((int)slot) + "]").
                        color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        if (!lore.isEmpty()) {
            Component lastLine = lore.getLast();
            String lastLineText = LegacyComponentSerializer.legacySection().serialize(lastLine);
            if (lastLineText.contains("激活")) {
                lore.set(lore.size() - 1, enableLore);
            } else {
                lore.add(enableLore);
            }
            meta.lore(lore);
        }
    }
}
