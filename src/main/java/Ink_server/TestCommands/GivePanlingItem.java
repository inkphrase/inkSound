package Ink_server.TestCommands;

import Ink_server.StaticDatas.ItemLib.ItemDataManager;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GivePanlingItem implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String label, @NonNull String @NonNull [] args){
        // 格式: /plgive <player> <type> <id>
        if (args.length < 3) {
            sender.sendMessage("§c用法: /plgive <玩家> <类型> <ID>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§c玩家不在线。");
            return true;
        }

        String typeIdentifier = args[1]; // 例如 pl.item.weapon
        String itemId = args[2];

        ItemStack item;

        // 分支管理：根据标识符决定去哪个库找物品
        switch (typeIdentifier) {
            case "weapon":
                item = WeaponManager.getWeapon(itemId);
                break;
            case "item":
                // item = plugin.getMaterialManager().buildMaterial(itemId);
                item = ItemDataManager.getItem(itemId);
                break;
            default:
                sender.sendMessage("§c未知的物品类型标识符。");
                return true;
        }

        if (item != null) {
            if (args.length == 4){
                item.setAmount(Integer.parseInt(args[3]));
            }
            target.getInventory().addItem(item);
            sender.sendMessage("§a已将 " + itemId + " 发送给 " + target.getName());
        } else {
            sender.sendMessage("§c找不到 ID 为 " + itemId + " 的物品。");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String alias, @NonNull String @NonNull [] args) {
        if (args.length == 1) return null; // 返回 null 默认补全在线玩家名

        if (args.length == 2) {
            return Arrays.asList("weapon", "item");
        }

        if (args.length == 3 && args[1].equals("weapon")) {
            // 从武器库中获取所有已注册的 ID
            return new ArrayList<>(WeaponManager.getWeaponLib().keySet());
        }else if (args.length == 3 && args[1].equals("item")){
            return new ArrayList<>(ItemDataManager.getItemLib().keySet());
        }

        return Collections.emptyList();
    }
}
