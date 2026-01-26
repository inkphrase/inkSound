package Ink_server.InkSoundEvents;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.InkSound;
import Ink_server.InkSoundFight.Elements.ElementsRegistry;
import Ink_server.InkSoundFight.Elements.UltimateElementsRegistry;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerUseElementEventCaller implements Listener {
    private static final InkSound plugin = InkSound.getInstance();

    public static void init() {
        plugin.getServer().getPluginManager().registerEvents(new PlayerUseElementEventCaller(), plugin);
        plugin.getLogger().info("元素使用事件已初始化！");
    }

    private static final Set<String> elements = Set.of(
            //普通
            ItemRegistry.ELEMENT_METAL, ItemRegistry.ELEMENT_WOOD, ItemRegistry.ELEMENT_WATER,
            ItemRegistry.ELEMENT_LAVA, ItemRegistry.ELEMENT_EARTH,
            //精炼
            ItemRegistry.REFINED_METAL, ItemRegistry.REFINED_WOOD, ItemRegistry.REFINED_WATER,
            ItemRegistry.REFINED_LAVA, ItemRegistry.REFINED_EARTH,
            //浓缩
            ItemRegistry.ADVANCED_METAL, ItemRegistry.ADVANCED_WOOD, ItemRegistry.ADVANCED_WATER,
            ItemRegistry.ADVANCED_LAVA, ItemRegistry.ADVANCED_EARTH
    );
    private static final Map<String, Component> coolDownTip = new HashMap<>();
    private static final Map<String, String> coolTypeMap = new HashMap<>();
    private static final Map<String, Component> ultimateCoolMap = new HashMap<>();
    static {
        final Component metalCooling = Component.text("金元素阵法冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component woodCooling = Component.text("木元素阵法冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component waterCooling = Component.text("水元素阵法冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component lavaCooling = Component.text("火元素阵法冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component earthCooling = Component.text("土元素阵法冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        coolDownTip.put(ItemRegistry.ELEMENT_METAL, metalCooling);
        coolDownTip.put(ItemRegistry.ELEMENT_WOOD, woodCooling);
        coolDownTip.put(ItemRegistry.ELEMENT_WATER, waterCooling);
        coolDownTip.put(ItemRegistry.ELEMENT_LAVA, lavaCooling);
        coolDownTip.put(ItemRegistry.ELEMENT_EARTH, earthCooling);
        coolDownTip.put(ItemRegistry.REFINED_METAL, metalCooling);
        coolDownTip.put(ItemRegistry.REFINED_WOOD, woodCooling);
        coolDownTip.put(ItemRegistry.REFINED_WATER, waterCooling);
        coolDownTip.put(ItemRegistry.REFINED_LAVA, lavaCooling);
        coolDownTip.put(ItemRegistry.REFINED_EARTH, earthCooling);
        coolDownTip.put(ItemRegistry.ADVANCED_METAL, metalCooling);
        coolDownTip.put(ItemRegistry.ADVANCED_WOOD, woodCooling);
        coolDownTip.put(ItemRegistry.ADVANCED_WATER, waterCooling);
        coolDownTip.put(ItemRegistry.ADVANCED_LAVA, lavaCooling);
        coolDownTip.put(ItemRegistry.ADVANCED_EARTH, earthCooling);

        coolTypeMap.put(ItemRegistry.ELEMENT_METAL, "metal");
        coolTypeMap.put(ItemRegistry.ELEMENT_WOOD, "wood");
        coolTypeMap.put(ItemRegistry.ELEMENT_WATER, "water");
        coolTypeMap.put(ItemRegistry.ELEMENT_LAVA, "lava");
        coolTypeMap.put(ItemRegistry.ELEMENT_EARTH, "earth");
        coolTypeMap.put(ItemRegistry.REFINED_METAL, "metal");
        coolTypeMap.put(ItemRegistry.REFINED_WOOD, "wood");
        coolTypeMap.put(ItemRegistry.REFINED_WATER, "water");
        coolTypeMap.put(ItemRegistry.REFINED_LAVA, "lava");
        coolTypeMap.put(ItemRegistry.REFINED_EARTH, "earth");
        coolTypeMap.put(ItemRegistry.ADVANCED_METAL, "metal");
        coolTypeMap.put(ItemRegistry.ADVANCED_WOOD, "wood");
        coolTypeMap.put(ItemRegistry.ADVANCED_WATER, "water");
        coolTypeMap.put(ItemRegistry.ADVANCED_LAVA, "lava");
        coolTypeMap.put(ItemRegistry.ADVANCED_EARTH, "earth");

        ultimateCoolMap.put(ItemRegistry.REFINED_METAL, Component.text("金元素奥义冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        ultimateCoolMap.put(ItemRegistry.REFINED_WOOD, Component.text("木元素奥义冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        ultimateCoolMap.put(ItemRegistry.REFINED_WATER, Component.text("水元素奥义冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        ultimateCoolMap.put(ItemRegistry.REFINED_LAVA, Component.text("火元素奥义冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        ultimateCoolMap.put(ItemRegistry.REFINED_EARTH, Component.text("土元素奥义冷却中").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //排除副手
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        //只处理右键点击
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfoManager.getPlayerInfo(player.getUniqueId());
        //排除无数据与非炼丹师
        if (playerInfo == null || playerInfo.getJob() != 2) return;
        //排除非元素
        ItemStack item = player.getInventory().getItemInMainHand();
        //首先检查物品类型，元素一定是粘土球
        if (item.getType() != Material.CLAY_BALL || !item.hasItemMeta()) return;
        var pdc = item.getItemMeta().getPersistentDataContainer();
        //二次检查，由于必须获取id，所以增加一次if显得微不足道
        String element = pdc.get(ItemKeys.ID, PersistentDataType.STRING);
        if (!elements.contains(element)) return;
        //检查副手是否为丹炉
        ItemStack offhand = player.getInventory().getItemInOffHand();
        //丹炉一定是树脂砖
        if (offhand.getType() != Material.RESIN_BRICK || !item.hasItemMeta()) return;
        //副手未激活则不触发
        var offPdc = offhand.getItemMeta().getPersistentDataContainer();
        Boolean enable = offPdc.get(ItemKeys.ENABLE, PersistentDataType.BOOLEAN);
        if (!Boolean.TRUE.equals(enable)) return;
        //逻辑到这里，说明玩家是炼丹师且使用了元素
        PlayerUseElementEvent useElementEvent = new PlayerUseElementEvent(player, element);
        Bukkit.getPluginManager().callEvent(useElementEvent);
        if (!useElementEvent.isCancelled()) {
            //使用元素技能并设置cd
            EntityData playerData = EntityDataManager.getData(player);
            if (playerData == null) {
                plugin.getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
                return;
            }
            //这里优先判断奥义阵法
            if (player.isSneaking()) {
                if (playerData.getSkillsMap().containsKey("yin") && playerData.getSkillsMap().containsKey("yang")){
                    UltimateElementsRegistry ultimateElementsRegistry = UltimateElementsRegistry.getById(element);
                    if (ultimateElementsRegistry != null) {
                        //奥义阵法暂未实装，此处放行，不执行return
                    }
                }
            }
            ElementsRegistry elementEffect = ElementsRegistry.getById(element);
            if (elementEffect == null) return;
            elementEffect.handle(player, playerData);
        }
    }

    //在这里检测元素使用事件是否取消
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerUseElement(PlayerUseElementEvent event) {
        Player player = event.getPlayer();
        String element = event.getElement();
        //检查cd
        EntityData playerData = EntityDataManager.getData(player);
        if (playerData == null) {
            plugin.getLogger().severe("玩家 " + player.getName() + " 被战斗系统忽略！");
            return;
        }
        int currentTick = Bukkit.getCurrentTick();
        Integer cdEndTick = playerData.cooldownMap.get(coolTypeMap.get(element));
        Integer ultimateEnd = playerData.cooldownMap.get("ultimate");
        if (player.isSneaking()){
            if (playerData.getSkillsMap().containsKey("yin") && playerData.getSkillsMap().containsKey("yang")){
                if (UltimateElementsRegistry.ID_MAP.containsKey(element)) {
                    if (ultimateEnd != null && ultimateEnd > currentTick) {
                        event.setCancelled(true);
                        player.sendActionBar(ultimateCoolMap.get(element)
                                .append(Component.text("  剩余: " + ((ultimateEnd - currentTick) / 20.0) + " 秒")
                                        .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false)));
                        player.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
                    }
                    return;
                }
            }
        }
        if (cdEndTick != null && cdEndTick > currentTick) {
            //仍在cd，取消事件
            event.setCancelled(true);
            player.sendActionBar(coolDownTip.get(element)
                    .append(Component.text("  剩余: " + ((cdEndTick - currentTick) / 20) + " 秒")
                            .color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false)));
            player.playSound(player, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
        }
    }
}
