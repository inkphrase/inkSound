package Ink_server.TestCommands;

import Ink_server.InkSound;
import Ink_server.StaticDatas.MonsterLib.MonsterData;
import Ink_server.StaticDatas.MonsterLib.MonsterManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnPanlingMonster implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String label, @NonNull String @NonNull [] args){
        if (args.length > 1){
            sender.sendMessage(Component.text("用法: plspawn <怪物id>").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            return true;
        }
        if (!(sender instanceof Player player)){
            InkSound.getInstance().getLogger().info("此指令只可在游戏内使用");
            return true;
        }
        MonsterData data = MonsterManager.getMonster(args[0]);
        if (data == null){
            sender.sendMessage(Component.text("位置id！").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            return true;
        }
        Location loc = player.getLocation();
        MonsterManager.spawnMonster(args[0], loc);
        return  true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String alias, @NonNull String @NonNull [] args){
        if (args.length == 1){
            return new ArrayList<>(MonsterManager.getMonsterLib().keySet());
        }
        else return null;
    }
}
