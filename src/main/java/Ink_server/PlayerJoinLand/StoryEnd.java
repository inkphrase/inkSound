package Ink_server.PlayerJoinLand;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

public class StoryEnd {
    private static final World world = Bukkit.getWorld("world");
    public static final Set<Location> storys = new HashSet<>();
    static {
        int[] X = {1225, 1273, 1321, 1369, 1417};
        for (int x : X){
            storys.add(new Location(world, x, 42, 646));
            storys.add(new Location(world, x, 42, 647));
        }
    }

    public static void endStory(PlayerInteractEvent event, Player player, Block block) {
        if (event.getAction() != Action.PHYSICAL) return;
        Material material = block.getType();
        if (material != Material.STONE_PRESSURE_PLATE) return;
        Location location = new Location(world, 1202.5, 27, -380.5, -90, 0);
        player.setRespawnLocation(location, true);
        player.teleport(location);
        event.setCancelled(true);
    }
}
