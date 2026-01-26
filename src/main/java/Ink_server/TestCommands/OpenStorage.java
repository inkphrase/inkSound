package Ink_server.TestCommands;

import Ink_server.House.GetHouse;
import Ink_server.House.StorageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import java.io.*;
import java.sql.*;
import java.util.*;

public class OpenStorage implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NonNull [] args){
        //用法：/plopen player/house name/num
        if (args.length < 2){
            sender.sendMessage(Component.text("命令用法：/plopen player/house <玩家名>/<府邸号>").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
            return true;
        }
        String type = args[0];
        Player cmd = (Player) sender;
        switch (type){
            case "player" -> {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null){
                    sender.sendMessage(Component.text("玩家" + args[1] + "不在线").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                    return true;
                }
                UUID uuid = player.getUniqueId();
                Inventory storage = StorageManager.getPersonalStorageMap().computeIfAbsent(uuid, k -> {
                    Inventory inv = StorageManager.createPersonalInventory(player);
                    StorageManager.loadPersonalStorageFromDB(uuid, inv);
                    return inv;
                });
                cmd.openInventory(storage);
            }
            case "house" -> {
                int id = Integer.parseInt(args[1]);
                if (id < 0 || id >= GetHouse.getHouses().size()){
                    sender.sendMessage(Component.text("请输入正确的府邸号").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                    return true;
                }
                Inventory storage = StorageManager.getSharedStorageMap().computeIfAbsent(id, k -> {
                    Inventory inv = StorageManager.createSharedInventory(id);
                    StorageManager.loadSharedStorageFromDB(id, inv);
                    return inv;
                });
                cmd.openInventory(storage);
            }
            default -> sender.sendMessage(Component.text("命令用法：/plopen player/house <玩家名>/<府邸号>").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String alias, @NonNull String @NonNull [] args){
        if (args.length == 1){
            return List.of("player", "house");
        }
        if (args.length == 2){
            switch (args[0]){
                case "player" -> {return  null;}
                case "house" -> {return List.of("num");}
            }
        }
        return Collections.emptyList();
    }
}
