package Ink_server.LandTpSystem;

import Ink_server.InkSound;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OverLandTp implements Listener {
    private static final World world = Bukkit.getWorld("world");
    public static final Location landMiddle = new Location(world, 179.5, 42, 65.5, -180, 0);

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new OverLandTp(), plugin);
    }

    private OverLandTp(){}

    //存储踏板点-传送点的映射
    private static final Map<Location, Location> teleport = new ConcurrentHashMap<>();
    static {
        //铁匠铺
        for (int z = 155; z <= 157; z++){
            teleport.put(new Location(world, 79, 33, z), new Location(world, 75.5, 46, 142.5, -90, 0));
        }
        for (int z = 141; z <= 143; z++){
            teleport.put(new Location(world, 73, 47, z), new Location(world, 80.5, 33, 156.5, -90, 0));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        // 检查是否点击了方块
        if (block == null) return;
        Material material = block.getType();
        if (material != Material.STONE_PRESSURE_PLATE) return;
        Location loc = block.getLocation();
        if (teleport.containsKey(loc)){
            Location spwanLoc = teleport.get(loc);
            player.teleport(spwanLoc);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void houseTp(PlayerInteractEvent event){
        if (event.getAction() != Action.PHYSICAL) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        // 检查是否点击了方块
        if (block == null) return;
        Material material = block.getType();
        if (material == Material.POLISHED_BLACKSTONE_PRESSURE_PLATE){
            player.teleport(landMiddle);
            PlayerInfoManager.getPlayerInfo(player.getUniqueId()).setInHouse(false);
            player.sendMessage(Component.text("已传送至皇城中心").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false));
            event.setCancelled(true);
        }else if (material == Material.PALE_OAK_PRESSURE_PLATE){
            Location loc = player.getLocation();
            if (loc.getY() < 72){
                loc.setY(75);
                loc.setYaw((loc.getYaw() - 180)%360);
                player.teleport(loc);
            }else {
                loc.setY(66);
                player.teleport(loc);
            }
        }
    }
}
