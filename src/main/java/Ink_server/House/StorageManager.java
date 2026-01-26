package Ink_server.House;

import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import Ink_server.InkSound;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 随身仓库系统 - 完全静态化版本
 */
public class StorageManager implements Listener {

    private static final InkSound plugin =  InkSound.getInstance();
    private static Connection connection;

    private static final Map<UUID, Inventory> personalStorages = new ConcurrentHashMap<>();
    private static final Map<Integer, Inventory> sharedStorages = new ConcurrentHashMap<>();
    private static final Map<Integer, Set<UUID>> sharedStoragePermissions = new ConcurrentHashMap<>();

    private static final int PERSONAL_STORAGE_SIZE = 54;
    private static final int SHARED_STORAGE_SIZE = 54;

    private static final Map<Integer, String> chineseNum = new HashMap<>();

    static {
        chineseNum.put(0, "零"); chineseNum.put(1, "壹"); chineseNum.put(2, "贰");
        chineseNum.put(3, "叁"); chineseNum.put(4, "肆"); chineseNum.put(5, "伍");
        chineseNum.put(6, "陆"); chineseNum.put(7, "柒"); chineseNum.put(8, "捌");
        chineseNum.put(9, "玖");
    }

    // ==================== 初始化（静态方法） ====================

    /**
     * 静态初始化方法
     */
    public static void init() {
        plugin.getServer().getPluginManager().registerEvents(new StorageManager(), plugin);
        initDatabase();
    }

    /**
     * 私有构造函数，防止外部实例化
     */
    private StorageManager() {
        // 只用于事件监听器注册
    }

    // ==================== 数据库初始化 ====================

    private static void initDatabase() {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists() && !dataFolder.mkdirs()) {
                plugin.getLogger().severe("无法创建数据文件夹，插件可能无法正常工作！");
            }

            String dbPath = plugin.getDataFolder().getAbsolutePath() + "/enderStorage.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            String createPersonalTable =
                    "CREATE TABLE IF NOT EXISTS personal_storage (" +
                            "    uuid TEXT PRIMARY KEY," +
                            "    items TEXT NOT NULL," +
                            "    last_update INTEGER DEFAULT (strftime('%s', 'now'))" +
                            ")";

