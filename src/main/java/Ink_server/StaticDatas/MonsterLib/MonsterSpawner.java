package Ink_server.StaticDatas.MonsterLib;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MonsterSpawner implements Listener {
    private static final Map<Block, Integer> spawners = new  ConcurrentHashMap<>();
    private static final InkSound plugin = InkSound.getInstance();

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new MonsterSpawner(), plugin);
        plugin.getLogger().info("刷怪笼系统已注册");
    }

    @EventHandler
    public void onSpawner(SpawnerSpawnEvent event) {

        var spawner = event.getSpawner();
        if (spawner == null) return;
        Block spawnerBlock = spawner.getBlock();

        // 在刷怪笼上方安全位置生成怪物
        Location loc = spawnerBlock.getLocation();

        // 取消原版生成
        event.setCancelled(true);

        // 检查下方告示牌
        Block signBlock = spawnerBlock.getRelative(BlockFace.DOWN);
        if (signBlock.getState() instanceof Sign sign) {
            String line1 = PlainTextComponentSerializer.plainText()
                    .serialize(sign.getSide(Side.FRONT).line(0));
            String line2 = PlainTextComponentSerializer.plainText()
                    .serialize(sign.getSide(Side.FRONT).line(1));
            String line3 = PlainTextComponentSerializer.plainText()
                    .serialize(sign.getSide(Side.FRONT).line(2));
            String line4 = PlainTextComponentSerializer.plainText()
                    .serialize(sign.getSide(Side.FRONT).line(3));


            if (line1.isEmpty() || !MonsterManager.getMonsterLib().containsKey(line1.trim())) return;

            int maxNum = 1;
            int nearbyNum = 0;

            if (!spawners.containsKey(spawnerBlock)) {
                try {
                    // 配置生成延迟（第2行: minDelay,maxDelay）
                    if (!line2.trim().isEmpty()) {
                        String[] delays = line2.trim().split(",");
                        if (delays.length >= 2) {
                            int minDelay = Integer.parseInt(delays[0].trim());
                            int maxDelay = Integer.parseInt(delays[1].trim());
                            spawner.setMinSpawnDelay(minDelay);
                            spawner.setMaxSpawnDelay(maxDelay);
                        }
                    }

                    // 配置生成数量（第3行: spawnCount,maxNearby）
                    if (!line3.trim().isEmpty()) {
                        String[] counts = line3.trim().split(",");
                        if (counts.length >= 2) {
                            int spawnCount = Integer.parseInt(counts[0].trim());
                            int maxNearby = Integer.parseInt(counts[1].trim());
                            spawner.setSpawnCount(spawnCount);
                            spawner.setMaxNearbyEntities(maxNearby);
                            maxNum = maxNearby;
                        }
                    }

                    // 配置范围（第4行: spawnRange,playerRange）
                    if (!line4.trim().isEmpty()) {
                        String[] ranges = line4.trim().split(",");
                        if (ranges.length >= 2) {
                            int playerRange = Integer.parseInt(ranges[1].trim());
                            spawner.setRequiredPlayerRange(playerRange);
                        }
                    }
                    spawner.update();
                    spawners.put(spawnerBlock, maxNum);
                }catch (Exception e){
                    plugin.getLogger().warning("刷怪笼配置错误，坐标: " + loc);
                }
            }

            String monsterId = MonsterManager.getMonster(line1.trim()).getId();

            for (Entity Entity : spawnerBlock.getLocation().getNearbyEntities(8,8,8)){
                if (!(Entity instanceof LivingEntity livingEntity) || Entity instanceof Player) continue;
                String nearId = livingEntity.getPersistentDataContainer().getOrDefault(ItemKeys.ID, PersistentDataType.STRING, "unknown");
                if (nearId.equals(monsterId)){
                    nearbyNum++;
                }
            }
            int max = spawners.get(spawnerBlock);
            if (nearbyNum < max){
                double x = loc.getX();
                int dy = Integer.parseInt(line4.trim().split(",")[0]);
                double y = loc.getY();
                double z = loc.getZ();
                x = x+ 0.5;
                y = y + dy;
                z = z+ 0.5;
                loc.setX(x);
                loc.setY(y);
                loc.setZ(z);
                MonsterManager.spawnMonster(line1.trim(), loc);
            }
        }
    }
}
