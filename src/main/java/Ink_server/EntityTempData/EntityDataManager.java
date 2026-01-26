package Ink_server.EntityTempData;

import Ink_server.InkSound;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static Ink_server.InkSoundFight.TickTimer.team;

public class EntityDataManager implements Listener {
    private static final Map<UUID, EntityData> manager = new ConcurrentHashMap<>();
    private static final InkSound plugin = InkSound.getInstance();

    public static EntityData getData(Entity entity ){
        if (!(entity instanceof LivingEntity living)) return null;
        if (living instanceof Player player){
            return manager.computeIfAbsent(player.getUniqueId(), k -> new EntityData());
        }
        else return manager.get(living.getUniqueId());
    }

    private EntityDataManager(){}

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new EntityDataManager(), plugin);
        // 立即执行一次清理（服务器启动时）
        cleanupAllEntities();
        plugin.getLogger().info("实体数据管理器已初始化并完成无数据实体清理！");
    }

    public static void UnloacAll(){manager.clear();}

    public static void removeData(UUID uuid){
        manager.remove(uuid);
    }

    public static boolean hasData(UUID uuid) {
        return manager.containsKey(uuid);
    }

    public static void addEntity(UUID uuid, EntityData entityData){
        manager.put(uuid, entityData);
    }

    private static void cleanupAllEntities() {
        World world = Bukkit.getWorld("world");
        if (world == null) return;
        for (LivingEntity entity : world.getLivingEntities()) {
            cleanupEntity(entity);
        }
    }

    public static void cleanupEntity(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return;
        UUID uuid = livingEntity.getUniqueId();
        if (team != null && team.hasEntry(uuid.toString())) {
            team.removeEntries(uuid.toString());
            entity.remove();  // 等同于 kill
            plugin.getLogger().info("清理非法战斗实体: " + entity.getType() + " (UUID: " + uuid + ")");
            removeData(uuid);
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        Chunk chunk = event.getChunk();
        cleanupChunkMonsters(chunk);

    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        Bukkit.getScheduler().runTask(plugin, ()-> cleanupChunkMonsters(chunk));
    }

    /**
     * 清理单个区块中的所有怪物
     */
    private static void cleanupChunkMonsters(Chunk chunk) {
        int count = 0;
        for (Entity entity : chunk.getEntities()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (living instanceof Player) continue; // 跳过玩家

            UUID uuid = living.getUniqueId();

            // 从怪物队伍中移除
            if (team != null && team.hasEntry(uuid.toString())) {
                team.removeEntry(uuid.toString());
                // 移除实体数据
                removeData(uuid);

                // 删除实体
                entity.remove();
                count++;
            }
        }

        if (count > 0 && plugin.getConfig().getBoolean("debug-chunk", false)) {
            plugin.getLogger().info(
                    "区块 [" + chunk.getX() + ", " + chunk.getZ() + "] " +
                            "已清理 " + count + " 个怪物"
            );
        }
    }
}
