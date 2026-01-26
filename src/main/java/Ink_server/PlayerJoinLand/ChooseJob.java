package Ink_server.PlayerJoinLand;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChooseJob {
    private static final World world = Bukkit.getWorld("world");
    private static final Map<Double, String> JobMap = new HashMap<>();
    static {
        JobMap.put(0.0, "战士"); JobMap.put(1.0, "弓箭手"); JobMap.put(2.0, "炼丹师");
    }
    public static final Map<Location, Double> jobChooser = new ConcurrentHashMap<>();
    static {
        for (int z =40; z <= 44; z +=2){
            jobChooser.put(new Location(world, 1324, 63, z), (double)(z - 40) / 2);
        }
    }

    public static void jobChoose(PlayerInteractEvent event, Player player, Block block, Location loc) {
        if (event.getAction() != Action.PHYSICAL) return;
        Material material = block.getType();
        if (material != Material.STONE_PRESSURE_PLATE) return;
        double job = jobChooser.get(loc);
        JobSetter(player, job);
        event.setCancelled(true);
    }

    private static void JobSetter(Player player, double job) {
        // 1. 创建目标位置
        Location location = new Location(ChooseJob.world, 1199.5, 42, 647, -90, 0);
        double race = PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getRace();
        switch ((int)race) {
            case 1 -> location.setX(1247.5);
            case 2 -> location.setX(1295.5);
            case 3 -> location.setX(1391.5);
            case 4 -> location.setX(1343.5);
        }
        // 2. 设置出生点
        player.setRespawnLocation(location, true);
        // 3. 传送玩家
        player.teleport(location);
        // 4. 设置 PDC "job" 为 职业
        PlayerInfoManager.getPlayerInfo(player.getUniqueId()).setJob(job);

        // 6. 发送提示消息
        player.sendMessage("您选择了「" + JobMap.get(job) + "」。");
    }
}
