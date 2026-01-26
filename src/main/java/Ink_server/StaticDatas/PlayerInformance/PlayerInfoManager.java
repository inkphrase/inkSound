package Ink_server.StaticDatas.PlayerInformance;

import Ink_server.InkSound;
import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.StaticDatas.PlayerKeys;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInfoManager {
    private static final Map<UUID, PlayerInfo> manager = new ConcurrentHashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static NamespacedKey PLAYER_INFO_KEY = PlayerKeys.PLAYERINFO;
    public static Map<UUID, PlayerInfo> getManager(){return manager;}

    //读取信息
    public static PlayerInfo getPlayerInfo(UUID uuid){
        return manager.get(uuid);
    }

    //上线加载
    public static PlayerInfo loadPlayerInfo(Player player) {
        UUID uid = player.getUniqueId();
        // 从 PDC 读取
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        String json = pdc.get(PLAYER_INFO_KEY, PersistentDataType.STRING);
        PlayerInfo playerInfo;
        if (json != null && !json.isEmpty()) {
            // 反序列化
            playerInfo = GSON.fromJson(json, PlayerInfo.class);
        } else {
            // 新玩家，创建默认数据
            playerInfo = new PlayerInfo();
        }
        // 存入缓存
        manager.put(uid, playerInfo);
        //读取剩余冷却并写入结束时间戳
        //加载冷却结束时间
        EntityData playerData = EntityDataManager.getData(player);
        if (playerData == null) {
            InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
            playerData = new EntityData();
        }
        int currentTick = Bukkit.getCurrentTick();
        for (String id : playerInfo.getRestCooldownMap().keySet()) {
            int restTicks = playerInfo.getRestCooldownMap().get(id);
            playerData.cooldownMap.put(id, currentTick + restTicks);
        }
        return playerInfo;
    }

    //下线时保存
    public static void savePlayerInfo(Player player) {
        UUID uid = player.getUniqueId();
        PlayerInfo playerInfo = manager.get(uid);
        if (playerInfo != null) {
            //读取结束时间戳，写入剩余冷却
            EntityData playerData = EntityDataManager.getData(player);
            if (playerData == null) {
                InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
                playerData = new EntityData();
            }
            int currentTick = Bukkit.getCurrentTick();
            playerInfo.clearRestCooldownMap();
            for (String id : playerData.cooldownMap.keySet()) {
                int endTick = playerData.cooldownMap.get(id);
                if (endTick > currentTick) {
                    playerInfo.setRestCooldownMap(id, endTick - currentTick);
                }
            }
            // 序列化为 JSON
            String json = GSON.toJson(playerInfo);
            // 写入 PDC
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            pdc.set(PLAYER_INFO_KEY, PersistentDataType.STRING, json);
            // 从缓存移除
            manager.remove(uid);
        }
    }

    //强制保存，仅下线与调试时使用
    public static void forceSave(Player player) {
        UUID uid = player.getUniqueId();
        PlayerInfo playerInfo = manager.get(uid);
        if (playerInfo != null) {
            //读取结束时间戳，写入剩余冷却
            EntityData playerData = EntityDataManager.getData(player);
            if (playerData == null) {
                InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
                playerData = new EntityData();
            }
            int currentTick = Bukkit.getCurrentTick();
            for (String id : playerData.cooldownMap.keySet()) {
                int endTick = playerData.cooldownMap.get(id);
                if (endTick > currentTick) {
                    playerInfo.setRestCooldownMap(id, endTick - currentTick);
                }
            }
            String json = GSON.toJson(playerInfo);
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            pdc.set(PLAYER_INFO_KEY, PersistentDataType.STRING, json);
        }
    }

    public static void saveAll() {
        for (Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            forceSave(player);
        }
        manager.clear();
    }
}
