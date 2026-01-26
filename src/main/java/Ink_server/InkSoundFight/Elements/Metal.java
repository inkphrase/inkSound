package Ink_server.InkSoundFight.Elements;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.InkSoundFight.TickTimer;
import Ink_server.InkSoundFight.Fight;
import Ink_server.StaticDatas.Functions;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Random;

public class Metal {
    public static void metalEffect(Player player, EntityData playerData) {
        double multiple = playerData.getMultiple();
        double attack = playerData.getAttackData();
        int distance = attack >= 20 ? 10 : 5;
        for (Entity entity : player.getNearbyEntities(distance,distance,distance)){
            //检查活体
            if (!(entity instanceof LivingEntity livingEntity)) continue;
            //排除玩家
            if (livingEntity instanceof Player) continue;
            //排除非法
            if (!TickTimer.isCombatant(livingEntity)) continue;
            EntityData entityData = EntityDataManager.getData(livingEntity);
            if (entityData == null) continue;
            Fight.doDamage(player, livingEntity, 3 +multiple);
            break;
        }
        Location playerLoc = player.getLocation();
        for (Player other : playerLoc.getNearbyPlayers(3)){
            other.sendMessage(Component.text("☯" + player.getName() + "炼化了金元素,发动阵法-蚀骨刺☯")
                    .color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC,false));
        }
        player.playSound(playerLoc, "panling:metal", 1.0f, 1.0f);
        Fight.setCool(playerData, "metal", 50);
        if (new Random().nextDouble() >= playerData.getRecycle()){
            Functions.require(player, ItemRegistry.ELEMENT_METAL, 1);
        }
    }
}
