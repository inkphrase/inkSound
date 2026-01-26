package Ink_server.TestCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class ItemDataCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("只有玩家可以使用此命令！");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType().isAir()) {
            player.sendMessage(Component.text("请手持一个物品！", NamedTextColor.RED));
            return true;
        }
        
        // 发送分隔线
        player.sendMessage(Component.text("=== 物品数据 ===", NamedTextColor.GOLD, TextDecoration.BOLD));
        
        // 基本信息
        player.sendMessage(Component.text("物品类型: ", NamedTextColor.YELLOW)
                .append(Component.text(item.getType().name(), NamedTextColor.WHITE)));
        player.sendMessage(Component.text("数量: ", NamedTextColor.YELLOW)
                .append(Component.text(item.getAmount(), NamedTextColor.WHITE)));
        
        // 获取 ItemMeta
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            // 显示名称
            if (meta.hasDisplayName()) {
                player.sendMessage(Component.text("显示名称: ", NamedTextColor.YELLOW)
                        .append(Objects.requireNonNull(meta.displayName())));
            }
            
            // Lore
            if (meta.hasLore()) {
                player.sendMessage(Component.text("Lore:", NamedTextColor.YELLOW));
                for (Component line : Objects.requireNonNull(meta.lore())) {
                    player.sendMessage(Component.text("  - ", NamedTextColor.GRAY).append(line));
                }
            }
            
            // CustomModelData
            if (meta.hasCustomModelData()) {
                player.sendMessage(Component.text("CustomModelData: ", NamedTextColor.YELLOW)
                        .append(Component.text(meta.getCustomModelData(), NamedTextColor.WHITE)));
            }
            
            // 自定义模型数据组件（新版本）
            try {
                var customModelDataComponent = meta.getCustomModelDataComponent();
                if (customModelDataComponent != null) {
                    player.sendMessage(Component.text("CustomModelDataComponent:", NamedTextColor.YELLOW));

                    // Floats
                    if (!customModelDataComponent.getFloats().isEmpty()) {
                        player.sendMessage(Component.text("  Floats: ", NamedTextColor.GRAY)
                                .append(Component.text(customModelDataComponent.getFloats().toString(), NamedTextColor.WHITE)));
                    }

                    // Flags
                    if (!customModelDataComponent.getFlags().isEmpty()) {
                        player.sendMessage(Component.text("  Flags: ", NamedTextColor.GRAY)
                                .append(Component.text(customModelDataComponent.getFlags().toString(), NamedTextColor.WHITE)));
                    }

                    // Colors
                    if (!customModelDataComponent.getColors().isEmpty()) {
                        player.sendMessage(Component.text("  Colors: ", NamedTextColor.GRAY)
                                .append(Component.text(customModelDataComponent.getColors().toString(), NamedTextColor.WHITE)));
                    }

                    // Strings
                    if (!customModelDataComponent.getStrings().isEmpty()) {
                        player.sendMessage(Component.text("  Strings: ", NamedTextColor.GRAY)
                                .append(Component.text(customModelDataComponent.getStrings().toString(), NamedTextColor.WHITE)));
                    }
                }
            } catch (Exception e) {
                // 如果方法不存在，跳过
            }
            
            // 耐久度
            if (meta instanceof org.bukkit.inventory.meta.Damageable) {
                org.bukkit.inventory.meta.Damageable damageable = (org.bukkit.inventory.meta.Damageable) meta;
                if (damageable.hasDamage()) {
                    player.sendMessage(Component.text("损坏值: ", NamedTextColor.YELLOW)
                            .append(Component.text(damageable.getDamage(), NamedTextColor.WHITE)));
                }
                if (damageable.hasMaxDamage()) {
                    player.sendMessage(Component.text("最大耐久: ", NamedTextColor.YELLOW)
                            .append(Component.text(damageable.getMaxDamage(), NamedTextColor.WHITE)));
                }
            }
            
            // 附魔
            if (meta.hasEnchants()) {
                player.sendMessage(Component.text("附魔:", NamedTextColor.YELLOW));
                meta.getEnchants().forEach((enchant, level) -> {
                    player.sendMessage(Component.text("  - ", NamedTextColor.GRAY)
                            .append(Component.text(enchant.getKey().getKey() + " " + level, NamedTextColor.WHITE)));
                });
            }
            
            // ItemFlags
            if (!meta.getItemFlags().isEmpty()) {
                player.sendMessage(Component.text("ItemFlags: ", NamedTextColor.YELLOW)
                        .append(Component.text(meta.getItemFlags().toString(), NamedTextColor.WHITE)));
            }
            
            // 是否无法破坏
            if (meta.isUnbreakable()) {
                player.sendMessage(Component.text("无法破坏: ", NamedTextColor.YELLOW)
                        .append(Component.text("是", NamedTextColor.GREEN)));
            }
            
            // 自定义标签（PersistentDataContainer）
            var pdc = meta.getPersistentDataContainer();
            if (!pdc.isEmpty()) {
                player.sendMessage(Component.text("持久化数据:", NamedTextColor.YELLOW));
                pdc.getKeys().forEach(key -> {
                    player.sendMessage(Component.text("  - " + key.toString(), NamedTextColor.GRAY));
                });
            }
        }
        
        // 使用 asString() 方法获取完整的数据组件信息（1.21+）
        try {
            String itemString = item.toString();
            player.sendMessage(Component.text("\n完整物品数据字符串:", NamedTextColor.AQUA));
            player.sendMessage(Component.text(itemString, NamedTextColor.GRAY));
        } catch (Exception e) {
            // 如果方法不可用，显示简化信息
            player.sendMessage(Component.text("\n物品toString:", NamedTextColor.AQUA));
            player.sendMessage(Component.text(item.toString(), NamedTextColor.GRAY));
        }
        
        player.sendMessage(Component.text("==================", NamedTextColor.GOLD, TextDecoration.BOLD));
        
        return true;
    }
}