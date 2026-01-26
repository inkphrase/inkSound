package Ink_server.House;

import Ink_server.InkSound;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GetHouse {
    private static final InkSound plugin = InkSound.getInstance();
    private final File dataFile;
    private final Gson gson;
    private static final Map<Integer, HouseData> houses = new ConcurrentHashMap<>();

    public GetHouse() {
        this.dataFile = new File(plugin.getDataFolder(), "houses.json");

        // 配置 Gson（美化输出、支持UUID）
        this.gson = new GsonBuilder()
                .setPrettyPrinting()  // 格式化输出
                .enableComplexMapKeySerialization()  // 支持复杂键
                .create();

        // 确保数据文件夹存在
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public static void init(){}

    public void load() {
        if (!dataFile.exists()) {
            plugin.getLogger().info("府邸数据文件不存在，创建新文件");
            save();  // 创建空文件
            return;
        }

        try (Reader reader = new InputStreamReader(
                new FileInputStream(dataFile),
                StandardCharsets.UTF_8)) {

            // 定义类型：Map<Integer, HouseData>
            Type type = new TypeToken<Map<Integer, HouseData>>(){}.getType();

            // 反序列化
            Map<Integer, HouseData> loaded = gson.fromJson(reader, type);

            if (loaded != null) {
                houses.clear();
                houses.putAll(loaded);
                plugin.getLogger().info("成功加载 " + houses.size() + " 个府邸数据");
            }

        } catch (IOException e) {
            plugin.getLogger().severe("加载府邸数据失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            plugin.getLogger().severe("解析府邸数据失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 保存数据 ====================

    /**
     * 保存所有府邸数据到 JSON 文件
     */
    public void save() {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(dataFile),
                StandardCharsets.UTF_8)) {

            // 序列化
            gson.toJson(houses, writer);

            plugin.getLogger().info("成功保存 " + houses.size() + " 个府邸数据");

        } catch (IOException e) {
            plugin.getLogger().severe("保存府邸数据失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void shareHouse(Player player, int num){
        if (houses.isEmpty())return;
        if (num < 0 || num >= houses.size()) return;
        UUID uuid = player.getUniqueId();
        houses.get(num).addMember(uuid);
        PlayerInfoManager.getPlayerInfo(uuid).setHouseId(num);
        StorageManager.addSharedStoragePermission(uuid, num);
        Bukkit.broadcast(Component.text("玩家" + player.getName() + "获得了" + StorageManager.toChinese(num) + "号府邸！")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.ITALIC, false));
    }

    public static void giveNewHouse(Player player){
        UUID uuid = player.getUniqueId();
        int num = houses.size();
        houses.put(num, new HouseData());
        houses.get(num).addMember(uuid);
        PlayerInfoManager.getPlayerInfo(uuid).setHouseId(num);
        StorageManager.addSharedStoragePermission(uuid,num);
        Bukkit.broadcast(Component.text("玩家" + player.getName() + "获得了" + StorageManager.toChinese(num) + "号府邸！")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.ITALIC, false));
    }

    public static void leaveHouse(Player player){
        if (houses.isEmpty()) return;
        UUID uuid = player.getUniqueId();
        int num = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
        houses.get(num).removeMember(uuid);
        StorageManager.removeSharedStoragePermission(uuid);
        PlayerInfoManager.getPlayerInfo(uuid).setHouseId(-1);
        Bukkit.broadcast(Component.text("玩家" + player.getName() + "离开了" + StorageManager.toChinese(num) + "号府邸！")
                .color(NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
    }

    public static Set<UUID> getHouseMember(int num){
        return houses.get(num).getMembers();
    }
    public static Map<Integer, HouseData> getHouses(){return houses;}
}
