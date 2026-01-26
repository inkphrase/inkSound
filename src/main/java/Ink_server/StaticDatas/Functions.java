package Ink_server.StaticDatas;

import Ink_server.InkSound;
import Ink_server.StaticDatas.WeaponLib.WeaponData;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Functions {

    public static void init(){
        InkSound.getInstance().getLogger().info("工具方法已注册！");
    }

    //交易扣除物品
    public static boolean require(Player player, String id, int num) {
        if (num <= 0) return true;
        var ID = ItemKeys.ID;
        Inventory inv = player.getInventory();
        int amountCheck = 0;
        List<Integer> slots = new ArrayList<>();
        for (int slot = 35; slot >= 0; slot--) {
            ItemStack item = inv.getItem(slot);
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) continue;
            String itemId = item.getItemMeta().getPersistentDataContainer().get(ID, PersistentDataType.STRING);
            if (!Objects.equals(itemId, id)) continue;
            amountCheck += item.getAmount();
            slots.add(slot);
            if (amountCheck >= num) break;
        }
        if (amountCheck < num) return false;
        for (int slot : slots) {
            ItemStack item = inv.getItem(slot);
            if (item == null) continue;
            int itemAmount = item.getAmount();
            if (itemAmount > num) {
                item.setAmount(itemAmount - num);
                return true;
            }
            else if (itemAmount == num) {
                inv.setItem(slot, null);
                return true;
            }
            else {
                inv.setItem(slot, null);
                num -= itemAmount;
            }
        }
        return true;
    }

    /**
     * 简单交易：只根据原版物品扣除，慎用
     */
    public static boolean simpleRequire(Player player, Material material, int num) {
        if (num <= 0) return true;
        if (material == null || material == Material.AIR) return false;

        Inventory inv = player.getInventory();
        int amountCheck = 0;
        List<Integer> slots = new ArrayList<>();

        // 第一遍：检查数量并记录槽位
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = inv.getItem(slot);

            // ✅ 快速过滤：类型检查最快
            if (item == null || item.getType() != material) continue;

            amountCheck += item.getAmount();
            slots.add(slot);

            if (amountCheck >= num) break;
        }

        // 数量不足
        if (amountCheck < num) return false;

        // 第二遍：扣除物品
        for (int slot : slots) {
            ItemStack item = inv.getItem(slot);
            if (item == null) continue;

            int itemAmount = item.getAmount();
            if (itemAmount > num) {
                item.setAmount(itemAmount - num);
                return true;
            } else if (itemAmount == num) {
                inv.setItem(slot, null);
                return true;
            } else {
                inv.setItem(slot, null);
                num -= itemAmount;
            }
        }

        return true;
    }

    //热重载lore
    public static void refresh(ItemStack item) {
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        var pdc = meta.getPersistentDataContainer();
        String id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);

        if (id == null || !WeaponManager.getWeaponLib().containsKey(id)) return;

        // 获取配置中的标准 Lore
        WeaponData weaponData = WeaponManager.getWeaponLib().get(id);
        List<Component> configLoreStrings = weaponData.getLore();
        if (configLoreStrings == null || configLoreStrings.size() < 2) return;

        // 创建新的 Lore 列表，并去掉最后两行（根据你的需求）
        List<Component> newLore = new ArrayList<>();
        for (int i = 0; i < configLoreStrings.size() - 2; i++) {
            newLore.add(configLoreStrings.get(i));
        }

        // 获取物品当前的 Lore
        List<Component> currentLore = item.lore();
        if (currentLore == null) currentLore = new ArrayList<>();

        boolean changed = false;
        // 取两者长度的最小值进行比较和替换，防止负数或越界报错
        int compareSize = Math.min(newLore.size(), currentLore.size());

        for (int i = 0; i < compareSize; i++) {
            Component expected = newLore.get(i);
            // 使用 Objects.equals 进行内容比较而非引用比较
            if (!Objects.equals(currentLore.get(i), expected)) {
                currentLore.set(i, expected);
                changed = true;
            }
        }

        // 如果配置的 Lore 比物品现有的更长，补全缺失的部分
        if (newLore.size() > currentLore.size()) {
            for (int i = currentLore.size(); i < newLore.size(); i++) {
                currentLore.add(i, newLore.get(i));
            }
            changed = true;
        }

        // 只有发生变化时才写回物品，节省资源
        if (changed) {
            item.lore(currentLore);
        }
    }

    /**
     * 设置队伍碰撞规则
     * @param teamName 队伍名称
     * @param collide 是否碰撞
     */
    public static void setTeamCollision(String teamName, boolean collide) {
        if (collide) {
            enableTeamCollision(teamName);
        } else {
            disableTeamCollision(teamName);
        }
    }
    private static void disableTeamCollision(String teamName) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);

        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }

        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }
    private static void enableTeamCollision(String teamName) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(teamName);

        if (team == null) return;

        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
    }

}