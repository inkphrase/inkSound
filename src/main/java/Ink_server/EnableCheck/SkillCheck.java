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

public class SkillCheck {
    //这里定义实例中用于统计玩家技能的变量
    public final Map<String, Integer> seriesCount = new HashMap<>();
    public final Set<String> armorSkills = new HashSet<>();
    public Player player;

    public SkillCheck(Player player) {
        this.player = player;
    }

    //这里进行统计与实际激活
    public void doCount() {
        Player player = this.player;
        for (int i : AttributeCal.slots){
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || !item.hasItemMeta()) continue;
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;
            var pdc = meta.getPersistentDataContainer();
            String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
            if (id == null) continue;
            WeaponData weaponData = WeaponManager.getWeaponLib().get(id);
            Set<String> weaponArmorSkills = weaponData.getArmorSkills();
            Set<String> weaponSeriesSkills = weaponData.getSeriesSkills();
            this.armorSkills.addAll(weaponArmorSkills);
            for (String seriesSkillID : weaponSeriesSkills){
                this.seriesCount.put(seriesSkillID, this.seriesCount.getOrDefault(seriesSkillID, 0) + 1);
            }
        }
        for(String seriesSkillID : this.seriesCount.keySet()){
            SkillsOnArmor.SeriesCheck seriesCheck = SkillsOnArmor.SeriesCheck.getById(seriesSkillID);
            if (seriesCheck == null) continue;
            seriesCheck.check(this, seriesSkillID);
        }
        EntityData playerData = EntityDataManager.getData(player);
        var recentArmorSkills = playerData.getArmorSkills();
        Set<String> shouldDisable = new HashSet<>(recentArmorSkills);
        shouldDisable.removeAll(this.armorSkills);
        Set<String> shouldEnable = new HashSet<>(this.armorSkills);
        shouldEnable.removeAll(recentArmorSkills);
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
        playerData.setArmorSkills(this.armorSkills);
    }

    public static void skillUpdate(Player player){
        SkillCheck skillCheck = new SkillCheck(player);
        skillCheck.doCount();
    }

}
