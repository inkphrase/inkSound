package Ink_server.EnableCheck;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.WeaponLib.WeaponData;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

/**
 * 装备技能、套装技能更新类
 */
public class SkillCheck {
    //这里定义实例中用于统计玩家技能的变量
    public final Map<String, Integer> seriesCount = new HashMap<>();
    public final Set<String> armorSkills = new HashSet<>();
    public Player player;

    //需要传入玩家，声明这次check的目标
    public SkillCheck(Player player) {
        this.player = player;
    }

    //这里进行统计与实际激活
    public void doCount() {
        //遍历装备槽位，将装备数据中的技能写入装备技能set和套装技能map
        Player player = this.player;

        for (int i : AttributeCal.slots){
            //基础非空检查
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || !item.hasItemMeta()) continue;

            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;

            var pdc = meta.getPersistentDataContainer();
            String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
            if (id == null) continue;

            //武器数据类提取指定字段
            WeaponData weaponData = WeaponManager.getWeaponLib().get(id);
            Set<String> weaponArmorSkills = weaponData.getArmorSkills();
            Set<String> weaponSeriesSkills = weaponData.getSeriesSkills();

            this.armorSkills.addAll(weaponArmorSkills);
            //统计套装件数
            for (String seriesSkillID : weaponSeriesSkills){
                this.seriesCount.put(seriesSkillID, this.seriesCount.getOrDefault(seriesSkillID, 0) + 1);
            }
        }
        //使用内部枚举类将满足指定数量的套装技能转译并写入装备技能
        for(String seriesSkillID : this.seriesCount.keySet()){
            SkillsOnArmor.SeriesCheck seriesCheck = SkillsOnArmor.SeriesCheck.getById(seriesSkillID);
            if (seriesCheck == null) continue;
            seriesCheck.check(this, seriesSkillID);
        }

        //根据现在的装备技能set和玩家上一时刻的技能set的两个差集，决定装备技能变动情况
        EntityData playerData = EntityDataManager.getData(player);
        var recentArmorSkills = playerData.getArmorSkills();

        //失去的技能
        Set<String> shouldDisable = new HashSet<>(recentArmorSkills);
        shouldDisable.removeAll(this.armorSkills);

        //新获得的技能
        Set<String> shouldEnable = new HashSet<>(this.armorSkills);
        shouldEnable.removeAll(recentArmorSkills);

        //禁用与启用
        for (String disabledSkillID : shouldDisable){
            SkillsOnArmor skillsOnArmor = SkillsOnArmor.getById(disabledSkillID);
            if (skillsOnArmor == null) continue;
            skillsOnArmor.disable(player);
        }
        for (String enabledSkillID : shouldEnable){
            SkillsOnArmor skillsOnArmor = SkillsOnArmor.getById(enabledSkillID);
            if (skillsOnArmor == null) continue;
            skillsOnArmor.enable(player);
        }

        //更新装备技能set
        playerData.setArmorSkills(this.armorSkills);
    }

    //外部显式调用
    public static void skillUpdate(Player player){
        SkillCheck skillCheck = new SkillCheck(player);
        skillCheck.doCount();
    }

}
