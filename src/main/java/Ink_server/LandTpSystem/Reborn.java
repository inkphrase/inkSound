package Ink_server.LandTpSystem;

import Ink_server.InkSound;
import Ink_server.PlayerJoinLand.FinalConfirm;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Reborn implements Listener {
    private static final World world = Bukkit.getWorld("world");
    private static final Location raceReborn = new Location(world, 208, 54, -1788);
    private static final Location pointReborn = new Location(world, 202, 54, -1788);
    private static final Location[] pointList = new Location[]{
            new Location(world, 179.5, 42, 65.5, -180, 0),
            new Location(world, 687.5, 70, 115.5, -42, 7),
            new Location(world, -327.5, 58, 594.5, -153, 1),
            new Location(world, -417.5, 112, 164.5, -180, 0),
            new Location(world, -330.5, 24, -696.5, 0, 0)


    };
    private static final Map<Location, Integer> pointMap = new HashMap<>();
    static {
        pointMap.put(new Location(world, 179, 43, 63), 0);
        pointMap.put(new Location(world, 686, 71, 113), 1);
        pointMap.put(new Location(world, -329, 59, 588), 2);
        pointMap.put(new Location(world, -420, 113, 159), 3);
        pointMap.put(new Location(world, -333, 20, -701), 4);
    }

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new Reborn(), plugin);
    }

    private Reborn(){}

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        // 检查是否点击了方块
        if (block == null) return;
        Material material = block.getType();
        if (material != Material.END_PORTAL_FRAME) return;
        Location loc = block.getLocation();
        //ItemStack inHand = event.getItem();
        if (loc.equals(raceReborn)){
            Double race = PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getRace();
            Location spwanLoc = FinalConfirm.raceSpwan.get(race);
            player.teleport(spwanLoc);
        } else if (loc.equals(pointReborn)) {
            rebornToPoint(player);
        }
        PlayerInfoManager.getPlayerInfo(player.getUniqueId()).setInLand(true);
    }

    @EventHandler
    public void setRebronPoint(PlayerInteractEvent event){
        if (event.getAction() != Action.PHYSICAL) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block == null) return;
        Material material = block.getType();
        if (material != Material.OAK_PRESSURE_PLATE) return;
        Location location = block.getLocation();
        if (pointMap.containsKey(location)){
            PlayerInfoManager.getPlayerInfo(player.getUniqueId()).setRebornPlace(pointMap.get(location));
            player.sendMessage(Component.text("重生点已设置！").color(NamedTextColor.GREEN));
            spawnAuraBeam(block);
        }
    }

    private void rebornToPoint(Player player){
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if (mainhand.getType() != Material.NETHER_STAR){
            player.sendMessage(Component.text("请手持重生石！").color(NamedTextColor.RED));
            return;
        }
        int amount = mainhand.getAmount();
        if (amount == 1){
            player.getInventory().setItemInMainHand(null);
        }else {
            mainhand.setAmount(amount - 1);
        }
        int point = PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getRebornPlace();
        if (point == -1){
            Double race = PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getRace();
            Location spwanLoc = FinalConfirm.raceSpwan.get(race);
            player.teleport(spwanLoc);
        }else {
            Location rebornLoc = pointList[point];
            player.teleport(rebornLoc);
        }
    }

    private void spawnAuraBeam(Block block) {
        Location center = block.getLocation().add(0.5, 0.5, 0.5); // 获取方块中心
        World world = center.getWorld();

        // 向上延伸 16 格
        for (double y = 0; y < 16; y += 0.5) {
            Location particleLoc = center.clone().add(0, y, 0);

            /* * spawnParticle 参数说明:
             * count: 10 (每 0.5 格高度产生 10 个粒子)
             * offsetX/Y/Z: 0.2, 0.5, 0.2 (控制粒子束的粗细和随机偏移)
             * extra: 0.1 (粒子的运动速度/离散度)
             */
            world.spawnParticle(
                    Particle.HAPPY_VILLAGER,
                    particleLoc,
                    30,    // 数量越多，光束越浓密
                    0.4,   // X 轴偏移（束的宽度）
                    0.5,   // Y 轴偏移
                    0.4,   // Z 轴偏移（束的厚度）
                    0.001   // 额外速度
            );
        }
    }

}
