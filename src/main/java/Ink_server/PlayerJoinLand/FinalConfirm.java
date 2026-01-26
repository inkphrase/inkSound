package Ink_server.PlayerJoinLand;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FinalConfirm {
    private static final World world = Bukkit.getWorld("world");
    public static final Map<Double, Location> raceSpwan = new ConcurrentHashMap<>();
    public static final Map<Location, Integer> ends = new ConcurrentHashMap<>();
    public static final Location location = new Location(world, 1315.5, 76, 42.5, -90, 0);
    static {
        raceSpwan.put(0.0, new Location(world, 3208.5, 73, 381.5, 90, 0));
        raceSpwan.put(1.0, new Location(world, 2845.5, 48, 899.5, 180, -20));
        raceSpwan.put(2.0, new Location(world, 3179.5, 127, 783.5, -90, 0));
        raceSpwan.put(3.0, new Location(world, 1689.5, 140, 138.5, 90, 0));
        raceSpwan.put(4.0, new Location(world, 3299.5, 22, -137.5, 90, 0));
        ends.put(new Location(world, 1217, 27, -381), 1);
        ends.put(new Location(world, 1202, 28, -381), 2);
    }

    public static void endCheck(PlayerInteractEvent event, Player player, Block block, Location loc) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL) return;
        // 检查是否是踏板
        Material material = block.getType();
        if (material != Material.STONE_PRESSURE_PLATE && material != Material.OAK_WALL_SIGN)return;
        switch (ends.get(loc)){
            case 1:
                getIntoLand(player);
                event.setCancelled(true);
                break;
            case 2:
                reset(event, player);
                break;
            }
    }

    public static void reset(PlayerInteractEvent event, Player player) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        player.setRespawnLocation(location, true);
        player.teleport(location);
        //重置玩家信息
        PlayerInfoManager.getManager().remove(player.getUniqueId());
        PlayerInfoManager.getManager().put(player.getUniqueId(), new PlayerInfo());
        //删除行为
        event.setCancelled(true);
        //移出队伍
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("InGame");
        if (team == null) return;
        team.removeEntries(player.getName());
        //清除物品
        player.getInventory().clear();
        player.setExperienceLevelAndProgress(0);
        player.clearActivePotionEffects();
        player.setFoodLevel(20);
        player.setHealth(20);
    }

    private static void getIntoLand(Player player){
        PlayerInfo info = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        double race = info.getRace();
        //设置奈何桥重生点，并传送
        Location location = raceSpwan.get(race);
        Location respwan = new Location(world, 205, 54, -1771);
        player.setRespawnLocation(respwan, true);
        player.teleport(location);
        //添加ingame标记，移除经验与buff
        info.setInGame(true);
        info.setInLand(true);
        player.getInventory().clear();
        player.setExperienceLevelAndProgress(0);
        player.clearActivePotionEffects();
        player.setFoodLevel(20);
        player.setHealth(20);
    }
}
