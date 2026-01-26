package Ink_server.StaticDatas.MinecraftDisabler;

import Ink_server.InkSound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

/**
 * 配方拦截监听器
 * 内部类，不需要手动注册，由 RecipeDisabler 自动管理
 */
class RecipeBlockListener implements Listener {

    private static final InkSound plugin = InkSound.getInstance();
    private final RecipeDisabler recipeDisabler;

    /**
     * 构造函数（包内可见）
     */
    RecipeBlockListener(RecipeDisabler recipeDisabler) {
        this.recipeDisabler = recipeDisabler;
    }

    /**
     * 玩家加入服务器时移除所有已发现的配方
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!recipeDisabler.isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        // 延迟10tick执行，确保玩家完全加载
        recipeDisabler.removePlayerRecipesDelayed(player, 10L);
    }

    /**
     * 阻止玩家发现新配方
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRecipeDiscover(PlayerRecipeDiscoverEvent event) {
        if (!recipeDisabler.isEnabled()) {
            return;
        }

        // 取消配方发现事件
        event.setCancelled(true);

        plugin.getLogger().fine("已阻止玩家 " + event.getPlayer().getName() +
                " 发现配方: " + event.getRecipe());
    }

    /**
     * 阻止玩家在工作台中合成物品
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCraftItem(CraftItemEvent event) {
        if (!recipeDisabler.isEnabled()) {
            return;
        }

        // 取消合成事件
        event.setCancelled(true);

        if (event.getWhoClicked() instanceof Player player) {
            player.sendMessage("§c合成功能已被禁用！");

            plugin.getLogger().fine("已阻止玩家 " + player.getName() + " 合成物品");
        }
    }

    /**
     * 阻止工作台显示合成预览
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (!recipeDisabler.isEnabled()) {
            return;
        }

        // 清空合成结果
        event.getInventory().setResult(null);
    }
}