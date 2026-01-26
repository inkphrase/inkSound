package Ink_server.TestCommands;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GetPdcCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NonNull [] args) {

        // 确定目标玩家：如果没输名字则查自己，如果是控制台则必须输名字
        Player target;
        if (args.length == 0) {
            if (sender instanceof Player p) {
                target = p;
            } else {
                sender.sendMessage("§c控制台使用请输入玩家名: /getpdc <player>");
                return true;
            }
        } else {
            target = Bukkit.getPlayer(args[0]);
        }

        if (target == null) {
            sender.sendMessage("§c玩家不在线。");
            return true;
        }

        // 获取该玩家的所有 PDC Keys
        PersistentDataContainer pdc = target.getPersistentDataContainer();
        Set<NamespacedKey> keys = pdc.getKeys();

        if (keys.isEmpty()) {
            sender.sendMessage("§e玩家 §f" + target.getName() + " §e目前没有任何 PDC 记录。");
        } else {
            sender.sendMessage("§6--- 玩家 " + target.getName() + " 的 PDC 列表 ---");
            // 将 NamespacedKey 转换为字符串并列出
            for (NamespacedKey key : keys) {
                sender.sendMessage("§7- §f" + key.toString());
                sender.sendMessage("§7- §f" + target.getPersistentDataContainer().get(key, PersistentDataType.STRING));
            }
            sender.sendMessage("§6总计: §e" + keys.size() + " §6项");
        }

        return true;
    }

    // Tab 补全：自动提示在线玩家
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NonNull [] args) {
        if (args.length == 1) {
            return null; // 返回 null 自动补全在线玩家
        }
        return new ArrayList<>();
    }
}
