package Ink_server.TestCommands;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Collection;

public class PlayerDataCommand {

    /**
     * 执行指令: /pldata get <player> [dataname]
     */
    public static String executeCommand(String[] args) {
        if (args.length < 2) {
            return "§c用法: /pldata get <player> [dataname]";
        }

        String command = args[0];

        if (!command.equalsIgnoreCase("get")) {
            return "§c未知指令: " + command;
        }

        String playerName = args[1];
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) return "§c玩家 " + playerName + " 未找到或未在线";
        EntityData playerData = EntityDataManager.getData(player);

        if (playerData == null) {
            return "§c玩家 " + playerName + " 未找到或未在线";
        }

        // 如果提供了具体属性名
        if (args.length >= 3) {
            String dataName = args[2];
            return getSpecificData(playerData, dataName);
        }

        // 否则返回所有属性
        return getAllData(playerData);
    }

    /**
     * 获取特定属性的值
     */
    private static String getSpecificData(EntityData playerData, String dataName) {
        try {
            Field field = EntityData.class.getDeclaredField(dataName);
            field.setAccessible(true);
            Object value = field.get(playerData);
            return formatFieldDisplay(dataName, value);
        } catch (NoSuchFieldException e) {
            return "§c属性不存在: " + dataName;
        } catch (IllegalAccessException e) {
            return "§c无法访问属性: " + dataName;
        }
    }

    /**
     * 获取所有属性的值
     */
    private static String getAllData(EntityData playerData) {
        StringBuilder result = new StringBuilder("§6=== 玩家数据 ===\n");

        try {
            Field[] fields = EntityData.class.getDeclaredFields();

            // 按分类显示
            result.append("§a【战斗数据】\n");
            printFieldGroup(result, playerData, fields, "Data");
            printFieldGroup(result, playerData, fields, "speed");

            result.append("§a【装备数据】\n");
            printFieldGroup(result, playerData, fields, "Slot");

            result.append("§a【Buff数据】\n");
            printFieldGroup(result, playerData, fields, "Buff");

            result.append("§a【永久属性】\n");
            printFieldGroup(result, playerData, fields, "Perm");

            return result.toString();
        } catch (IllegalAccessException e) {
            return "§c获取属性失败";
        }
    }

    /**
     * 按分类打印属性组
     */
    private static void printFieldGroup(StringBuilder result, EntityData playerData,
                                        Field[] fields, String keyword) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.getName().contains(keyword)) {
                field.setAccessible(true);
                Object value = field.get(playerData);
                result.append("  ").append(formatFieldWithValue(field.getName(), value)).append("\n");
            }
        }
    }

    /**
     * 格式化单个字段显示（用于具体查询）
     */
    private static String formatFieldDisplay(String fieldName, Object value) {
        if (value == null) {
            return "§a" + fieldName + ": §enull";
        }

        Class<?> type = value.getClass();

        if (type == Double.class || type == Float.class) {
            return "§a" + fieldName + ": §e" + value;
        } else if (type == Integer.class || type == Long.class) {
            return "§a" + fieldName + ": §e" + value;
        } else if (type == String.class) {
            return "§a" + fieldName + ": §e" + value;
        } else if (type == Boolean.class) {
            return "§a" + fieldName + ": §e" + value;
        } else if (Collection.class.isAssignableFrom(type)) {
            return "§a" + fieldName + ": §e" + formatCollection((Collection<?>) value);
        } else {
            return "§a" + fieldName + ": §e" + value.toString();
        }
    }

    /**
     * 格式化字段与值显示（用于列表显示）
     */
    private static String formatFieldWithValue(String fieldName, Object value) {
        if (value == null) {
            return fieldName + ": §enull";
        }

        Class<?> type = value.getClass();

        if (type == Double.class || type == Float.class) {
            return fieldName + ": §e" + value;
        } else if (type == Integer.class || type == Long.class) {
            return fieldName + ": §e" + value;
        } else if (type == String.class) {
            return fieldName + ": §e\"" + value + "\"";
        } else if (type == Boolean.class) {
            return fieldName + ": §e" + value;
        } else if (Collection.class.isAssignableFrom(type)) {
            return fieldName + ": §e" + formatCollection((Collection<?>) value);
        } else {
            return fieldName + ": §e" + value.toString();
        }
    }

    /**
     * 格式化集合显示
     */
    private static String formatCollection(Collection<?> collection) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        collection.forEach(item -> {
            if (sb.length() > 1) sb.append(", ");
            sb.append(item);
        });
        sb.append("]");
        return sb.toString();
    }
}
