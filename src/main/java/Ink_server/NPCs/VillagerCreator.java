package Ink_server.NPCs;

import Ink_server.InkSound;
import Ink_server.StaticDatas.Factions;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.NPCID;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 村民生成器系统
 * 使用下界合金锄头创建自定义村民
 */
public class VillagerCreator implements Listener {

    private static final InkSound plugin = InkSound.getInstance();
    private static final NamespacedKey villagerIdKey = ItemKeys.ID;

    // 存储正在创建村民的玩家数据
    private static final Map<UUID, VillagerSetup> setupMap = new ConcurrentHashMap<>();

    // 等待输入的玩家（UUID -> 输入类型）
    private static final Map<UUID, InputType> waitingForInput = new ConcurrentHashMap<>();

    // 村民ID映射表（示例数据，你需要根据实际情况修改）
    private static final Map<String, String> villagerIdMap = NPCID.NPCMap;

    private static final Map<String, NamedTextColor> colorMap = new HashMap<>();
    static {
        colorMap.put("green", NamedTextColor.GREEN);
        colorMap.put("gray", NamedTextColor.GRAY);
        colorMap.put("red", NamedTextColor.RED);
        colorMap.put("aqua", NamedTextColor.AQUA);
        colorMap.put("gold", NamedTextColor.GOLD);
        colorMap.put("white", NamedTextColor.WHITE);
        colorMap.put("purple", NamedTextColor.LIGHT_PURPLE);
        colorMap.put("blue", NamedTextColor.BLUE);
        colorMap.put("darkgray", NamedTextColor.DARK_GRAY);
    }

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new VillagerCreator(), plugin);

        // 初始化队伍
        initNPCTeam();

        plugin.getLogger().info("NPC生成工具已加载！");
    }

    private VillagerCreator() {
    }

    /**
     * 初始化NPC队伍
     */
    private static void initNPCTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(Factions.NPCS);

        if (team == null) {
            team = scoreboard.registerNewTeam(Factions.NPCS);
            plugin.getLogger().info("创建了NPC队伍: plNPC");
        }

        // 禁用碰撞
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        plugin.getLogger().info("NPC队伍初始化完成，碰撞已禁用");
    }


    // ==================== 交互事件 ====================

    /**
     * 下蹲右键地板：创建村民
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();

        // 检查是否是OP
        if (!player.isOp()) return;

        // 检查是否手持下界合金锄头
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.NETHERITE_HOE) return;

        // 检查是否下蹲
        if (!player.isSneaking()) return;

        // 检查是否右键方块
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        // 检查是否点击上表面
        if (event.getBlockFace() != BlockFace.UP) return;

        event.setCancelled(true);
        // 防止触发原版方块交互（比如你点的是木桶或箱子）
        event.setUseInteractedBlock(Event.Result.DENY);
        // 防止触发原版工具逻辑（虽然锄头点地板一般没事，但养成好习惯）
        event.setUseItemInHand(Event.Result.DENY);

        // 获取生成位置（方块上方）
        Location spawnLoc = block.getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5);

        // 创建或重置村民设置
        VillagerSetup setup = new VillagerSetup(spawnLoc);
        setupMap.put(player.getUniqueId(), setup);

        // 打开主菜单
        openMainMenu(player);

        player.sendMessage(Component.text("已设置村民生成位置").color(NamedTextColor.GREEN));
    }

    /**
     * 下蹲右键村民：移除队伍并删除
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        // 检查是否是OP
        if (!player.isOp()) return;

        // 检查是否手持下界合金锄头
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.NETHERITE_HOE) return;

        // 检查是否下蹲
        if (!player.isSneaking()) return;

        // 检查是否是村民
        if (event.getRightClicked().getType() != EntityType.VILLAGER) return;

        event.setCancelled(true);

        // 建议增加：从队伍中移除
        Entity villager = event.getRightClicked();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(Factions.NPCS);
        if (team != null) {
            team.removeEntry(villager.getUniqueId().toString()); // 清理死数据
        }

        // 删除实体
        villager.remove();
        player.sendMessage(Component.text("已删除村民并清理队伍记录").color(NamedTextColor.RED));
    }

    /**
     * 左键：切换静音
     */
    @EventHandler
    public void onVillagerSilence(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(event.getDamager() instanceof Player player)) return;
        if (!player.isOp())return;
        if (player.isSneaking()) return;
        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_HOE))) return;
        if (villager.isSilent()){
            villager.setSilent(false);
            player.sendMessage("已取消静音");
        }else {
            villager.setSilent(true);
            player.sendMessage("已设为静音");
        }
    }

    /**
     * 右键：管理交易
     */
    @EventHandler
    public void onVillagerManage(PlayerInteractEntityEvent event){
        //检测村民
        if (!(event.getRightClicked() instanceof Villager villager)) return;
        Player player = event.getPlayer();
        //检测op、下蹲状态
        if (!player.isOp())return;
        if (player.isSneaking()) return;
        //检测工具
        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_HOE))) return;
        String id = villager.getPersistentDataContainer().get(ItemKeys.ID, PersistentDataType.STRING);
        if (id == null || !NPCID.NPCMap.containsKey(id)) return;
        Component title = villager.customName();
        event.setCancelled(true);
        openTradeMenu(player, id, title);
    }

    // ==================== 菜单系统 ====================

    /**
     * 打开主菜单
     */
    private static void openMainMenu(Player player) {
        VillagerSetup setup = setupMap.get(player.getUniqueId());
        if (setup == null) return;

        VillagerMenuHolder holder = new VillagerMenuHolder(player, MenuType.MAIN, null);
        Inventory menu = Bukkit.createInventory(holder, 27,
                Component.text("村民生成器").color(NamedTextColor.AQUA));

        // 填充玻璃板
        ItemStack glass = createItem(Material.GRAY_STAINED_GLASS_PANE, "");
        for (int i = 0; i < 27; i++) {
            if (i != 10 && i != 12 && i != 14 && i != 16 && i != 22) {
                menu.setItem(i, glass);
            }
        }

        // 村民类型
        ItemStack professionItem = createItem(
                Material.ARMOR_STAND,
                "§a选择村民类型",
                "§7当前: §e" + getProfessionName(setup.profession)
        );
        menu.setItem(10, professionItem);

        // 村民ID
        List<Component> idLore = new ArrayList<>();
        idLore.add(Component.text("当前: ")
                .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text((setup.customId != null ? setup.customId : "未设置"))
                        .color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC,false)));
        idLore.add(Component.text("名称: ")
                .color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text((setup.displayName))
                        .color((colorMap.get(setup.color) != null) ? colorMap.get(setup.color) : NamedTextColor.RED)
                        .decoration(TextDecoration.ITALIC,false)));
        ItemStack idItem = createItem(
                Material.NAME_TAG,
                "§a选择村民ID"
        );
        ItemMeta idMeta = idItem.getItemMeta();
        idMeta.lore(idLore);
        idItem.setItemMeta(idMeta);
        menu.setItem(12, idItem);

        // 村民朝向
        ItemStack rotationItem = createItem(
                Material.COMPASS,
                "§a选择村民朝向",
                "§7当前: §e" + setup.yaw + "°"
        );
        menu.setItem(14, rotationItem);

        //选择颜色
        ItemStack colorItem = new ItemStack(Material.REDSTONE);
        ItemMeta colorMeta = colorItem.getItemMeta();
        if (colorMap.get(setup.color) != null){
            colorMeta.displayName(Component.text(setup.color.toUpperCase()).color(colorMap.get(setup.color)).decoration(TextDecoration.ITALIC,false));
        }else {
            colorMeta.displayName(Component.text("选择名字颜色").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false));
        }
        colorItem.setItemMeta(colorMeta);
        menu.setItem(16, colorItem);

        // 确认生成
        ItemStack confirmItem = createItem(
                Material.BOOK,
                "§a§l确认生成"
        );
        ItemMeta confirmMeta = confirmItem.getItemMeta();
        confirmMeta.setItemModel(new NamespacedKey("inksound", "others/confirm"));
        confirmItem.setItemMeta(confirmMeta);
        menu.setItem(22, confirmItem);

        player.openInventory(menu);
    }

    /**
     * 打开职业选择菜单
     */
    private static void openProfessionMenu(Player player) {
        VillagerMenuHolder holder = new VillagerMenuHolder(player, MenuType.PROFESSION, null);
        Inventory menu = Bukkit.createInventory(holder, 54,
                Component.text("选择村民职业").color(NamedTextColor.AQUA));

        // 添加所有职业选项
        Villager.Profession[] professions = {
                Villager.Profession.NITWIT,
                Villager.Profession.FARMER,
                Villager.Profession.FISHERMAN,
                Villager.Profession.SHEPHERD,
                Villager.Profession.FLETCHER,
                Villager.Profession.LIBRARIAN,
                Villager.Profession.CARTOGRAPHER,
                Villager.Profession.CLERIC,
                Villager.Profession.ARMORER,
                Villager.Profession.WEAPONSMITH,
                Villager.Profession.TOOLSMITH,
                Villager.Profession.BUTCHER,
                Villager.Profession.LEATHERWORKER,
                Villager.Profession.MASON
        };
        int slot = 10;

        for (Villager.Profession profession : professions) {
            if (profession == Villager.Profession.NONE) continue;

            ItemStack head = createProfessionHead(profession);
            menu.setItem(slot, head);
            slot++;

            if (slot % 9 >= 7) slot += 3;  // 跳过边缘
        }

        // 返回按钮
        ItemStack back = createItem(Material.ARROW, "§c返回");
        menu.setItem(49, back);

        player.openInventory(menu);
    }

    /**
     * 打开交易管理菜单
     */
    private static void openTradeMenu(Player player, String id, Component title){
        VillagerMenuHolder holder = new VillagerMenuHolder(player, MenuType.TRADEMANAGER, id);
        Inventory inventory = Bukkit.createInventory(holder, 54, title);
        //放置玻璃
        int[] GLASS_SLOTS = new int[]{4,13,22,31,40};
        ItemStack glass = createItem(Material.GRAY_STAINED_GLASS_PANE, "");
        for (int i : GLASS_SLOTS){inventory.setItem(i,glass);}
        //放置确认按钮
        ItemStack confirm = new ItemStack(Material.BOOK);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(Component.text("确认交易项").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false));
        confirmMeta.setItemModel(new NamespacedKey("inksound", "others/confirm"));
        confirm.setItemMeta(confirmMeta);
        inventory.setItem(49, confirm);
        getTrades(id, inventory);
        player.openInventory(inventory);
    }

    private static void getTrades(String id, Inventory inventory) {
        List<VillagerTradeManager.TradeRecipe> trades = VillagerTradeManager.getTrades(id);
        if (trades==null || trades.isEmpty()) return;
        for (int i = 0; i < Math.min(12,trades.size());i++){
            VillagerTradeManager.TradeRecipe trade = trades.get(i);
            int row = i%6;
            int col = i/6;
            int inputSlot1 = row*9 + col*5;
            int inputSlot2 = inputSlot1 + 1;
            int resultSlot = inputSlot2 + 2;
            inventory.setItem(inputSlot1, trade.getInput1());
            inventory.setItem(inputSlot2, trade.getInput2());
            inventory.setItem(resultSlot, trade.getResult());
        }
    }


    // ==================== 菜单点击处理 ====================

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof VillagerMenuHolder holder)) return;
        if (holder.type == MenuType.TRADEMANAGER) {
            handleTradeManager(player, event, event.getRawSlot(), holder.id);
            return;
        }

        event.setCancelled(true);

        int slot = event.getRawSlot();
        if (slot >= event.getInventory().getSize() || slot < 0) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        VillagerSetup setup = setupMap.get(player.getUniqueId());
        if (setup == null) return;

        switch (holder.type) {
            case MAIN -> handleMainMenuClick(player, setup, slot);
            case PROFESSION -> handleProfessionMenuClick(player, setup, clicked);
        }
    }

    /**
     * 处理主菜单点击
     */
    private static void handleMainMenuClick(Player player, VillagerSetup setup, int slot) {
        switch (slot) {
            case 10: // 选择职业
                openProfessionMenu(player);
                break;

            case 12: // 选择ID
                player.closeInventory();
                waitingForInput.put(player.getUniqueId(), InputType.ID);
                player.sendMessage(Component.text("请在聊天栏输入村民ID").color(NamedTextColor.YELLOW));
                break;

            case 14: // 选择朝向
                player.closeInventory();
                waitingForInput.put(player.getUniqueId(), InputType.ROTATION);
                player.sendMessage(Component.text("请输入朝向角度（0-360）").color(NamedTextColor.YELLOW));
                break;

            case 16:
                player.closeInventory();
                waitingForInput.put(player.getUniqueId(), InputType.COLOR);
                player.sendMessage(Component.text("请在聊天栏输入颜色").color(NamedTextColor.YELLOW));
                break;

            case 22: // 确认生成
                if (spawnVillager(player, setup)) {
                    setupMap.remove(player.getUniqueId());
                    player.closeInventory();
                    player.sendMessage(Component.text("村民生成成功！").color(NamedTextColor.GREEN));
                } else {
                    player.sendMessage(Component.text("生成失败，请检查设置").color(NamedTextColor.RED));
                }
                break;
        }
    }

    /**
     * 处理职业菜单点击
     */
    private static void handleProfessionMenuClick(Player player, VillagerSetup setup, ItemStack clicked) {
        if (clicked.getType() == Material.ARROW) {
            // 返回主菜单
            openMainMenu(player);
            return;
        }
        var type = clicked.getType();
        Villager.Profession prof = handleType(type);
        if (prof == null) return;
        setup.profession = prof;
        player.sendMessage(Component.text("已设置职业: " + getProfessionName(prof))
                .color(NamedTextColor.GREEN));
        openMainMenu(player);
    }

    /**
     * 处理设置交易
     */
    private static void handleTradeManager(Player player ,InventoryClickEvent event, int slot, String id){
        if (slot < 0 || slot >= 54) return;
        BitSet bitSet = new BitSet();
        for (int i = 4; i <= 40; i += 9) {
            bitSet.set(i);
        }
        if (bitSet.get(slot)) event.setCancelled(true);
        if (slot == 49){
            setTrades(id, event.getInventory());
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    private static void setTrades(String id, Inventory inv){
        List<VillagerTradeManager.TradeRecipe> trades = new ArrayList<>();
        for (int i = 0; i < 12; i++){
            int row = i%6;
            int col = i/6;
            int inputSlot1 = row*9 + col*5;
            int inputSlot2 = inputSlot1 + 1;
            int resultSlot = inputSlot2 + 2;
            ItemStack input1 = inv.getItem(inputSlot1);
            ItemStack input2 = inv.getItem(inputSlot2);
            ItemStack result = inv.getItem(resultSlot);
            if (input1 != null && result != null){
                VillagerTradeManager.TradeRecipe trade = new VillagerTradeManager.TradeRecipe(input1,input2,result);
                trades.add(trade);
            }
        }
        VillagerTradeManager.setTrades(id, trades);
    }

    // ==================== 聊天输入处理 ====================

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        InputType inputType = waitingForInput.remove(player.getUniqueId());

        if (inputType == null) return;

        event.setCancelled(true);
        String input = PlainTextComponentSerializer.plainText().serialize(event.message()).trim();

        VillagerSetup setup = setupMap.get(player.getUniqueId());
        if (setup == null) return;

        // 同步处理
        Bukkit.getScheduler().runTask(plugin, () -> {
            switch (inputType) {
                case ID -> handleIdInput(player, setup, input);
                case ROTATION -> handleRotationInput(player, setup, input);
                case COLOR -> handleColorInput(player, setup, input);
            }
        });
    }

    /**
     * 处理ID输入
     */
    private static void handleIdInput(Player player, VillagerSetup setup, String input) {
        if (villagerIdMap.containsKey(input)) {
            setup.customId = input;
            setup.displayName = villagerIdMap.get(input);
            player.sendMessage(Component.text("已设置村民ID: " + input + " -> " + setup.displayName)
                    .color(NamedTextColor.GREEN));
        } else {
            player.sendMessage(Component.text("无效的村民ID: " + input)
                    .color(NamedTextColor.RED));
        }

        // 重新打开菜单
        openMainMenu(player);
    }

    /**
     * 处理朝向输入
     */
    private static void handleRotationInput(Player player, VillagerSetup setup, String input) {
        try {
            float yaw = Float.parseFloat(input);

            if (yaw < 0 || yaw > 360) {
                player.sendMessage(Component.text("朝向必须在 0-360 之间")
                        .color(NamedTextColor.RED));
            } else {
                setup.yaw = yaw;
                player.sendMessage(Component.text("已设置朝向: " + yaw + "°")
                        .color(NamedTextColor.GREEN));
            }
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("请输入有效的数字")
                    .color(NamedTextColor.RED));
        }

        // 重新打开菜单
        openMainMenu(player);
    }

    /**
     * 处理颜色输入
     */
    private static void handleColorInput(Player player, VillagerSetup setup, String input){
        if (colorMap.containsKey(input.toLowerCase())){
            setup.color = input;
            player.sendMessage(Component.text("已设置名字颜色: ").color(NamedTextColor.GREEN)
                    .append(Component.text(input).color(colorMap.get(input))));
        } else {
            player.sendMessage(Component.text("无效的颜色: " + input)
                    .color(NamedTextColor.RED));
        }
        openMainMenu(player);
    }

    // ==================== 菜单关闭处理 ====================

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof VillagerMenuHolder)) return;

        // 如果正在等待输入，不做处理
        if (waitingForInput.containsKey(player.getUniqueId())) return;
    }

    // ==================== 村民生成 ====================

    /**
     * 生成村民
     */
    private static boolean spawnVillager(Player player, VillagerSetup setup) {
        if (!player.isOp()) return false;
        if (setup.location == null) return false;
        if (setup.customId == null || setup.displayName == null){
            player.sendMessage(Component.text("请选择NPC类型").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
            return false;
        }

        try {
            // 生成村民
            Villager villager = (Villager) setup.location.getWorld()
                    .spawnEntity(setup.location, EntityType.VILLAGER);

            // 设置职业
            villager.setProfession(setup.profession);

            // 设置朝向
            Location loc = villager.getLocation();
            loc.setYaw(setup.yaw);
            villager.teleport(loc);

            // 设置自定义ID（如果有）
            villager.getPersistentDataContainer().set(
                    villagerIdKey,
                    PersistentDataType.STRING,
                    setup.customId
            );

            villager.customName(Component.text(setup.displayName)
                    .color((colorMap.get(setup.color) != null) ? colorMap.get(setup.color) : NamedTextColor.RED)
                    .decoration(TextDecoration.ITALIC,false)
                    .decoration(TextDecoration.BOLD, true));


            villager.setRecipes(Collections.emptyList());
            villager.setVillagerLevel(5); // 设为最高级，防止升级触发刷新
            villager.setVillagerExperience(0);
            villager.setCollidable(false);
            villager.setAI(false);
            villager.setInvulnerable(true);

            addToNPCTeam(villager);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加实体到NPC队伍
     */
    private static void addToNPCTeam(Villager villager) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(Factions.NPCS);

        if (team != null) {
            team.addEntry(villager.getUniqueId().toString());
            plugin.getLogger().info("已将村民添加到plNPC队伍");
        }
    }

    /**
     * 取消伤害村民事件
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHurt(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if (!entity.getType().equals(EntityType.VILLAGER)) return;
        event.setCancelled(true);
    }

    @EventHandler(priority =  EventPriority.LOWEST, ignoreCancelled = true)
    public void onTarget(EntityTargetEvent event) {
        Entity target = event.getTarget();
        if (target != null && target.getType().equals(EntityType.VILLAGER)) {
            event.setCancelled(true);
        }
    }

    // ==================== 工具方法 ====================

    /**
     * 创建物品
     */
    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name)
                    .decoration(TextDecoration.ITALIC, false));

            if (lore.length > 0) {
                List<Component> loreList = new ArrayList<>();
                for (String line : lore) {
                    loreList.add(Component.text(line)
                            .decoration(TextDecoration.ITALIC, false));
                }
                meta.lore(loreList);
            }

            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * 创建职业头颅
     */
    private static ItemStack createProfessionHead(Villager.Profession profession) {
        String key = profession.key().value();
        Material material = switch (key.toUpperCase()) {
            case "NITWIT" -> Material.GLOW_ITEM_FRAME;
            case "FARMER" -> Material.WHEAT;
            case "FISHERMAN" -> Material.FISHING_ROD;
            case "SHEPHERD" -> Material.WHITE_WOOL;
            case "FLETCHER" -> Material.BOW;
            case "LIBRARIAN" -> Material.BOOK;
            case "CARTOGRAPHER" -> Material.MAP;
            case "CLERIC" -> Material.BREWING_STAND;
            case "ARMORER" -> Material.IRON_CHESTPLATE;
            case "WEAPONSMITH" -> Material.IRON_SWORD;
            case "TOOLSMITH" -> Material.IRON_PICKAXE;
            case "BUTCHER" -> Material.COOKED_BEEF;
            case "LEATHERWORKER" -> Material.LEATHER;
            case "MASON" -> Material.STONE_BRICKS;
            default -> Material.EMERALD;
        };

        return createItem(material, "§a" + getProfessionName(profession));
    }

    private static Villager.Profession handleType(Material material){
        String type = switch (material){
            case Material.WHEAT -> "FARMER";
            case Material.FISHING_ROD -> "FISHERMAN";
            case Material.WHITE_WOOL -> "SHEPHERD";
            case Material.BOW -> "FLETCHER";
            case Material.BOOK -> "LIBRARIAN";
            case Material.MAP -> "CARTOGRAPHER";
            case Material.BREWING_STAND -> "CLERIC";
            case Material.IRON_CHESTPLATE -> "ARMORER";
            case Material.IRON_SWORD -> "WEAPONSMITH";
            case Material.IRON_PICKAXE -> "TOOLSMITH";
            case Material.COOKED_BEEF -> "BUTCHER";
            case Material.LEATHER -> "LEATHERWORKER";
            case Material.STONE_BRICKS -> "MASON";
            case Material.GLOW_ITEM_FRAME -> "NITWIT";
            default -> null;
        };

        if (type == null) return null;
        String prof = type.toLowerCase();
        NamespacedKey key = NamespacedKey.minecraft(prof);
        return Registry.VILLAGER_PROFESSION.get(key);
    }

    /**
     * 获取职业中文名
     */
    private static String getProfessionName(Villager.Profession profession) {
        String key = profession.key().value();
        return switch (key.toUpperCase()) {
            case "FARMER" -> "农民";
            case "FISHERMAN" -> "渔夫";
            case "SHEPHERD" -> "牧羊人";
            case "FLETCHER" -> "制箭师";
            case "LIBRARIAN" -> "图书管理员";
            case "CARTOGRAPHER" -> "制图师";
            case "CLERIC" -> "牧师";
            case "ARMORER" -> "盔甲师";
            case "WEAPONSMITH" -> "武器师";
            case "TOOLSMITH" -> "工具师";
            case "BUTCHER" -> "屠夫";
            case "LEATHERWORKER" -> "皮革师";
            case "MASON" -> "石匠";
            case "NITWIT" -> "皇城人";
            default -> "无职业";
        };
    }

    // ==================== 内部类 ====================

    /**
     * 村民设置数据
     */
    private static class VillagerSetup {
        Location location;
        Villager.Profession profession = Villager.Profession.FARMER;
        String customId = null;
        String displayName = "暂未设置";
        String color = null;
        float yaw = 0;

        VillagerSetup(Location location) {
            this.location = location;
        }
    }

    /**
     * 菜单类型
     */
    private enum MenuType {
        MAIN,
        PROFESSION,
        TRADEMANAGER
    }

    /**
     * 输入类型
     */
    private enum InputType {
        ID,
        ROTATION,
        COLOR
    }

    /**
     * 菜单持有者
     */
    private static class VillagerMenuHolder implements InventoryHolder {
        final Player player;
        final MenuType type;
        private Inventory inventory;
        private final String id;

        VillagerMenuHolder(Player player, MenuType type, String id) {
            this.player = player;
            this.type = type;
            this.id = id;
        }

        @Override
        public @NonNull Inventory getInventory() {
            return inventory;
        }

        public void setInventory(Inventory inventory) {
            this.inventory = inventory;
        }

        public String getId() {
            return id;
        }
    }
}
