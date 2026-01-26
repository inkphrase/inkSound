package Ink_server.StaticDatas.PlayerInformance;

import Ink_server.InkSound;
import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.UUID;

public class PlayerInfoListener implements Listener {
    private static final InkSound plugin = InkSound.getInstance();

    private PlayerInfoListener(){}

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new PlayerInfoListener(), plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 加载玩家数据
        PlayerInfo playerInfo = PlayerInfoManager.loadPlayerInfo(player);

        if (!playerInfo.isInLand()){
            Location respwan = new Location(Bukkit.getWorld("world"), 205.5, 54, -1770.5);
            plugin.getServer().getScheduler().runTask(plugin, ()-> player.teleport(respwan));
            player.sendMessage(Component.text("你被一股力量从界外拉入奈何桥").color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC,false));
        }

        // 可选：发送欢迎消息
        if (playerInfo.isInGame()) {
            player.sendMessage(Component.text("欢迎回来！大侠今天要做些什么呢？").color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC,false));
        } else {
            player.sendMessage(Component.text("欢迎！大侠要开辟怎样一片天地呢？").color(NamedTextColor.GREEN)
                    .decoration(TextDecoration.ITALIC,false));
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // 保存玩家数据
        PlayerInfoManager.savePlayerInfo(player);

        //清除临时战斗数据（保证正确存储冷却）
        EntityData entityData = EntityDataManager.getData(player); // 尝试再获取一次（仅用于检查）
        EntityDataManager.removeData(player.getUniqueId());

        if (entityData == null) {
            plugin.getLogger().warning("玩家 " + player.getName() + " 下线时 EntityData 已不存在，无法验证保存完整性");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID uuid = player.getUniqueId();
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(uuid);
        playerInfo.setInLand(false);
        playerInfo.setInHouse(false);
        EntityData playerData = EntityDataManager.getData(player);
        if (playerData == null) {
            InkSound.getInstance().getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
            return;
        }
        playerData.clearBuff();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        String cmd = event.getCommand().toLowerCase();

        if (cmd.equals("reload") || cmd.startsWith("reload ")) {
            event.setCancelled(true);
            plugin.getLogger().warning("已阻止控制台执行 reload 指令。");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();

        if (msg.equals("/reload") || msg.startsWith("/reload ")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§c该服务器已禁用 /reload 指令。");
        }
    }

}
