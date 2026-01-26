package Ink_server.PlayerJoinLand;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import Ink_server.StaticDatas.Factions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChooseRace {
    private static final Map<Double, String> RaceMap = new HashMap<>();
    private static final World world = Bukkit.getWorld("world");
    static {
        RaceMap.put(0.0, "神族"); RaceMap.put(1.0, "妖族"); RaceMap.put(2.0, "仙族"); RaceMap.put(3.0, "人族"); RaceMap.put(4.0, "战神族");
    }

    public static final Map<Location, Double> raceChooser = new ConcurrentHashMap<>();
    static {
        raceChooser.put(new Location(world, 1233, 76, 42), 0.0);
        raceChooser.put(new Location(world, 1366, 77, 13), 1.0);
        raceChooser.put(new Location(world, 1277, 77, 105), 2.0);
        raceChooser.put(new Location(world, 1282, 78, -8), 3.0);
        raceChooser.put(new Location(world, 1366, 77, 70), 4.0);
    }

    public static void raceChoose(PlayerInteractEvent event, Player player, Block block, Location loc) {
        if (event.getAction() != Action.PHYSICAL) return;
        // 检查是否是踏板
        Material material = block.getType();
        if (material != Material.STONE_PRESSURE_PLATE) return;
        RaceSet(player, raceChooser.get(loc));
        event.setCancelled(true);
    }


    /**
     * 处理触发事件
     */
    private static void RaceSet(Player player, double race) {
        // 1. 创建目标位置
        Location JobLocation = new Location(ChooseRace.world, 1316.5, 63, 42.5, 90, 0);
        // 2. 设置出生点
        player.setRespawnLocation(JobLocation, true);
        // 3. 传送玩家
        player.teleport(JobLocation);
        // 4. 设置 PDC "race" 为 种族
        PlayerInfoManager.getPlayerInfo(player.getUniqueId()).setRace(race);
        // 5. 加入队伍
        addToTeam(player);
        // 6. 发送提示消息
        player.sendMessage("您选择了「" + RaceMap.get(race) + "」，接下来将选择要游玩的职业。");
    }

    //加入黄名队伍
    private static void addToTeam(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(Factions.INGAME);

        // 如果队伍不存在，创建它
        if (team == null) {
            team = scoreboard.registerNewTeam(Factions.INGAME);
            team.displayName(Component.text("§a游戏中"));
            team.color(NamedTextColor.YELLOW);
            team.setAllowFriendlyFire(false);  // 禁止队友伤害// 可以看到隐身队友
        }

        // 将玩家加入队伍
        team.addEntry(player.getName());
    }
}
