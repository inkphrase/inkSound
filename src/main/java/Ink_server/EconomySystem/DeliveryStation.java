package Ink_server.EconomySystem;

import Ink_server.InkSound;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeliveryStation implements Listener {
    public static long UTC8 = 28800000L; // UTC+8的时间偏移量，单位为毫秒
    public static long checkMills = 14400000L; // 4小时的毫秒数

    public static InkSound plugin = InkSound.getInstance();

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new DeliveryStation(),plugin);
    }

    /**
     * 更新时间check，每4小时更新一次，具体时间为每天的0点、4点、8点、12点、16点、20点
     */
    public static Boolean shouldUpdate(){
        long time = System.currentTimeMillis();
        long currentPeriod = (time + UTC8) / checkMills;
        long lastUpdatePeriod = GlobalData.getLastDeliveryUpdatePeriod();
        if (currentPeriod > lastUpdatePeriod) {
            GlobalData.setLastDeliveryUpdatePeriod(currentPeriod);
            return  true;
        }
        else return false;
    }
    /**
     * 更新跑商时间与次数，次数上限为12次，每次增加4次
     */
    public static void deliveryUpdate(Player player){
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        long lastTime = playerInfo.getDeliveryUpdatePeriod();
        long lastUpdatePeriod = GlobalData.getLastDeliveryUpdatePeriod();
        if (lastUpdatePeriod > lastTime){
            playerInfo.setDelivertyTimes(Math.min(playerInfo.getDelivertyTimes() + (int)(lastUpdatePeriod - lastTime) * 4, 12));
            playerInfo.setDeliveryUpdatePeriod(lastUpdatePeriod);
            player.sendMessage(Component.text("跑商次数已刷新！").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        deliveryUpdate(player);
    }
}
