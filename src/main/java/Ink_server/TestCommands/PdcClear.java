package Ink_server.TestCommands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class PdcClear implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
            // 1. 权限检查
            if (!sender.hasPermission("ink.admin")) {
                sender.sendMessage("§c你没有权限执行此操作。");
                return true;
            }

            // 2. 确定目标玩家
            Player target;
            if (args.length == 0) {
                if (sender instanceof Player) {
                    target = (Player) sender;
                } else {
                    sender.sendMessage("§c控制台必须指定一名玩家：/clearpdc <玩家名>");
                    return true;
                }
            } else {
                target = Bukkit.getPlayer(args[0]);
            }

            // 3. 执行清理
            if (target != null && target.isOnline()) {
                PersistentDataContainer pdc = target.getPersistentDataContainer();
                // 获取所有已存在的键
                Set<NamespacedKey> keys = pdc.getKeys();
                for (NamespacedKey key : keys) {
                    pdc.remove(key);
                }
                sender.sendMessage("§a已成功清除玩家 " + target.getName() + " 的所有 PDC 数据。");
                target.sendMessage("§e你的旧版 PDC 数据已被管理员清除。");
            } else {
                sender.sendMessage("§c目标玩家不在线。");
            }

            return true;
        }

}
