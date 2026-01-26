package Ink_server.House;

import Ink_server.InkSound;
import Ink_server.StaticDatas.Functions;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BuyHouse implements Listener {
    private static final InkSound plugin = InkSound.getInstance();
    private static final World world = Bukkit.getWorld("world");
    private static final int[] GLASS_SLOTS = new int[]{0,2,4,6,8};
    private static final int[] CONFIRM_GLASS_SLOTS = new int[]{0,2,3,5,6,8};
    private static final int BUY_SLOT = 1;
    private static final int SHARE_SLOT = 3;
    private static final int LEAVE_SLOT = 5;
    private static final int SEARCH_SLOT = 7;
    private static final ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final ItemMeta glassMeta = glass.getItemMeta();
    private static final ItemStack buy = new ItemStack(Material.TRIAL_KEY);
    private static final ItemMeta buyMeta = buy.getItemMeta();
    private static final ItemStack share = new ItemStack(Material.REDSTONE);
    private static final ItemMeta shareMeta = share.getItemMeta();
    private static final ItemStack leave = new ItemStack(Material.OAK_DOOR);
    private static final ItemMeta leaveMeta = leave.getItemMeta();
    private static final ItemStack search = new ItemStack(Material.BOOK);
    private static final ItemMeta searchMeta = search.getItemMeta();
    private static final ItemStack had = new ItemStack(Material.BARRIER);
    private static final ItemMeta hadMeta = had.getItemMeta();
    private static final ItemStack confirm = new ItemStack(Material.BOOK);
    private static final ItemMeta confirmMeta = confirm.getItemMeta();
    private static final ItemStack back = new ItemStack(Material.BOOK);
    private static final ItemMeta backMeta = back.getItemMeta();
    static {
        if (glassMeta != null){
            glassMeta.displayName(Component.empty());
            glassMeta.addItemFlags(ItemFlag.values());
            glass.setItemMeta(glassMeta);
        }
        if (buyMeta != null){
            buyMeta.displayName(Component.text("购买府邸").
                    color(NamedTextColor.GOLD)
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("限制等级: ").color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC,false)
                    .append(Component.text("30").color(NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false)));
            lore.add(Component.text("花费").color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC,false)
                    .append(Component.text("32").color(NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false))
                    .append(Component.text("张银票以购置房产").color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.ITALIC,false)));
            buyMeta.lore(lore);
            buyMeta.addItemFlags(ItemFlag.values());
            buyMeta.setItemModel(new NamespacedKey("inksound", "others/housekey"));
            buy.setItemMeta(buyMeta);
        }
        if (shareMeta != null){
            shareMeta.displayName(Component.text("分享府邸").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("花费").color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC,false)
                    .append(Component.text("3").color(NamedTextColor.AQUA)
                            .decoration(TextDecoration.ITALIC, false))
                    .append(Component.text("张银票").color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.ITALIC,false)));
            lore.add(Component.text("选择三格内的一名玩家分享房产").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
            shareMeta.lore(lore);
            shareMeta.addItemFlags(ItemFlag.values());
            shareMeta.setItemModel(new NamespacedKey("inksound", "others/housestone"));
            share.setItemMeta(shareMeta);
        }
        if (leaveMeta != null){
            leaveMeta.displayName(Component.text("离开府邸").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("放弃对现有房产及其内资源的使用权").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
            lore.add(Component.text("此后可重新购置或加入房产").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
            leaveMeta.lore(lore);
            leaveMeta.addItemFlags(ItemFlag.values());
            leaveMeta.setItemModel(new NamespacedKey("inksound", "others/leave"));
            leave.setItemMeta(leaveMeta);
        }
        if (searchMeta != null){
            searchMeta.displayName(Component.text("查询府邸信息").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("查询所在房产的成员").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)
                    .append(Component.text("以及房产各项信息").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)));
            searchMeta.lore(lore);
            searchMeta.addItemFlags(ItemFlag.values());
            searchMeta.setItemModel(new NamespacedKey("inksound", "others/search"));
            search.setItemMeta(searchMeta);
        }
        if (hadMeta != null){
            hadMeta.displayName(Component.text("您已拥有府邸").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false));
            hadMeta.addItemFlags(ItemFlag.values());
            hadMeta.setItemModel(new NamespacedKey("inksound", "others/housekey"));
            had.setItemMeta(hadMeta);
        }
        if (confirmMeta != null){
            confirmMeta.displayName(Component.text("确认分享").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false));
            confirmMeta.addItemFlags(ItemFlag.values());
            confirmMeta.setItemModel(new NamespacedKey("inksound", "others/confirm"));
            confirm.setItemMeta(confirmMeta);
        }
        if (backMeta != null){
            backMeta.displayName(Component.text("选择其他玩家").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            backMeta.addItemFlags(ItemFlag.values());
            backMeta.setItemModel(new NamespacedKey("inksound", "others/back"));
            back.setItemMeta(backMeta);
        }
    }

    private BuyHouse(){

    }
    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new BuyHouse(), plugin);
    }

    private static boolean isLocked(ItemStack item) {
        return item != null && item.getType() == Material.BARRIER;
    }

    public static void openBuyMenu(Player player){
        HouseInventoryHolder holder = new HouseInventoryHolder(player, MenuType.MAIN);
        Inventory inv = Bukkit.createInventory(holder, 9, Component.text("石安").color(NamedTextColor.AQUA).
                decoration(TextDecoration.ITALIC,false)
                .decoration(TextDecoration.BOLD,true));
        for (int slot : GLASS_SLOTS){
            inv.setItem(slot, glass);
        }
        ItemStack locked = EnderChestMenu.locked;
        if (PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getHouseId() >= 0){
            inv.setItem(BUY_SLOT, had);
            inv.setItem(SHARE_SLOT, share);
            inv.setItem(LEAVE_SLOT, leave);
            inv.setItem(SEARCH_SLOT, search);
        }else {
            inv.setItem(BUY_SLOT, buy);
            inv.setItem(SHARE_SLOT, locked);
            inv.setItem(LEAVE_SLOT, locked);
            inv.setItem(SEARCH_SLOT, locked);
        }
        player.openInventory(inv);
    }

    private static void openShareMenu(Player player){
        HouseInventoryHolder holder = new HouseInventoryHolder(player, MenuType.SHARE);
        Inventory inv = Bukkit.createInventory(holder, 9,
                Component.text("分享府邸").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
        Location loc = player.getLocation();
        Collection<Player> nearby = loc.getNearbyPlayers(3);
        int i = 0;
        for (Player near : nearby){
            UUID uid = near.getUniqueId();
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            if (headMeta != null){
                OfflinePlayer oP = Bukkit.getOfflinePlayer(uid);
                headMeta.setOwningPlayer(oP);
                headMeta.displayName(Component.text(player.getName()).color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
                head.setItemMeta(headMeta);
                if (oP.getPlayer() == player) continue;
                if (i<9){
                    inv.setItem(i, head);
                    i++;
                }else break;
            }
        }
        player.openInventory(inv);
    }

    private static void openShareConfirm(Player player,ItemStack itemStack){
        HouseInventoryHolder holder = new HouseInventoryHolder(player, MenuType.SHARECONFIRM);
        Inventory inv = Bukkit.createInventory(holder, 9,
                Component.text("分享府邸").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
        for (int slot : CONFIRM_GLASS_SLOTS){
            inv.setItem(slot, glass);
        }
        inv.setItem(1, confirm);
        inv.setItem(4, itemStack);
        inv.setItem(7, back);
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!(event.getInventory().getHolder() instanceof HouseInventoryHolder holder)) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        // 取消所有点击
        event.setCancelled(true);
        int slot = event.getRawSlot();
        // 点击了玩家背包
        if (slot >= 9 || slot < 0) return;
        Inventory inv = event.getInventory();
        ItemStack clicked = inv.getItem(slot);
        switch (holder.type){
            case MAIN -> handleMain(player,clicked,slot);
            case SHARE -> handleShareMenu(player,clicked,slot);
            case SHARECONFIRM -> handleShareConfirmMenu(player,slot,inv);
        }
    }

    public void handleMain(Player player, ItemStack clicked, int slot){
        if (slot < 0 || slot >= 9) return;
        switch (slot){
            case BUY_SLOT -> {
                try {
                    handleBuy(player,clicked);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case SHARE_SLOT -> handleShare(player, clicked);
            case LEAVE_SLOT -> handleLeave(player, clicked);
            case SEARCH_SLOT -> handleSearch(player,clicked);
        }
    }

    public void handleShareMenu(Player player, ItemStack clicked, int slot){
        if (slot < 0 || slot >= 9) return;
        if (clicked == null || clicked.getType() != Material.PLAYER_HEAD) return;
        if (clicked.getItemMeta() instanceof SkullMeta){
            openShareConfirm(player,clicked);
        }
    }

    public void handleShareConfirmMenu(Player player, int slot, Inventory inv){
        if (slot < 0 || slot >= 9) return;
        switch (slot){
            case 1 ->{
                SkullMeta meta = (SkullMeta) Objects.requireNonNull(inv.getItem(4)).getItemMeta();
                Player shareTo = Objects.requireNonNull(meta.getOwningPlayer()).getPlayer();
                if (shareTo == null) return;
                UUID uuid = player.getUniqueId();
                int id = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
                if (GetHouse.getHouses().isEmpty()){
                    player.closeInventory();
                    return;
                }
                var members = GetHouse.getHouseMember(id);
                if (members.size() >= 10){
                    player.sendMessage(Component.text("府邸已达十人上限！").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
                }else {
                    //将指定房号分配给玩家
                    if (PlayerInfoManager.getPlayerInfo(shareTo.getUniqueId()).getHouseId() >= 0){
                        player.sendMessage(Component.text("玩家" + shareTo.getName() + "已拥有府邸！").
                                color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
                    }else {
                        GetHouse.shareHouse(shareTo,id);
                    }
                }
                player.closeInventory();
            }
            case 7 -> openShareMenu(player);
        }
    }

    private static void handleBuy(Player player, ItemStack itemStack) throws IOException {
        if (isLocked(itemStack))return;
        player.closeInventory();
        if (PlayerInfoManager.getPlayerInfo(player.getUniqueId()).getHouseId() != -1){
            player.sendMessage(Component.text("您已拥有府邸！").
                    color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            return;
        }
        if (player.getLevel() < 30){
            player.sendMessage(Component.text("石安").
                    color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                    .append(Component.text(": 这位朋友，你的声望似乎还不够啊").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)));
            return;
        }
        if (!Functions.require(player, ItemRegistry.MONEY2, 32)){
            player.sendMessage(Component.text("石安").
                    color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                    .append(Component.text(": 小友似乎没带够钱啊").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)));
            return;
        }

        int id = GetHouse.getHouses().size();
        creatHouse(id);
        GetHouse.giveNewHouse(player);
    }

    private static void handleShare(Player player, ItemStack item){
        if (isLocked(item)) return;
        openShareMenu(player);
    }

    private static void handleLeave(Player player, ItemStack item){
        if (isLocked(item)) return;
        UUID uuid = player.getUniqueId();
        int id = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
        if (GetHouse.getHouses().isEmpty()){
            player.closeInventory();
            return;
        }
        var members = GetHouse.getHouseMember(id);
        if (members.size() <= 1){
            player.sendMessage(Component.text("您是府邸的唯一成员！").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));return;
        }else {
            //除去玩家房产号
            GetHouse.leaveHouse(player);
        }
        player.closeInventory();
    }

    private static void handleSearch(Player player, ItemStack item){
        if (isLocked(item)) return;
        UUID uuid = player.getUniqueId();
        int id = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
        if (GetHouse.getHouses().isEmpty()){
            player.closeInventory();
            return;
        }
        HouseData house = GetHouse.getHouses().get(id);
        var members = GetHouse.getHouseMember(id);
        var level = house.getFarmLevel();
        var amount = house.getFarmAmount();
        List<String> pets = house.getPets();

        player.sendMessage(Component.text("======")
                .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("府邸信息")
                        .color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false))
                .append(Component.text("======")
                        .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)));
        player.sendMessage(Component.text("府邸成员: ")
                .color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
        for (UUID member : members){
            player.sendMessage(Component.text(Objects.requireNonNull(Bukkit.getPlayer(member)).getName())
                    .color(NamedTextColor.AQUA)
                    .decoration(TextDecoration.ITALIC,false));
        }
        player.sendMessage(Component.text("田产等级: ")
                .color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)
                .append(Component.text(level)
                        .color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)));
        player.sendMessage(Component.text("作物储量: ")
                .color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)
                .append(Component.text(amount)
                        .color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)));
        player.sendMessage(Component.text("宠物: ")
                .color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
        for (String petId : pets){
            player.sendMessage(Component.text(petId)
                    .color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
        }
        player.sendMessage(Component.text("===================").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));
        player.closeInventory();
    }

    private static void creatHouse(int id) throws IOException {
        int row = id % 10;
        int col = id / 10;
        /*
        int checkx = -397 + col * 100;
        int checky = 64;
        int checkz = 1663 + row * 100;
        Location checkloc = new Location(world, checkx, checky, checkz);
        Block block = checkloc.getBlock();
        if (block.getType() != Material.AIR)return;
         */
        int x1 = -436 + col * 100;
        int y1 = 64;
        int z1 = 1606 + row * 100;
        Location loc1 = new Location(world, x1, y1, z1);
        Location loc2 = new Location(world, x1 + 48, y1, z1);
        Location loc3 = new Location(world, x1, y1, z1 + 48);
        Location loc4 = new Location(world, x1 + 48, y1, z1 + 48);
        StructureManager manager = Bukkit.getStructureManager();
        File fudi1 = new File(plugin.getDataFolder(), "structures/fudi1.nbt");
        File fudi2 = new File(plugin.getDataFolder(), "structures/fudi2.nbt");
        File fudi3 = new File(plugin.getDataFolder(), "structures/fudi3.nbt");
        File fudi4 = new File(plugin.getDataFolder(), "structures/fudi4.nbt");
        Structure fudi_1 = manager.loadStructure(fudi1);
        Structure fudi_2 = manager.loadStructure(fudi2);
        Structure fudi_3 = manager.loadStructure(fudi3);
        Structure fudi_4 = manager.loadStructure(fudi4);
        fudi_1.place(loc1, true, StructureRotation.NONE, Mirror.NONE, 0, 1.0f, new Random());
        fudi_2.place(loc2, true, StructureRotation.NONE, Mirror.NONE, 0, 1.0f, new Random());
        fudi_3.place(loc3, true, StructureRotation.NONE, Mirror.NONE, 0, 1.0f, new Random());
        fudi_4.place(loc4, true, StructureRotation.NONE, Mirror.NONE, 0, 1.0f, new Random());
    }

    private enum MenuType{
        MAIN,
        SHARE,
        SHARECONFIRM
    }

    private static class HouseInventoryHolder implements InventoryHolder {
        private final UUID owner;
        private Inventory inventory;
        private final MenuType type;

        public HouseInventoryHolder(Player owner, MenuType type) {
            this.owner = owner.getUniqueId();
            this.type = type;
        }

        @Override
        public @NonNull Inventory getInventory() {
            return inventory;
        }

        private void setInventory(Inventory inventory) {
            this.inventory = inventory;
        }

        public Player getOwner() {
            return Bukkit.getPlayer(owner);
        }
    }
}