            String createSharedTable =
                    "CREATE TABLE IF NOT EXISTS shared_storage (" +
                            "    storage_id INTEGER PRIMARY KEY," +
                            "    items TEXT NOT NULL," +
                            "    last_update INTEGER DEFAULT (strftime('%s', 'now'))" +
                            ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createPersonalTable);
                stmt.execute(createSharedTable);
            }

            plugin.getLogger().info("数据库初始化成功");

        } catch (SQLException e) {
            plugin.getLogger().severe("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 个人仓库 ====================

    public static void openPersonalStorage(Player player) {
        UUID uuid = player.getUniqueId();

        Inventory storage = personalStorages.computeIfAbsent(uuid, k -> {
            Inventory inv = createPersonalInventory(player);
            loadPersonalStorageFromDB(uuid, inv);
            return inv;
        });

        player.openInventory(storage);
    }

    public static Inventory createPersonalInventory(Player player) {
        return Bukkit.createInventory(
                null,
                PERSONAL_STORAGE_SIZE,
                Component.text("§6§l" + player.getName() + "的仓库")
        );
    }

    // ==================== 共享仓库 ====================

    public static void openSharedStorage(Player player) {
        UUID uuid = player.getUniqueId();
        int storageId = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();

        if (!hasSharedStorage(uuid)) {
            player.sendMessage("§c你还没有获得府邸！");
            return;
        }

        Inventory storage = sharedStorages.computeIfAbsent(storageId, k -> {
            Inventory inv = createSharedInventory(storageId);
            loadSharedStorageFromDB(storageId, inv);
            return inv;
        });

        player.openInventory(storage);
    }

    public static Inventory createSharedInventory(int storageId) {
        return Bukkit.createInventory(
                null,
                SHARED_STORAGE_SIZE,
                Component.text("§b§l" + toChinese(storageId) + "号府邸")
        );
    }

    public static void addSharedStoragePermission(UUID playerUuid, int storageId) {
        sharedStoragePermissions.computeIfAbsent(storageId, k -> ConcurrentHashMap.newKeySet())
                .add(playerUuid);
    }

    public static void removeSharedStoragePermission(UUID playerUuid) {
        int storageId = PlayerInfoManager.getPlayerInfo(playerUuid).getHouseId();
        Set<UUID> permissions = sharedStoragePermissions.get(storageId);
        if (permissions != null) {
            permissions.remove(playerUuid);
        }
    }

    public static boolean hasSharedStorage(UUID playerUuid) {
        int storageId = PlayerInfoManager.getPlayerInfo(playerUuid).getHouseId();
        return storageId >= 0;
    }

    // ==================== 事件处理 ====================

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        int houseId = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
        if (houseId >= 0){
            addSharedStoragePermission(uuid, houseId);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        // 保存并卸载个人仓库
        if (personalStorages.containsKey(uuid)) {
            savePersonalStorageToDB(uuid);
            personalStorages.remove(uuid);
            plugin.getLogger().info("玩家 " + player.getName() + " 的个人仓库已保存并卸载");
        }

        // 检查该玩家有权限的共享仓库
        int storageId = PlayerInfoManager.getPlayerInfo(uuid).getHouseId();
        if (storageId >= 0) {
            removeSharedStoragePermission(uuid);
            Set<UUID> permissions = sharedStoragePermissions.get(storageId);

            if (permissions == null || permissions.isEmpty()) {
                if (sharedStorages.containsKey(storageId)) {
                    saveSharedStorageToDB(storageId);
                    sharedStorages.remove(storageId);
                    plugin.getLogger().info("府邸仓库 #" + storageId + " 已保存并卸载");
                }
            }
        }
    }

    // ==================== 数据库操作 ====================

    public static void loadPersonalStorageFromDB(UUID uuid, Inventory inventory) {
        try {
            String sql = "SELECT items FROM personal_storage WHERE uuid = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String serialized = rs.getString("items");
                        ItemStack[] items = deserializeItems(serialized);
                        inventory.setContents(items);
                        plugin.getLogger().info("从数据库加载个人仓库: " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());
                    } else {
                        plugin.getLogger().info("个人仓库不存在，创建新仓库: " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());
                    }
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("加载个人仓库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void savePersonalStorageToDB(UUID uuid) {
        Inventory inventory = personalStorages.get(uuid);
        if (inventory == null) return;

        try {
            String serialized = serializeItems(inventory.getContents());
            String sql = "INSERT OR REPLACE INTO personal_storage (uuid, items, last_update) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, uuid.toString());
                pstmt.setString(2, serialized);
                pstmt.setLong(3, System.currentTimeMillis() / 1000);
                pstmt.executeUpdate();
            }

            plugin.getLogger().info("保存个人仓库到数据库: " + Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());
        } catch (SQLException e) {
            plugin.getLogger().severe("保存个人仓库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadSharedStorageFromDB(int storageId, Inventory inventory) {
        try {
            String sql = "SELECT items FROM shared_storage WHERE storage_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, storageId);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String serialized = rs.getString("items");
                        ItemStack[] items = deserializeItems(serialized);
                        inventory.setContents(items);
                        plugin.getLogger().info("从数据库加载共享仓库: " + storageId);
                    } else {
                        plugin.getLogger().info("共享仓库不存在，创建新仓库: " + storageId);
                    }
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("加载共享仓库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveSharedStorageToDB(int storageId) {
        Inventory inventory = sharedStorages.get(storageId);
        if (inventory == null) return;

        try {
            String serialized = serializeItems(inventory.getContents());
            String sql = "INSERT OR REPLACE INTO shared_storage (storage_id, items, last_update) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, storageId);
                pstmt.setString(2, serialized);
                pstmt.setLong(3, System.currentTimeMillis() / 1000);
                pstmt.executeUpdate();
            }

            plugin.getLogger().info("保存共享仓库到数据库: " + storageId);
        } catch (SQLException e) {
            plugin.getLogger().severe("保存共享仓库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 序列化工具 ====================

    private static String serializeItems(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutput = new DataOutputStream(outputStream)) {

            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                if (item == null || item.getType().isAir()) {
                    dataOutput.writeInt(0);
                } else {
                    byte[] bytes = item.serializeAsBytes();
                    dataOutput.writeInt(bytes.length);
                    dataOutput.write(bytes);
                }
            }

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());

        } catch (IOException e) {
            plugin.getLogger().severe("序列化物品失败: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    private static ItemStack[] deserializeItems(String data) {
        if (data == null || data.isEmpty()) {
            return new ItemStack[PERSONAL_STORAGE_SIZE];
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
             DataInputStream dataInput = new DataInputStream(inputStream)) {

            int length = dataInput.readInt();
            ItemStack[] items = new ItemStack[length];

            for (int i = 0; i < length; i++) {
                int byteLength = dataInput.readInt();
                if (byteLength == 0) {
                    items[i] = null;
                } else {
                    byte[] bytes = new byte[byteLength];
                    dataInput.readFully(bytes);
                    items[i] = ItemStack.deserializeBytes(bytes);
                }
            }

            return items;

        } catch (IOException e) {
            plugin.getLogger().severe("反序列化物品失败: " + e.getMessage());
            e.printStackTrace();
            return new ItemStack[PERSONAL_STORAGE_SIZE];
        }
    }

    // ==================== 工具方法 ====================

    public static String toChinese(int num){
        String hun = "";
        String ten = "";
        String one = "";
        if (num / 100 != 0){
            hun = chineseNum.get(num/100) + "佰";
            if (num/10%10 == 0){
                ten = chineseNum.get(0);
            }else {
                ten = chineseNum.get(num/10%10) + "拾";
            }
            if (num%100 != 0){
                one = chineseNum.get(num%10);
            }
        }else if (num/10 != 0){
            ten = chineseNum.get(num/10) + "拾";
            if (num%10 != 0){
                one = chineseNum.get(num%10);
            }
        }else {
            one = chineseNum.get(num);
        }
        return hun+ten+one;
    }

    // ==================== 关闭处理 ====================

    /**
     * 服务器关闭时保存所有数据（静态方法）
     */
    public static void onDisable() {
        plugin.getLogger().info("开始保存所有仓库数据...");

        for (UUID uuid : personalStorages.keySet()) {
            savePersonalStorageToDB(uuid);
        }

        for (int storageId : sharedStorages.keySet()) {
            saveSharedStorageToDB(storageId);
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("数据库连接已关闭");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("关闭数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }

        plugin.getLogger().info("所有仓库数据已保存");
    }

    public static void saveAll() {
        for (UUID uuid : personalStorages.keySet()) {
            savePersonalStorageToDB(uuid);
        }
        for (int storageId : sharedStorages.keySet()) {
            saveSharedStorageToDB(storageId);
        }
        plugin.getLogger().info("手动保存完成");
    }

    public static Collection<Inventory> getPersonalStorages(){
        return personalStorages.values();
    }

    public static Collection<Inventory> getSharedStorages(){
        return sharedStorages.values();
    }

    public static Map<UUID, Inventory> getPersonalStorageMap(){
        return personalStorages;
    }
    public static Map<Integer, Inventory> getSharedStorageMap(){
        return sharedStorages;
    }
}