package Ink_server.EconomySystem;

import Ink_server.InkSound;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AdventureAssociation implements Listener {
    public static long UTC8 = 28800000L; // UTC+8的时间偏移量，单位为毫秒
    public static long checkMills = 21600000L; // 4小时的毫秒数
    public static long startMills = 7200000L; //每日2点开始

    public static InkSound plugin = InkSound.getInstance();

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new DeliveryStation(),plugin);
    }

    /**
     * 更新时间check，每4小时更新一次，具体时间为每天的2点、8点、14点、20点
     */
    public static Boolean shouldUpdate(){
        long time = System.currentTimeMillis();
        long currentPeriod = (time + UTC8 + startMills) / checkMills;
        long lastUpdatePeriod = GlobalData.getLastAdventureUpdatePeriod();
        if (currentPeriod > lastUpdatePeriod) {
            GlobalData.setLastAdventureUpdatePeriod(currentPeriod);
            return  true;
        }
        else return false;
    }
    /**
     * 更新委托时间与次数，次数上限为12次，每次增加6次
     */
    public static void adventureUpdate(Player player){
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        long lastTime = playerInfo.getAdventureUpdatePeriod();
        long lastUpdatePeriod = GlobalData.getLastAdventureUpdatePeriod();
        if (lastUpdatePeriod > lastTime){
            playerInfo.setAdventureUpdatePeriod(lastUpdatePeriod);
            playerInfo.setAdventureTimes(Math.min(playerInfo.getDelivertyTimes() + 6, 12));
            player.sendMessage(Component.text("委托任务次数已刷新！").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        adventureUpdate(player);
    }
}
