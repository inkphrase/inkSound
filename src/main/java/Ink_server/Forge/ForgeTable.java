package Ink_server.Forge;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NonNull;

import java.util.*;

import static java.util.Arrays.asList;

public class ForgeTable implements Listener {
    private static final InkSound plugin = InkSound.getInstance();
    private static final Set<Integer> glassSlots = new HashSet<>(asList(
            0,1,2,3,5,6,7,8,
            9,17,
            18,19,20,21,23,24,25,26));
    private static final int hammerSlot = 4;
    private static final int infoSlot = 13;
    private static final int[] materialSlot = new int[]{10,11,12,14,15,16,22};
    //设置挡板、锻造锤
    private static final ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final ItemMeta glassMeta = glass.getItemMeta();
    private static final ItemStack hammer = new ItemStack(Material.ANVIL);
    private static final ItemMeta hammerMeta = hammer.getItemMeta();
    private static final ItemStack info = new ItemStack(Material.END_CRYSTAL);
    private static final ItemMeta infoMeta = info.getItemMeta();

    static {
        if (glassMeta != null){
            glassMeta.displayName(Component.empty());
            glassMeta.addItemFlags(ItemFlag.values());
            glass.setItemMeta(glassMeta);
        }
        if (hammerMeta != null){
            hammerMeta.displayName(Component.text("点击开始锻造").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
            hammerMeta.addItemFlags(ItemFlag.values());
            var pdc = hammerMeta.getPersistentDataContainer();
            pdc.set(ItemKeys.ID, PersistentDataType.STRING, "forge_hammer");
            hammerMeta.setItemModel(new NamespacedKey("inksound","others/forgehammer"));
            hammer.setItemMeta(hammerMeta);
        }
        if (infoMeta != null){
            infoMeta.displayName(Component.text("§6§l锻造方式"));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("§b点击上方进行锻造"));
            lore.add(Component.text("§f左侧: §e核心 §7- §6强化材料1 §7- §6强化材料2"));
            lore.add(Component.text("§f右侧: §6部位材料1 §7- §6部位材料2 §7- §9元素"));
            lore.add(Component.text("§b请在下方放入装备基底"));
            infoMeta.setItemModel(new NamespacedKey("inksound", "others/forgeinfo"));
            infoMeta.lore(lore);
            info.setItemMeta(infoMeta);
        }
    }

    public static void openForgeTable(Player player){
        //创建容器，持有者为自定义持有者“锻造台使用者”
        ForgeInventoryHolder holder = new ForgeInventoryHolder(player);
        Inventory forgeTable = Bukkit.createInventory(holder, 27, Component.text("锻造台").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false));
        for (int slot : glassSlots){forgeTable.setItem(slot, glass);}
        forgeTable.setItem(hammerSlot, hammer);
        forgeTable.setItem(infoSlot, info);
        player.openInventory(forgeTable);
    }

    private ForgeTable(){

    }

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new ForgeTable(), plugin);
    }

    @EventHandler
    public static void onClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof ForgeInventoryHolder)) return;
        int slot = event.getRawSlot();
        if (slot >= 27 || slot < 0) return;
        if (glassSlots.contains(slot) || slot == infoSlot) event.setCancelled(true);
        else if (slot == hammerSlot){
            doForge(event, player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof ForgeInventoryHolder)) return;
        Inventory inventory = event.getInventory();
        for (int i : materialSlot){
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR){
                // 将物品返还给玩家
                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);

                // 如果背包满了，掉落在地上
                for (ItemStack drop : leftover.values()) {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
            }
        }
    }

    @EventHandler
    public static void openTable(InventoryOpenEvent event){
        if (event.getInventory().getType() != InventoryType.DISPENSER) return;
        if (!(event.getPlayer() instanceof Player player)) return;
        event.setCancelled(true);
        Bukkit.getScheduler().runTask(plugin, ()->openForgeTable(player));
    }


    private static class ForgeInventoryHolder implements InventoryHolder {
        private final UUID owner;
        private Inventory inventory;

        public ForgeInventoryHolder(Player owner) {
            this.owner = owner.getUniqueId();
        }

        @Override
        public @NonNull Inventory getInventory() {
            return inventory;
        }

        public void setInventory(Inventory inventory) {
            this.inventory = inventory;
        }

        public Player getOwner() {
            return Bukkit.getPlayer(owner);
        }
    }

    private static void doForge(InventoryClickEvent event, Player player){
        TempRecipeList temp = new TempRecipeList(null,null);
        Inventory inventory = event.getInventory();
        for (int i = 0; i < materialSlot.length; i++){
            ItemStack item = inventory.getItem(materialSlot[i]);
            if (item != null && item.getType() != Material.AIR && item.hasItemMeta()){
                var meta = item.getItemMeta();
                var pdc = meta.getPersistentDataContainer();
                var id = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
                if (id != null) temp.setSlotItems(i, id);
                var num = item.getAmount();
                temp.setSlotAmounts(i, num);
            }
        }
        if (Recipe.getRecipes().containsKey(temp)){
            ItemStack result = WeaponManager.getWeapon(Recipe.getRecipes().get(temp));
            if (result == null){
                player.sendMessage(Component.text("该装备暂未实装！"));
                return;
            }
            for (int slot : materialSlot){
                inventory.setItem(slot, null);
            }
            inventory.setItem(22, result);
            player.sendMessage(Component.text("锻造成功！"));
            player.playSound(player, Sound.BLOCK_ANVIL_USE, 1.0f,1.0f);
        }else{
            player.sendMessage(Component.text("请按照正确的顺序摆放材料！").color(NamedTextColor.RED));
            player.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
        }
    }
}
