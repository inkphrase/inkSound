package Ink_server.LandTpSystem;

import Ink_server.InkSound;
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

public class RaceTp implements Listener {
    private static final World world = Bukkit.getWorld("world");

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RaceTp(), plugin);
    }

    private RaceTp(){}

    //存储踏板点-传送点的映射
    private static final Map<Location, Location> racePort = new ConcurrentHashMap<>();
    static {
        racePort.put(new Location(world, 3162, 145, 106), new Location(world, 226.5, 49, 37.5, 90, 0));
        racePort.put(new Location(world, 3167, 52, 939), new Location(world, 220.5, 49, 40.5, 180, 0));
        racePort.put(new Location(world, 2853, 53, 830), new Location(world, 402, 50, 86, 100, 0));
        racePort.put(new Location(world, 229, 49, 37), new Location(world, 3167.5, 145, 106.5, -90, 0));
        racePort.put(new Location(world, 220, 49, 43), new Location(world, 3171.5, 52, 939.5, -90, 0));
        racePort.put(new Location(world, 220, 49, 31), new Location(world, 1595.5, 140, 123.5, -90, 0));
        racePort.put(new Location(world, 408, 60, 82), new Location(world, 2851.5, 53, 830.5, 90, 0));
        racePort.put(new Location(world, -30, 26, 150), new Location(world, 3200.5, 19, -339.5, -20, 0));
        for (int z = 120; z <= 126; z++){
            racePort.put(new Location(world, 1598, 132, z), new Location(world, 220.5, 49, 34.4, 0, 0));
        }
        for (int x = 3197; x <= 3202; x++){
            racePort.put(new Location(world, x, 19, -346), new Location(world, -28.5, 27, 148.5, -140, -30));
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
        if (racePort.containsKey(loc)){
            Location spwanLoc = racePort.get(loc);
            player.teleport(spwanLoc);
            event.setCancelled(true);
        }
    }
}
