package Ink_server.StaticDatas.MinecraftDisabler;

import Ink_server.InkSound;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 配方禁用管理器 - 可直接集成到现有插件中
 * 使用方法：
 * 1. 将此类复制到你的插件包中
 * 2. 在主类中创建实例：recipeDisabler = new RecipeDisabler(this);
 * 3. 调用 enable() 方法启用功能
 * 4. 在 onDisable() 中调用 disable() 方法
 */
public class RecipeDisabler {

    private static final InkSound plugin = InkSound.getInstance();
    private boolean enabled = false;

    /**
     * 构造函数
     */
    public RecipeDisabler() {

    }

    /**
     * 启用配方禁用功能
     * 在你的插件 onEnable() 中调用此方法
     */
    public void enable() {
        if (enabled) {
            plugin.getLogger().warning("配方禁用功能已经启用！");
            return;
        }

        plugin.getLogger().info("正在启用配方禁用功能...");

        // 注册事件监听器
        RecipeBlockListener listener = new RecipeBlockListener(this);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        // 移除服务器上的所有配方
        removeAllServerRecipes();

        // 清除所有在线玩家的配方
        clearAllOnlinePlayersRecipes();

        enabled = true;
        plugin.getLogger().info("配方禁用功能已启用！所有原版配方已被禁用。");
    }

    /**
     * 禁用配方禁用功能
     * 在你的插件 onDisable() 中调用此方法（可选）
     */
    public void disable() {
        if (!enabled) {
            return;
        }

        // 注意：移除的配方不会自动恢复，需要重启服务器
        enabled = false;
        plugin.getLogger().info("配方禁用功能已禁用。");
    }

    /**
     * 检查功能是否已启用
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 从服务器移除所有原版配方
     */
    public void removeAllServerRecipes() {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        List<NamespacedKey> recipesToRemove = new ArrayList<>();

        // 收集所有原版配方的 NamespacedKey
        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            if (recipe instanceof Keyed) {
                NamespacedKey key = ((Keyed) recipe).getKey();

                // 只移除原版配方（minecraft命名空间）
                if (key.getNamespace().equals("minecraft")) {
                    recipesToRemove.add(key);
                }
            }
        }

        // 移除配方
        int removedCount = 0;
        for (NamespacedKey key : recipesToRemove) {
            if (Bukkit.removeRecipe(key)) {
                removedCount++;
            }
        }

        plugin.getLogger().info("已从服务器移除 " + removedCount + " 个原版配方");
    }

    /**
     * 移除指定玩家的所有已发现配方
     *
     * @param player 目标玩家
     */
    public void removePlayerRecipes(Player player) {
        // 获取所有已发现的配方

        List<NamespacedKey> discoveredRecipes = new ArrayList<>(player.getDiscoveredRecipes());

        // 移除所有配方
        int removedCount = 0;
        for (NamespacedKey key : discoveredRecipes) {
            if (player.undiscoverRecipe(key)) {
                removedCount++;
            }
        }

        if (removedCount > 0) {
            plugin.getLogger().fine("已为玩家 " + player.getName() + " 移除 " + removedCount + " 个配方");
        }

    }

    /**
     * 清除所有在线玩家的配方
     */
    public void clearAllOnlinePlayersRecipes() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            removePlayerRecipes(player);
        }
    }

    /**
     * 延迟移除玩家配方（用于玩家加入时）
     * @param player 目标玩家
     * @param delayTicks 延迟tick数（推荐10）
     */
    public void removePlayerRecipesDelayed(Player player, long delayTicks) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> removePlayerRecipes(player), delayTicks);
    }

    /**
     * 获取插件实例
     */
    protected InkSound getPlugin() {
        return plugin;
    }
}