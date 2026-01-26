package Ink_server.TestCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

/**
 * PlayerDataCommand 的 Bukkit/Spigot 适配器
 */
public class PlayerDataCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        // 检查是否为玩家
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家才能使用此命令");
            return true;
        }

        // 检查权限
        if (!player.hasPermission("ink.test")) {
            player.sendMessage("§c您没有权限使用此命令");
            return true;
        }

        // 执行指令
        String result = PlayerDataCommand.executeCommand(args);
        sender.sendMessage(result);

        return true;
    }
}
