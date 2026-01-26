package Ink_server.PlayerJoinLand;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static Ink_server.PlayerJoinLand.FinalConfirm.reset;

public class ReLife {
    public static final Map<Location, Double> raceRlifer = new ConcurrentHashMap<>();
    private static final World world = Bukkit.getWorld("world");
    static {
        raceRlifer.put(new Location(world, 3160, 76, 296), 0.0);
        raceRlifer.put(new Location(world, 2812, 45, 846), 1.0);
        raceRlifer.put(new Location(world, 3189, 104 , 776), 2.0);
        raceRlifer.put(new Location(world, 1668, 137, 141), 3.0);
        raceRlifer.put(new Location(world, 3280, 20, -129), 4.0);
        raceRlifer.put(new Location(world, 205, 54, -1759), -1.0);
        raceRlifer.put(new Location(world, 205, 54, -1753), -2.0);
    }

    public static void relifer(PlayerInteractEvent event, Player player, Block block, Location loc) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL) return;
        ItemStack inHand = event.getItem();
        Material material = block.getType();
        if (material == Material.END_PORTAL_FRAME){
            if (event.getHand() != EquipmentSlot.HAND) return;
            if (inHand == null || inHand.getType() != Material.NETHER_STAR) return;
            PlayerInfo info = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
            double race = info.getRace();
            Location location;
            if (raceRlifer.get(loc) == race){
                location = new Location(world, 205.5, 55, -1755.5, 0, 0);
                player.teleport(location);
                player.sendMessage(Component.text("生命诚可贵，请慎重决定", NamedTextColor.DARK_RED));
                inHand.setAmount(inHand.getAmount() - 1);
            }else {
                player.sendMessage(Component.text("你无法使用其他种族的重生祭坛", NamedTextColor.RED));
            }
        }else if (material == Material.STONE_PRESSURE_PLATE){
            if (raceRlifer.get(loc) == -1.0){
                player.setHealth(0);
            } else if (raceRlifer.get(loc) == -2.0){
                reset(event, player);
                Bukkit.broadcast(Component.text("玩家" + player.getName() + "选择了放弃一切，从头来过", NamedTextColor.DARK_RED));
                player.getInventory().clear();
                player.setExperienceLevelAndProgress(0);
                player.clearActivePotionEffects();
            }

        }
    }
}
