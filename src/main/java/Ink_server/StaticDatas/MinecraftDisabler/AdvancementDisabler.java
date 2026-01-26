package Ink_server.StaticDatas.MinecraftDisabler;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 原版成就完全移除器
 * 完全移除所有原版成就的获取、显示和面板显示
 */
public class AdvancementDisabler implements Listener {

    private final JavaPlugin plugin;
    private boolean enabled = false;
    private File datapackFile;

    public AdvancementDisabler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * 启用成就禁用功能
     */
    public void enable() {
        if (enabled) {
            plugin.getLogger().warning("成就禁用功能已经启用！");
            return;
        }

        plugin.getLogger().info("正在启用成就禁用功能...");

        // 创建完全移除原版成就的数据包
        try {
            createCompleteRemovalDatapack();
        } catch (IOException e) {
            plugin.getLogger().severe("创建成就移除数据包失败: " + e.getMessage());
            e.printStackTrace();
        }

        // 注册事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // 撤销所有在线玩家的原版成就
        for (Player player : Bukkit.getOnlinePlayers()) {
            revokeAllAdvancements(player);
        }

        enabled = true;
        plugin.getLogger().info("成就禁用功能已启用！");
        plugin.getLogger().info("请使用 /reload 或重启服务器以应用数据包，完全移除成就面板中的原版成就。");
    }

    /**
     * 禁用成就禁用功能
     */
    public void disable() {
        if (!enabled) {
            return;
        }

        plugin.getLogger().info("成就禁用功能已禁用。");
        enabled = false;
    }

    /**
     * 创建完全移除原版成就的数据包（更彻底的方案）
     */
    private void createCompleteRemovalDatapack() throws IOException {
        // 获取世界文件夹
        File worldFolder = Bukkit.getWorlds().getFirst().getWorldFolder();
        File datapacksFolder = new File(worldFolder, "datapacks");

        // 确保datapacks文件夹存在
        if (!datapacksFolder.exists()) {
            datapacksFolder.mkdirs();
        }

        // 数据包文件
        datapackFile = new File(datapacksFolder, "remove_vanilla_advancements.zip");

        // 如果数据包已存在，跳过
        if (datapackFile.exists()) {
            plugin.getLogger().info("成就移除数据包已存在，跳过创建。");
            return;
        }

        plugin.getLogger().info("正在创建成就移除数据包...");

        // 创建ZIP数据包
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(datapackFile))) {

            // 1. 创建 pack.mcmeta (1.21.11 使用 pack_format 48)
            String packMcmeta = """
                    {
                      "pack": {
                        "pack_format": 48,
                        "description": "Remove all vanilla advancements"
                      }
                    }""";

            ZipEntry mcmetaEntry = new ZipEntry("pack.mcmeta");
            zos.putNextEntry(mcmetaEntry);
            zos.write(packMcmeta.getBytes());
            zos.closeEntry();

            // 2. 遍历所有原版成就，创建空覆盖文件
            int count = 0;
            Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();

            while (iterator.hasNext()) {
                Advancement advancement = iterator.next();

                // 只处理原版成就（命名空间为 minecraft）
                if (advancement.getKey().getNamespace().equals("minecraft")) {
                    String path = advancement.getKey().getKey();

                    // 创建空的成就文件，使成就完全不可见
                    // 使用 impossible 触发器，确保永远无法触发
                    String emptyAdvancement = """
                            {
                              "criteria": {
                                "impossible": {
                                  "trigger": "minecraft:impossible"
                                }
                              }
                            }""";

                    // 创建对应路径的文件
                    ZipEntry entry = new ZipEntry("data/minecraft/advancement/" + path + ".json");
                    zos.putNextEntry(entry);
                    zos.write(emptyAdvancement.getBytes());
                    zos.closeEntry();

                    count++;
                }
            }

            plugin.getLogger().info("成就移除数据包创建成功，已覆盖 " + count + " 个原版成就");
            plugin.getLogger().info("数据包位置: " + datapackFile.getAbsolutePath());
        }
    }

    /**
     * 玩家加入时撤销所有原版成就
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!enabled) return;

        Player player = event.getPlayer();

        // 延迟1 tick执行，确保玩家数据已加载
        Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAllAdvancements(player), 1L);
    }

    /**
     * 监听成就完成事件，立即撤销
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        if (!enabled) return;

        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();

        // 检查是否为原版成就（命名空间为 minecraft）
        if (advancement.getKey().getNamespace().equals("minecraft")) {
            // 延迟1 tick撤销，避免与成就系统冲突
            Bukkit.getScheduler().runTaskLater(plugin, () -> revokeAdvancement(player, advancement), 1L);
        }
    }

    /**
     * 撤销玩家的所有原版成就
     * @param player 玩家
     */
    private void revokeAllAdvancements(Player player) {
        int revokedCount = 0;

        // 遍历所有成就
        Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
        while (iterator.hasNext()) {
            Advancement advancement = iterator.next();

            // 只撤销原版成就（命名空间为 minecraft）
            if (advancement.getKey().getNamespace().equals("minecraft")) {
                if (revokeAdvancement(player, advancement)) {
                    revokedCount++;
                }
            }
        }

        if (revokedCount > 0) {
            plugin.getLogger().info("已撤销玩家 " + player.getName() + " 的 " + revokedCount + " 个原版成就");
        }
    }

    /**
     * 撤销玩家的单个成就
     * @param player 玩家
     * @param advancement 成就
     * @return 是否成功撤销
     */
    private boolean revokeAdvancement(Player player, Advancement advancement) {
        var progress = player.getAdvancementProgress(advancement);

        // 如果成就已完成或有进度，撤销所有标准
        if (progress.isDone() || !progress.getAwardedCriteria().isEmpty()) {
            for (String criteria : progress.getAwardedCriteria()) {
                progress.revokeCriteria(criteria);
            }
            return true;
        }

        return false;
    }

    /**
     * 删除数据包（卸载时调用）
     */
    public void removeDatapack() {
        if (datapackFile != null && datapackFile.exists()) {
            if (datapackFile.delete()) {
                plugin.getLogger().info("已删除成就移除数据包");
                plugin.getLogger().info("请重启服务器以恢复原版成就");
            }
        }
    }

    /**
     * 检查数据包是否存在
     */
    public boolean isDatapackExists() {
        return datapackFile != null && datapackFile.exists();
    }

    /**
     * 检查是否已启用
     */
    public boolean isEnabled() {
        return enabled;
    }
}