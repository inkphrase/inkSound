package Ink_server.TestCommands;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerInfoCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NonNull [] args) {

        // 参数长度检查: plinfo <get/set/add> <player> <field> [value]
        if (args.length < 3) {
            sender.sendMessage("§c用法: /plinfo get/set/add <玩家> <信息字段> [值]");
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§c玩家不在线。");
            return true;
        }

        // 获取该玩家当前的 PlayerInfo 缓存对象
        PlayerInfo info = PlayerInfoManager.getPlayerInfo(target.getUniqueId());
        String field = args[2].toLowerCase();

        switch (action) {
            case "get":
                handleGet(sender, info, field);
                break;
            case "set":
                if (!sender.hasPermission("ink.admin")){
                    sender.sendMessage("§c你没有设置玩家信息的权限！");
                    return true;
                }
                if (args.length < 4) {
                    sender.sendMessage("§c请输入要设置的数值。");
                    return true;
                }
                if (args[3].equalsIgnoreCase("houseid")) {
                    sender.sendMessage("§c不允许指令修改房产数据");
                    return true;
                }
                handleSet(sender, info, field, args[3]);
                break;
            case "add":
                if (!sender.hasPermission("ink.admin")){
                    sender.sendMessage("§c你没有设置玩家信息的权限！");
                    return true;
                }
                if (args.length < 4) {
                    sender.sendMessage("§c请输入要添加的值。");
                    return true;
                }
                if (args[3].equalsIgnoreCase("houseid")) {
                    sender.sendMessage("§c不允许指令修改房产数据");
                    return true;
                }
                handleAdd(sender, info, field, args[3]);
                break;
            default:
                sender.sendMessage("§c未知动作: " + action);
                break;
        }

        // 操作完成后，建议手动同步一次 PDC 确保数据落盘
        PlayerInfoManager.forceSave(target);
        return true;
    }

    // --- Tab 补全逻辑 ---
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NonNull [] args) {
        if (!sender.hasPermission("inkserver.admin")) return new ArrayList<>();

        // 第一层：动作补全 /plinfo <get/set/add>
        if (args.length == 1) {
            return filter(Arrays.asList("get", "set", "add"), args[0]);
        }

        // 第二层：玩家名补全 /plinfo action <player>
        if (args.length == 2) {
            return null; // 返回 null 时，Bukkit 会自动补全在线玩家名
        }

        // 第三层：具体字段补全 /plinfo action player <field>
        if (args.length == 3) {
            return filter(getAllFieldNames(), args[2]);
        }

        return new ArrayList<>();
    }

    /**
     * 获取 PlayerInfo 中所有的字段名称
     */
    private List<String> getAllFieldNames() {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = PlayerInfo.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName().toLowerCase());
        }
        return fieldNames;
    }

    /**
     * 简单的过滤器，确保补全内容匹配已输入的字符
     */
    private List<String> filter(List<String> list, String input) {
        return list.stream()
                .filter(s -> s.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * 处理 get 操作 - 获取字段值
     */
    private void handleGet(CommandSender sender, PlayerInfo info, String fieldName) {
        try {
            Field field = findFieldIgnoreCase(fieldName);
            if (field == null) {
                sender.sendMessage("§c未知字段名: " + fieldName);
                return;
            }

            field.setAccessible(true);
            Object value = field.get(info);

            String display = formatValueForDisplay(value);
            sender.sendMessage("§a" + field.getName() + ": §e" + display);
        } catch (IllegalAccessException e) {
            sender.sendMessage("§c无法访问字段: " + fieldName);
        }
    }

    /**
     * 处理 set 操作 - 设置字段值
     */
    private void handleSet(CommandSender sender, PlayerInfo info, String fieldName, String valueStr) {
        try {
            Field field = findFieldIgnoreCase(fieldName);
            if (field == null) {
                sender.sendMessage("§c未知字段名: " + fieldName);
                return;
            }

            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            // 尝试转换值并设置
            Object convertedValue = convertValue(valueStr, fieldType);
            field.set(info, convertedValue);

            sender.sendMessage("§a已设置 " + field.getName() + " 为 " + valueStr);
        } catch (IllegalAccessException e) {
            sender.sendMessage("§c无法访问字段: " + fieldName);
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§c类型转换失败: " + e.getMessage());
        }
    }

    /**
     * 处理 add 操作 - 添加到集合或增加数值
     */
    private void handleAdd(CommandSender sender, PlayerInfo info, String fieldName, String value) {
        try {
            Field field = findFieldIgnoreCase(fieldName);
            if (field == null) {
                sender.sendMessage("§c未知字段名: " + fieldName);
                return;
            }

            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object fieldValue = field.get(info);

            // 处理 Set 类型
            if (Set.class.isAssignableFrom(fieldType)) {
                @SuppressWarnings("unchecked")
                Set<String> set = (Set<String>) fieldValue;
                if (set == null) {
                    set = new HashSet<>();
                    field.set(info, set);
                }
                set.add(value);
                sender.sendMessage("§a已将 " + value + " 添加至 " + field.getName());
            }
            // 处理 Map 类型
            else if (Map.class.isAssignableFrom(fieldType)) {
                @SuppressWarnings("unchecked")
                Map<String, ?> map = (Map<String, ?>) fieldValue;
                if (map == null) {
                    map = new HashMap<>();
                    field.set(info, map);
                }
                // 尝试添加为 key: value 的形式，如果只有一个参数则设为默认值
                @SuppressWarnings("unchecked")
                Map<String, Object> mutableMap = (Map<String, Object>) map;
                mutableMap.put(value, false); // 默认值为 false，可自定义
                sender.sendMessage("§a已将 " + value + " 添加至 " + field.getName() + " (值: false)");
            }
            // 处理数值类型 (double, int, long, float)
            else if (isNumericType(fieldType)) {
                double currentValue = ((Number) fieldValue).doubleValue();
                double addValue = Double.parseDouble(value);
                double newValue = currentValue + addValue;

                Object convertedValue = convertValue(String.valueOf(newValue), fieldType);
                field.set(info, convertedValue);
                sender.sendMessage("§a已将 " + field.getName() + " 增加 " + value + " (新值: " + newValue + ")");
            }
            else {
                sender.sendMessage("§c该字段类型不支持 add 操作: " + fieldType.getSimpleName());
            }
        } catch (IllegalAccessException e) {
            sender.sendMessage("§c无法访问字段: " + fieldName);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c错误：请输入有效的数字。");
        }
    }

    /**
     * 根据字段名查找字段（忽略大小写）
     */
    @Nullable
    private Field findFieldIgnoreCase(String fieldName) {
        Field[] fields = PlayerInfo.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 将字符串转换为指定的类型
     */
    private Object convertValue(String value, Class<?> targetType) throws IllegalArgumentException {
        try {
            if (targetType == double.class || targetType == Double.class) {
                return Double.parseDouble(value);
            } else if (targetType == int.class || targetType == Integer.class) {
                return Integer.parseInt(value);
            } else if (targetType == long.class || targetType == Long.class) {
                return Long.parseLong(value);
            } else if (targetType == float.class || targetType == Float.class) {
                return Float.parseFloat(value);
            } else if (targetType == boolean.class || targetType == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (targetType == String.class) {
                return value;
            } else {
                throw new IllegalArgumentException("不支持的类型: " + targetType.getSimpleName());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无法将 '" + value + "' 转换为 " + targetType.getSimpleName());
        }
    }

    /**
     * 检查是否为数值类型
     */
    private boolean isNumericType(Class<?> type) {
        return type == double.class || type == Double.class ||
                type == int.class || type == Integer.class ||
                type == long.class || type == Long.class ||
                type == float.class || type == Float.class;
    }

    /**
     * 格式化值以供显示
     */
    private String formatValueForDisplay(Object value) {
        if (value == null) {
            return "null";
        }

        Class<?> type = value.getClass();

        // 数值类型
        if (isNumericType(type)) {
            return value.toString();
        }
        // 字符串
        else if (type == String.class) {
            return "\"" + value + "\"";
        }
        // 布尔值
        else if (type == Boolean.class) {
            return value.toString();
        }
        // Set 类型
        else if (Set.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            Set<String> set = (Set<String>) value;
            return "[" + String.join(", ", set) + "]";
        }
        // Map 类型
        else if (Map.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            Map<String, ?> map = (Map<String, ?>) value;
            return map.entrySet().stream()
                    .map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(", ", "{", "}"));
        }
        // Collection 类型
        else if (Collection.class.isAssignableFrom(type)) {
            Collection<?> collection = (Collection<?>) value;
            return "[" + collection.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
        }

        return value.toString();
    }
}
