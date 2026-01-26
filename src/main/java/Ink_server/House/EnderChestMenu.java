package Ink_server.House;

import Ink_server.InkSound;
import Ink_server.LandTpSystem.OverLandTp;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * 末影箱菜单管理器
 * 玩家打开末影箱时显示自定义菜单
 */
public class EnderChestMenu implements Listener {

    private static final InkSound plugin = InkSound.getInstance();

    private static final World world = Bukkit.getWorld("world");

    // 槽位定义
    private static final Set<Integer> GLASS_SLOTS = new HashSet<>(asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 11, 13, 15, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
    ));

    private static final int TELEPORT_SLOT = 10;
    private static final int HOUSE_SLOT = 12;
    private static final int HOUSE_STORAGE_SLOT = 14;
    private static final int PERSONAL_STORAGE_SLOT = 16;

    // 菜单物品
    private static final ItemStack glass;
    private static final ItemStack teleport;
    private static final ItemStack houseStone;
    private static final ItemStack houseStorage;
    private static final ItemStack personalStorage;
    public static final ItemStack locked;

    static {
        // 玻璃板
        glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        if (glassMeta != null) {
            glassMeta.displayName(
                    Component.empty()
                            .decoration(TextDecoration.ITALIC, false)
            );
            glassMeta.addItemFlags(ItemFlag.values());
            glass.setItemMeta(glassMeta);
        }

        // 快捷重生晶
        teleport = new ItemStack(Material.END_CRYSTAL);
        ItemMeta teleportMeta = teleport.getItemMeta();
        if (teleportMeta != null) {
            teleportMeta.displayName(
                    Component.text("快捷重华晶")
                            .color(NamedTextColor.GREEN)
                            .decoration(TextDecoration.ITALIC, false)
            );
            teleportMeta.addItemFlags(ItemFlag.values());
            teleportMeta.setItemModel(new NamespacedKey("inksound", "others/teleportstone"));
            teleport.setItemMeta(teleportMeta);
        }

        // 府邸传送
        houseStone = new ItemStack(Material.BEACON);
        ItemMeta houseStoneMeta = houseStone.getItemMeta();
        if (houseStoneMeta != null) {
            houseStoneMeta.displayName(
                    Component.text("府邸传送")
                            .color(NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false)
            );
            houseStoneMeta.setItemModel(new NamespacedKey("inksound", "others/housestone"));
            houseStoneMeta.addItemFlags(ItemFlag.values());
            houseStone.setItemMeta(houseStoneMeta);
        }

        // 府邸仓库
        houseStorage = new ItemStack(Material.ENDER_CHEST);
        ItemMeta houseStorageMeta = houseStorage.getItemMeta();
        if (houseStorageMeta != null) {
            houseStorageMeta.displayName(
                    Component.text("府邸仓库")
                            .color(NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false)
            );
            houseStorageMeta.addItemFlags(ItemFlag.values());
            houseStorage.setItemMeta(houseStorageMeta);
        }

        // 个人仓库
        personalStorage = new ItemStack(Material.CHEST);
        ItemMeta personalStorageMeta = personalStorage.getItemMeta();
        if (personalStorageMeta != null) {
            personalStorageMeta.displayName(
                    Component.text("个人仓库")
                            .color(NamedTextColor.LIGHT_PURPLE)
                            .decoration(TextDecoration.ITALIC, false)
            );
            personalStorageMeta.addItemFlags(ItemFlag.values());
            personalStorage.setItemMeta(personalStorageMeta);
        }

        // 锁定标记
        locked = new ItemStack(Material.BARRIER);
        ItemMeta lockedMeta = locked.getItemMeta();
        if (lockedMeta != null) {
            lockedMeta.displayName(
                    Component.text("尚未解锁")
                            .color(NamedTextColor.RED)
                            .decoration(TextDecoration.ITALIC, false)
            );
            lockedMeta.addItemFlags(ItemFlag.values());
            locked.setItemMeta(lockedMeta);
        }
    }

    /**
     * 私有构造函数，防止外部实例化
     */
    private EnderChestMenu() {
        // 仅用于事件监听器注册
    }

    /**
     * 初始化末影箱菜单系统
     */
    public static void init() {
        plugin.getServer().getPluginManager().registerEvents(new EnderChestMenu(), plugin);
    }

    /**
     * 填充末影箱菜单内容
     */
    private static void fillEnderMenu(Inventory enderChest, Player player) {
        // 清空原有内容
        enderChest.clear();

        // 填充玻璃板装饰
        for (int slot : GLASS_SLOTS) {
            enderChest.setItem(slot, glass);
        }

        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        int houseId = playerInfo.getHouseId();

        // 设置功能物品
        //玩家不在大陆则全部禁用
        if (!playerInfo.isInLand()){
            enderChest.setItem(TELEPORT_SLOT, locked);
            enderChest.setItem(HOUSE_SLOT, locked);
            enderChest.setItem(HOUSE_STORAGE_SLOT, locked);
        }else {
            enderChest.setItem(TELEPORT_SLOT, teleport);

            // 根据玩家府邸状态设置物品
            if (houseId >= 0 && playerInfo.isInLand()) {
                enderChest.setItem(HOUSE_SLOT, houseStone);
                enderChest.setItem(HOUSE_STORAGE_SLOT, houseStorage);
            } else {
                enderChest.setItem(HOUSE_SLOT, locked);
                enderChest.setItem(HOUSE_STORAGE_SLOT, locked);
            }

        }

        enderChest.setItem(PERSONAL_STORAGE_SLOT, personalStorage);

    }

    /**
     * 检查是否是锁定物品
     */
    private static boolean isLocked(ItemStack item) {
        return item != null && item.getType() == Material.BARRIER;
    }

    // ==================== 事件处理 ====================

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() != InventoryType.ENDER_CHEST) return;
        if (!(event.getPlayer() instanceof Player player)) return;

        // 延迟1 tick 替换内容
        Bukkit.getScheduler().runTask(plugin, () -> {
            Inventory enderChest = player.getOpenInventory().getTopInventory();

            // 确认玩家还在查看末影箱
            if (enderChest.getType() == InventoryType.ENDER_CHEST) {
                fillEnderMenu(enderChest, player);
            }
        });
    }

    @EventHandler(priority =  EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        // 只处理末影箱界面
        if (event.getInventory().getType() != InventoryType.ENDER_CHEST) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // 取消所有点击
        event.setCancelled(true);

        int slot = event.getRawSlot();

        // 点击了玩家背包
        if (slot >= 27 || slot < 0) return;

        Inventory inv = event.getInventory();
        ItemStack clickedItem = inv.getItem(slot);
        if (isLocked(clickedItem)) return;

        // 处理各个功能按钮
        switch (slot){
            case TELEPORT_SLOT -> handleTeleportClick(player);
            case HOUSE_SLOT -> handleHouseClick(player);
            case HOUSE_STORAGE_SLOT -> handleHouseStorageClick(player);
            case PERSONAL_STORAGE_SLOT -> handlePersonalStorageClick(player);
            default -> {}
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() != InventoryType.ENDER_CHEST) return;

        // 清空末影箱内容，防止菜单物品被保存
        event.getInventory().clear();
    }

    // ==================== 功能处理方法 ====================

    /**
     * 处理重生晶点击
     */
    private void handleTeleportClick(Player player) {
        player.sendMessage(
                Component.text("重华晶尚未实装！")
                        .color(NamedTextColor.RED)
        );
    }

    /**
     * 处理府邸传送点击
     */
    private void handleHouseClick(Player player) {
        player.closeInventory();
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        if (!playerInfo.isInLand()) {
            player.sendMessage(Component.text("府邸传送石在此地没有反应").color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false));
            return;
        }
        if (playerInfo.isInHouse()){
            player.teleport(OverLandTp.landMiddle);
            player.sendMessage(Component.text("已传送至皇城中心").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false));
            playerInfo.setInHouse(false);
            return;
        }
        int id = playerInfo.getHouseId();
        if (id < 0){
            player.sendMessage(Component.text("您还没有获得府邸").color(NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC, false));
            return;
        }
        int row = id % 10;
        int col = id / 10;
        double x = -396.5 + col * 100;
        int y = 65;
        double z = 1663.5 + row * 100;
        Location loc = new Location(world,x,y,z,-180,0);
        player.teleport(loc);
        player.sendMessage(Component.text("已传送至府邸").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false));
        playerInfo.setInHouse(true);

    }

    /**
     * 处理府邸仓库点击
     */
    private void handleHouseStorageClick(Player player) {

        //player.closeInventory();
        StorageManager.openSharedStorage(player);
    }

    /**
     * 处理个人仓库点击
     */
    private void handlePersonalStorageClick(Player player) {
        //player.closeInventory();
        StorageManager.openPersonalStorage(player);
    }
}