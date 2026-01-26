package Ink_server.NPCs;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemLib.ItemDataManager;
import Ink_server.StaticDatas.NPCID;
import com.google.gson.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 村民交易管理系统 - 固定交易版本
 * 特性：
 * - 交易完全固定，不受玩家声望影响
 * - 不受村民等级影响
 * - 交易时不会有经验变动
 * - 可兑换次数为无限
 * - JSON只存储三个物品信息（输入1、输入2、输出）
 */
public class VillagerTradeManager {

    private static final InkSound plugin = InkSound.getInstance();
    private final File dataFile;
    private final File backupFile;
    private final Gson gson;

    // 交易数据：村民ID -> 交易列表
    private static final Map<String, List<TradeRecipe>> tradeMap = new ConcurrentHashMap<>();

    public VillagerTradeManager() {
        this.dataFile = new File(plugin.getDataFolder(), "villager_trades.json");
        this.backupFile = new File(plugin.getDataFolder(), "villager_trades.json.backup");

        // 配置 Gson - 使用排除策略避免扫描 ItemStack 内部字段
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 跳过 ItemStack 及其子类的所有字段
                        return f.getDeclaringClass() == ItemStack.class ||
                                ItemStack.class.isAssignableFrom(f.getDeclaringClass());
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 不跳过整个类，只跳过字段
                        return false;
                    }
                })
                .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
                .create();

        // 确保数据文件夹存在
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    // ==================== 加载与保存 ====================

    /**
     * 从 JSON 文件加载所有交易数据
     */
    public void load() {
        if (!dataFile.exists()) {
            plugin.getLogger().info("交易数据文件不存在，创建默认配置");
            createDefaultTrades();
            save();
            return;
        }

        try (Reader reader = new InputStreamReader(
                new FileInputStream(dataFile),
                StandardCharsets.UTF_8)) {

            JsonObject root = gson.fromJson(reader, JsonObject.class);

            if (root != null) {
                tradeMap.clear();

                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                    String villagerId = entry.getKey();
                    JsonArray tradesArray = entry.getValue().getAsJsonArray();

                    List<TradeRecipe> trades = new ArrayList<>();
                    for (JsonElement tradeElement : tradesArray) {
                        try {
                            TradeRecipe recipe = gson.fromJson(tradeElement, TradeRecipe.class);
                            // 验证交易配方有效性
                            if (recipe != null && recipe.getResult() != null) {
                                trades.add(recipe);
                            } else {
                                plugin.getLogger().warning("跳过无效的交易配方: " + villagerId);
                            }
                        } catch (Exception e) {
                            plugin.getLogger().warning("解析交易配方失败: " + villagerId + " - " + e.getMessage());
                        }
                    }

                    if (!trades.isEmpty()) {
                        tradeMap.put(villagerId, trades);
                    }
                }

                plugin.getLogger().info("成功加载 " + tradeMap.size() + " 个村民的交易数据");
            }

            if (tradeMap.isEmpty()){
                plugin.getLogger().warning("交易数据为空，创建默认配置");
                createDefaultTrades();
            }

        } catch (IOException e) {
            plugin.getLogger().severe("加载交易数据失败: " + e.getMessage());
            e.printStackTrace();

            // 尝试从备份恢复
            if (backupFile.exists()) {
                plugin.getLogger().info("尝试从备份文件恢复...");
                loadFromBackup();
            }
        }
    }

    /**
     * 从备份文件加载
     */
    private void loadFromBackup() {
        try (Reader reader = new InputStreamReader(
                new FileInputStream(backupFile),
                StandardCharsets.UTF_8)) {

            JsonObject root = gson.fromJson(reader, JsonObject.class);
            if (root != null) {
                tradeMap.clear();
                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                    String villagerId = entry.getKey();
                    JsonArray tradesArray = entry.getValue().getAsJsonArray();

                    List<TradeRecipe> trades = new ArrayList<>();
                    for (JsonElement tradeElement : tradesArray) {
                        TradeRecipe recipe = gson.fromJson(tradeElement, TradeRecipe.class);
                        if (recipe != null && recipe.getResult() != null) {
                            trades.add(recipe);
                        }
                    }

                    if (!trades.isEmpty()) {
                        tradeMap.put(villagerId, trades);
                    }
                }
                plugin.getLogger().info("成功从备份恢复 " + tradeMap.size() + " 个村民的交易数据");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("从备份恢复失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 保存所有交易数据到 JSON 文件（改进版，带备份和原子写入）
     */
    public void save() {
        // 创建临时文件
        File tempFile = new File(dataFile.getParent(), "villager_trades.json.tmp");

        try {
            // 1. 先写入临时文件
            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(tempFile),
                    StandardCharsets.UTF_8)) {

                gson.toJson(tradeMap, writer);
                writer.flush();
            }

            // 2. 如果原文件存在，备份它
            if (dataFile.exists()) {
                Files.copy(dataFile.toPath(), backupFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // 3. 用临时文件替换原文件（原子操作）
            Files.move(tempFile.toPath(), dataFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

            plugin.getLogger().info("成功保存 " + tradeMap.size() + " 个村民的交易数据");

        } catch (IOException e) {
            plugin.getLogger().severe("保存交易数据失败: " + e.getMessage());
            e.printStackTrace();

            // 清理临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 创建默认交易示例（带空值检查）
     */
    private void createDefaultTrades() {
        try {
            // 示例：东方锻造核心商人的交易
            List<TradeRecipe> EAST_FORGECORE_SELLER = new ArrayList<>();

            ItemStack metal = ItemDataManager.getItem("weaponmetal2");
            ItemStack core = ItemDataManager.getItem("armorcore2");
            ItemStack good = ItemDataManager.getItem("west.goods.2");

            // 空值检查
            if (metal == null || core == null || good == null) {
                plugin.getLogger().warning("无法创建默认交易：部分物品未找到");
                plugin.getLogger().warning("metal=" + (metal != null) +
                        ", core=" + (core != null) +
                        ", good=" + (good != null));
                return;
            }

            ItemStack good4 = good.clone();
            ItemStack good8 = good.clone();
            good4.setAmount(4);
            good8.setAmount(8);

            ItemStack weaponForge = ItemDataManager.getItem("east.weaponforge.2");
            ItemStack armorForge = ItemDataManager.getItem("east.armorforge.2");

            if (weaponForge == null || armorForge == null) {
                plugin.getLogger().warning("无法创建默认交易：锻造物品未找到");
                return;
            }

            // 交易1：金属 + 8个货物 -> 武器锻造
            EAST_FORGECORE_SELLER.add(new TradeRecipe(
                    metal,
                    good8,
                    weaponForge
            ));

            // 交易2：核心 + 4个货物 -> 护甲锻造
            EAST_FORGECORE_SELLER.add(new TradeRecipe(
                    core,
                    good4,
                    armorForge
            ));

            tradeMap.put(NPCID.EAST_FORGECORE_SELLER, EAST_FORGECORE_SELLER);

            plugin.getLogger().info("成功创建默认交易配置");

        } catch (Exception e) {
            plugin.getLogger().severe("创建默认交易失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== 数据操作 ====================

    /**
     * 获取村民的交易列表
     */
    public static List<TradeRecipe> getTrades(String villagerId) {
        return tradeMap.get(villagerId);
    }

    /**
     * 设置村民的交易列表
     */
    public static void setTrades(String villagerId, List<TradeRecipe> trades) {
        tradeMap.put(villagerId, trades);
    }

    /**
     * 添加单个交易
     */
    public void addTrade(String villagerId, TradeRecipe trade) {
        tradeMap.computeIfAbsent(villagerId, k -> new ArrayList<>()).add(trade);
    }

    /**
     * 获取村民的 MerchantRecipe 列表（用于打开交易界面）
     * 固定版本：无限次数，无经验，不受声望影响
     */
    public static List<MerchantRecipe> getMerchantRecipes(String villagerId) {
        List<TradeRecipe> trades = tradeMap.get(villagerId);
        if (trades == null || trades.isEmpty()) {
            return new ArrayList<>();
        }

        List<MerchantRecipe> recipes = new ArrayList<>();
        for (TradeRecipe trade : trades) {
            try {
                MerchantRecipe recipe = trade.toMerchantRecipe();
                if (recipe != null) {
                    recipes.add(recipe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return recipes;
    }

    /**
     * 清空村民的交易
     */
    public void clearTrades(String villagerId) {
        tradeMap.remove(villagerId);
    }

    /**
     * 获取所有村民ID
     */
    public Set<String> getAllVillagerIds() {
        return Collections.unmodifiableSet(tradeMap.keySet());
    }

    // ==================== 交易配方类 ====================

    /**
     * 交易配方数据类 - 固定交易版本
     * JSON结构简化：只存储三个物品信息
     * {
     *   "input1": ItemStack,
     *   "input2": ItemStack (可选),
     *   "result": ItemStack
     * }
     */
    public static class TradeRecipe {
        private ItemStack input1;   // 第一个输入物品
        private ItemStack input2;   // 第二个输入物品（可选）
        private ItemStack result;   // 输出物品

        // 无参构造函数（Gson需要）
        public TradeRecipe() {}

        /**
         * 完整构造函数
         */
        public TradeRecipe(ItemStack input1, ItemStack input2, ItemStack result) {
            this.input1 = input1;
            this.input2 = input2;
            this.result = result;
        }

        /**
         * 简化构造函数（只有一个输入物品）
         */
        public TradeRecipe(ItemStack input1, ItemStack result) {
            this(input1, null, result);
        }

        /**
         * 转换为 Bukkit 的 MerchantRecipe
         * 固定配置：
         * - maxUses = Integer.MAX_VALUE (无限次数)
         * - villagerExperience = 0 (不给经验)
         * - priceMultiplier = 0 (不受声望影响)
         * - experienceReward = false (不奖励经验)
         */
        public MerchantRecipe toMerchantRecipe() {
            if (result == null) {
                throw new IllegalStateException("交易结果物品不能为空");
            }

            // 设置为最大整数值，实现无限次数
            MerchantRecipe recipe = new MerchantRecipe(result, Integer.MAX_VALUE);

            // 添加输入物品
            if (input1 != null) {
                recipe.addIngredient(input1);
            }
            if (input2 != null) {
                recipe.addIngredient(input2);
            }

            // 固定配置：不受任何动态因素影响
            recipe.setExperienceReward(false);        // 不给玩家经验
            recipe.setVillagerExperience(0);          // 村民不获得经验（不升级）
            recipe.setPriceMultiplier(0f);            // 价格不受声望影响

            return recipe;
        }

        /**
         * 从 MerchantRecipe 创建 TradeRecipe
         * 只提取物品信息，忽略其他属性
         */
        public static TradeRecipe fromMerchantRecipe(MerchantRecipe merchantRecipe) {
            List<ItemStack> ingredients = merchantRecipe.getIngredients();

            ItemStack input1 = ingredients.size() > 0 ? ingredients.get(0) : null;
            ItemStack input2 = ingredients.size() > 1 ? ingredients.get(1) : null;

            return new TradeRecipe(
                    input1,
                    input2,
                    merchantRecipe.getResult()
            );
        }

        // Getters and Setters
        public ItemStack getInput1() { return input1; }
        public void setInput1(ItemStack input1) { this.input1 = input1; }

        public ItemStack getInput2() { return input2; }
        public void setInput2(ItemStack input2) { this.input2 = input2; }

        public ItemStack getResult() { return result; }
        public void setResult(ItemStack result) { this.result = result; }
    }

    // ==================== ItemStack 序列化器 ====================

    /**
     * ItemStack 的 Gson 序列化/反序列化器
     * 使用 Base64 编码的字节数据，完全避开 Gson 的反射机制
     */
    private static class ItemStackSerializer implements JsonSerializer<ItemStack>,
            JsonDeserializer<ItemStack> {

        @Override
        public JsonElement serialize(ItemStack item, java.lang.reflect.Type type,
                                     JsonSerializationContext context) {
            if (item == null) return JsonNull.INSTANCE;

            try {
                // 使用 ItemStack 的字节序列化（不涉及 Optional）
                byte[] data = item.serializeAsBytes();
                String base64 = Base64.getEncoder().encodeToString(data);
                return new JsonPrimitive(base64);
            } catch (Exception e) {
                System.err.println("ItemStack序列化失败: " + item.getType() + " - " + e.getMessage());
                e.printStackTrace();
                return JsonNull.INSTANCE;
            }
        }

        @Override
        public ItemStack deserialize(JsonElement json, java.lang.reflect.Type type,
                                     JsonDeserializationContext context)
                throws JsonParseException {
            if (json.isJsonNull()) return null;

            try {
                String base64 = json.getAsString();
                byte[] data = Base64.getDecoder().decode(base64);
                return ItemStack.deserializeBytes(data);
            } catch (Exception e) {
                System.err.println("ItemStack反序列化失败: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}