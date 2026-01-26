package Ink_server.StaticDatas.WeaponLib;

import Ink_server.InkSound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class WeaponRegistry {
    public static void init(){
        InkSound.getInstance().getLogger().info("武器已注册！");
    }

    //region 注册id常量
    public static final String SWORD1 = "sword1";
    public static final String BOW1 = "bow1";
    public static final String FURNACE1 = "furnace1";

    public static final String SWORD20 = "sword20";
    public static final String SWORD21 = "sword21";
    public static final String SWORD22 = "sword22";
    public static final String SWORD23 = "sword23";

    public static final String BOW20 = "bow20";
    public static final String BOW21 = "bow21";
    public static final String BOW22 = "bow22";
    public static final String BOW23 = "bow23";

    public static final String FURNACE2 = "furnace2";

    public static final String SWORD30 = "sword30";
    public static final String SWORD31 = "sword31";
    public static final String SWORD32 = "sword32";
    public static final String SWORD33 = "sword33";

    public static final String BOW30 = "bow30";
    public static final String BOW31 = "bow31";
    public static final String BOW32 = "bow32";
    public static final String BOW33 = "bow33";

    public static final String FURNACE3 = "furnace3";

    public static final String SWORD40 = "sword40";
    public static final String SWORD41 = "sword41";
    public static final String SWORD42 = "sword42";
    public static final String SWORD43 = "sword43";

    public static final String BOW40 = "bow40";
    public static final String BOW41 = "bow41";
    public static final String BOW42 = "bow42";
    public static final String BOW43 = "bow43";

    public static final String FURNACE4 = "furnace4";

    public static final String SWORD50 = "sword50";
    public static final String SWORD51 = "sword51";
    public static final String SWORD52 = "sword52";
    public static final String SWORD53 = "sword53";

    public static final String BOW50 = "bow50";
    public static final String BOW51 = "bow51";
    public static final String BOW52 = "bow52";
    public static final String BOW53 = "bow53";

    public static final String FURNACE5 = "furnace5";

    public static final String SWORD60 = "sword60";
    public static final String SWORD61 = "sword61";
    public static final String SWORD62 = "sword62";
    public static final String SWORD63 = "sword63";

    public static final String BOW60 = "bow60";
    public static final String BOW61 = "bow61";
    public static final String BOW62 = "bow62";
    public static final String BOW63 = "bow63";

    public static final String FURNACE6 = "furnace6";

    public static final String LEGENDSWORD60 = "legendsword60";
    public static final String LEGENDSWORD61 = "legendsword61";
    public static final String LEGENDSWORD62 = "legendsword62";
    public static final String LEGENDSWORD63 = "legendsword63";

    public static final String LEGENDBOW60 = "legendbow60";
    public static final String LEGENDBOW61 = "legendbow61";
    public static final String LEGENDBOW62 = "legendbow62";
    public static final String LEGENDBOW63 = "legendbow63";

    public static final String LEGENDFURNACE6 = "legendfurnace6";
    //endregion


    //注册武器
    static {
        //注册model常量
        final String WEAPON_MODEL = "weapons/";
        final String MAGIC_WEAPON_MODEL = "magicweapons/";

        //region 注册lore常量
        final Component RARE_1 = Component.text("稀有度:★      ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false);
        final Component RARE_2 = Component.text("稀有度:★★     ").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false);
        final Component RARE_3 = Component.text("稀有度:★★★    ").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false);
        final Component RARE_4 = Component.text("稀有度:★★★★   ").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC,false);
        final Component RARE_5 = Component.text("稀有度:★★★★★  ").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC,false);
        final Component RARE_6 = Component.text("稀有度:★★★★★★      ").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);

        final Component QUALITY_1 = Component.text("品质:[普通]").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false);
        final Component QUALITY_2 = Component.text("品质:[优秀]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false);
        final Component QUALITY_3 = Component.text("品质:[精良]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false);
        final Component QUALITY_4 = Component.text("品质:[史诗]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC,false);
        final Component QUALITY_5 = Component.text("品质:[珍奇]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC,false);
        final Component QUALITY_6 = Component.text("品质:[传说]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);

        final Component JOBLIMIT_0 = Component.text("限制职业:[战]      ").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component JOBLIMIT_1 = Component.text("限制职业:[弓]      ").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component JOBLIMIT_2 = Component.text("限制职业:[丹]      ").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component JOBLIMIT_3 = Component.text("限制职业:[无]      ").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);

        final Component LEVELLIMIT_10 = Component.text("限制等级:10").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_15 = Component.text("限制等级:15").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_20 = Component.text("限制等级:20").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_25 = Component.text("限制等级:25").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_30 = Component.text("限制等级:30").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_35 = Component.text("限制等级:35").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_40 = Component.text("限制等级:40").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_45 = Component.text("限制等级:45").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_50 = Component.text("限制等级:50").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_55 = Component.text("限制等级:55").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_60 = Component.text("限制等级:60").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_65 = Component.text("限制等级:65").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_70 = Component.text("限制等级:70").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_75 = Component.text("限制等级:75").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_80 = Component.text("限制等级:80").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_85 = Component.text("限制等级:85").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_90 = Component.text("限制等级:90").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_95 = Component.text("限制等级:95").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);
        final Component LEVELLIMIT_100 = Component.text("限制等级:100").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false);

        final Component WEAPON_ATTRIBUTE = Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("武器属性")
                        .color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC,false)
                        .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true))
                .append(Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));
        final Component WEAPON_FEATURE = Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("武器特性")
                        .color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC,false)
                        .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true))
                .append(Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));
        final Component WEAPON_SKILL = Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("武器技能")
                        .color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC,false)
                        .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true))
                .append(Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));
        final Component WEAPON_ENHANCE = Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("强化属性")
                        .color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.ITALIC,false)
                        .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.UNDERLINED, true))
                .append(Component.text("==========").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false));

        final Component ENCHANCE_1 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[壹阶]").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));
        final Component ENCHANCE_2 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[贰阶]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));
        final Component ENCHANCE_3 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[叁阶]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));
        final Component ENCHANCE_4 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[肆阶]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));
        final Component ENCHANCE_5 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[伍阶]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));
        final Component ENCHANCE_6 = Component.text("[附灵]").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false)
                .append(Component.text("可使用").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false)
                        .append(Component.text("[陆阶]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false)
                                .append(Component.text("及以下的附灵石附灵").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,false))));

        final Component ENABLE_1 = Component.text("激活位 [一]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_2 = Component.text("激活位 [二]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_3 = Component.text("激活位 [三]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_4 = Component.text("激活位 [四]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_5 = Component.text("激活位 [五]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_6 = Component.text("激活位 [六]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_7 = Component.text("激活位 [七]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_8 = Component.text("激活位 [八]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_9 = Component.text("激活位 [九]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_OFF = Component.text("激活位 [副]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);
        final Component ENABLE_ARMOR = Component.text("激活位 [甲]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false);

        //endregion

        //region 一阶
        WeaponData sword1 = new WeaponData(SWORD1, Material.NETHERITE_SWORD,
                Component.text("桃木剑").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD1, new ArrayList<>()
        );
        sword1.setAttribute(WeaponData.ATTACKBASE, 5);
        sword1.setAttribute(WeaponData.ENABLESLOT, 0);
        sword1.setAttribute(WeaponData.JOBLIMIT, 0);
        sword1.setAttribute(WeaponData.LEVELLIMIT, 0);
        sword1.setAttribute(WeaponData.ATTACKSPEED, -2.4);

        WeaponData bow1 = new WeaponData(BOW1, Material.BOW,
                Component.text("桃木弓").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW1, new ArrayList<>()
        );
        bow1.setAttribute(WeaponData.ATTACKBASE, 5);
        bow1.setAttribute(WeaponData.ENABLESLOT, 0);
        bow1.setAttribute(WeaponData.JOBLIMIT, 1);
        bow1.setAttribute(WeaponData.LEVELLIMIT, 0);

        WeaponData furnace1 = new WeaponData(FURNACE1, Material.RESIN_BRICK,
                Component.text("黑铁锅").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE1, new ArrayList<>()

        );
        furnace1.setAttribute(WeaponData.ATTACKBASE, 5.0);
        furnace1.setAttribute(WeaponData.ENABLESLOT, 40.0);
        furnace1.setAttribute(WeaponData.JOBLIMIT, 2.0);
        furnace1.setAttribute(WeaponData.LEVELLIMIT, 0.0);
        //endregion

        //region 二阶
        WeaponData sword20 = new WeaponData(SWORD20, Material.NETHERITE_SWORD,
                Component.text("青铜剑[金钟]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD20, new ArrayList<>()
        );

        WeaponData sword21 = new WeaponData(SWORD21, Material.NETHERITE_SWORD,
                Component.text("开山刀[破军]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD21, new ArrayList<>()
        );

        WeaponData sword22 = new WeaponData(SWORD22, Material.NETHERITE_SWORD,
                Component.text("穿林矛[探云]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD22, new ArrayList<>()
        );

        WeaponData sword23 = new WeaponData(SWORD23, Material.NETHERITE_SWORD,
                Component.text("斫岩斧[撼岳]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD23, new ArrayList<>()
        );

        WeaponData bow20 = new WeaponData(BOW20, Material.BOW,
                Component.text("青铜弓[狙击]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW20, new ArrayList<>()
        );

        WeaponData bow21 = new WeaponData(BOW21, Material.CROSSBOW,
                Component.text("黑铁弩[游侠]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW21, new ArrayList<>()
        );

        WeaponData bow22 = new WeaponData(BOW22, Material.BOW,
                Component.text("玄水弓[灵箭]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW22, new ArrayList<>()
        );

        WeaponData bow23 = new WeaponData(BOW23, Material.CROSSBOW,
                Component.text("飞叶弩[穿星]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW23, new ArrayList<>()
        );

        WeaponData furnace2 = new WeaponData(FURNACE2, Material.RESIN_BRICK,
                Component.text("青铜锅").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE2, new ArrayList<>()
        );
        //endregion

        //region 三阶
        WeaponData sword30 = new WeaponData(SWORD30, Material.NETHERITE_SWORD,
                Component.text("赤铜剑[金钟]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD30, new ArrayList<>()
        );

        WeaponData sword31 = new WeaponData(SWORD31, Material.NETHERITE_SWORD,
                Component.text("巨铜战锤[破军]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD31, new ArrayList<>()
        );

        WeaponData sword32 = new WeaponData(SWORD32, Material.NETHERITE_SWORD,
                Component.text("红缨枪[探云]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD32, new ArrayList<>()
        );

        WeaponData sword33 = new WeaponData(SWORD33, Material.NETHERITE_SWORD,
                Component.text("金焰钧[撼岳]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD33, new ArrayList<>()
        );

        WeaponData bow30 = new WeaponData(BOW30, Material.BOW,
                Component.text("焰铁弓[狙击]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW30, new ArrayList<>()
        );

        WeaponData bow31 = new WeaponData(BOW31, Material.CROSSBOW,
                Component.text("红翎弩[游侠]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW31, new ArrayList<>()
        );

        WeaponData bow32 = new WeaponData(BOW32, Material.BOW,
                Component.text("烈日弓[灵箭]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW32, new ArrayList<>()
        );

        WeaponData bow33 = new WeaponData(BOW33, Material.CROSSBOW,
                Component.text("妖灵弩[穿星]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW33, new ArrayList<>()
        );

        WeaponData furnace3 = new WeaponData(FURNACE3, Material.RESIN_BRICK,
                Component.text("炼丹炉").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE3, new ArrayList<>()
        );
        //endregion

        //region 四阶
        WeaponData sword40 = new WeaponData(SWORD40, Material.NETHERITE_SWORD,
                Component.text("太极剑[金钟]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD40, new ArrayList<>()
        );

        WeaponData sword41 = new WeaponData(SWORD41, Material.NETHERITE_SWORD,
                Component.text("破空斧[破军]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD41, new ArrayList<>()
        );

        WeaponData sword42 = new WeaponData(SWORD42, Material.NETHERITE_SWORD,
                Component.text("冰晶槊[探云]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD42, new ArrayList<>()
        );

        WeaponData sword43 = new WeaponData(SWORD43, Material.NETHERITE_SWORD,
                Component.text("镇魂斨[撼岳]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD43, new ArrayList<>()
        );

        WeaponData bow40 = new WeaponData(BOW40, Material.BOW,
                Component.text("重锤弓[狙击]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW40, new ArrayList<>()
        );

        WeaponData bow41 = new WeaponData(BOW41, Material.CROSSBOW,
                Component.text("蛟龙弩[游侠]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW41, new ArrayList<>()
        );

        WeaponData bow42 = new WeaponData(BOW42, Material.BOW,
                Component.text("幽魂弓[灵箭]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW42, new ArrayList<>()
        );

        WeaponData bow43 = new WeaponData(BOW43, Material.CROSSBOW,
                Component.text("游龙弩[穿星]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW43, new ArrayList<>()
        );

        WeaponData furnace4 = new WeaponData(FURNACE4, Material.RESIN_BRICK,
                Component.text("锁魂炉").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE4, new ArrayList<>()
        );
        //endregion

        //region 五阶
        WeaponData sword50 = new WeaponData(SWORD50, Material.NETHERITE_SWORD,
                Component.text("昆仑飞仙剑[金钟]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD50, new ArrayList<>()
        );

        WeaponData sword51 = new WeaponData(SWORD51, Material.NETHERITE_SWORD,
                Component.text("三宝玉如意[破军]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD51, new ArrayList<>()
        );

        WeaponData sword52 = new WeaponData(SWORD52, Material.NETHERITE_SWORD,
                Component.text("龙吟贯天戟[探云]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD52, new ArrayList<>()
        );

        WeaponData sword53 = new WeaponData(SWORD53, Material.NETHERITE_SWORD,
                Component.text("玄钢震地钺[撼岳]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD53, new ArrayList<>()
        );

        WeaponData bow50 = new WeaponData(BOW50, Material.BOW,
                Component.text("北斗灭神弓[狙击]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW50, new ArrayList<>()
        );

        WeaponData bow51 = new WeaponData(BOW51, Material.CROSSBOW,
                Component.text("日月流星弩[游侠]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW51, new ArrayList<>()
        );

        WeaponData bow52 = new WeaponData(BOW52, Material.BOW,
                Component.text("辉光曜阳弓[灵箭]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW52, new ArrayList<>()
        );

        WeaponData bow53 = new WeaponData(BOW53, Material.CROSSBOW,
                Component.text("瀚宇星海弩[穿星]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW53, new ArrayList<>()
        );

        WeaponData furnace5 = new WeaponData(FURNACE5, Material.RESIN_BRICK,
                Component.text("七煞鼎").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE5, new ArrayList<>()
        );
        //endregion

        //region 六阶
        WeaponData sword60 = new WeaponData(SWORD60, Material.NETHERITE_SWORD,
                Component.text("诛仙[金钟]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD60, new ArrayList<>()
        );

        WeaponData sword61 = new WeaponData(SWORD61, Material.NETHERITE_SWORD,
                Component.text("玉琮[破军]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD61, new ArrayList<>()
        );

        WeaponData sword62 = new WeaponData(SWORD62, Material.NETHERITE_SWORD,
                Component.text("戈铤[探云]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD62, new ArrayList<>()
        );

        WeaponData sword63 = new WeaponData(SWORD63, Material.NETHERITE_SWORD,
                Component.text("开天[撼岳]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + SWORD63, new ArrayList<>()
        );

        WeaponData bow60 = new WeaponData(BOW60, Material.BOW,
                Component.text("腾蛟起凤[狙击]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW60, new ArrayList<>()
        );

        WeaponData bow61 = new WeaponData(BOW61, Material.CROSSBOW,
                Component.text("雁阵惊寒[游侠]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW61, new ArrayList<>()
        );

        WeaponData bow62 = new WeaponData(BOW62, Material.BOW,
                Component.text("邺水朱华[灵箭]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW62, new ArrayList<>()
        );

        WeaponData bow63 = new WeaponData(BOW63, Material.CROSSBOW,
                Component.text("舸舰弥津[穿星]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + BOW63, new ArrayList<>()
        );

        WeaponData furnace6 = new WeaponData(FURNACE6, Material.RESIN_BRICK,
                Component.text("亘古磐岩").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + FURNACE6, new ArrayList<>()
        );
        //endregion

        //region 金箍棒 金钟
        WeaponData legendsword60 = new WeaponData(LEGENDSWORD60, Material.NETHERITE_SWORD,
                Component.text("如意金箍棒[金钟]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + LEGENDSWORD60, new ArrayList<>()
        );
        //endregion

        //region 金箍棒 破军
        List<Component> legendsword61Lore = List.of(
                RARE_6.append(QUALITY_6),
                JOBLIMIT_0.append(LEVELLIMIT_60),
                Component.text("传说中齐天大圣孙悟空所使用的兵器").color(NamedTextColor.GRAY),
                Component.text("为太上老君所定制的「定海神珍铁」").color(NamedTextColor.GRAY),
                Component.text("重达一万三千五百斤，可自由变换长短大小").color(NamedTextColor.GRAY),
                Component.text("大禹治水后遗留在东海，后被孙悟空取走").color(NamedTextColor.GRAY),
                WEAPON_ATTRIBUTE,
                Component.text("进攻属性 +60   进攻属性 +30%").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                Component.text("攻击速度 +4").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                WEAPON_FEATURE,
                Component.text("[破军] 移动速度 +30%").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                WEAPON_SKILL,
                Component.text("§c[万钧斩]"),
                Component.text("§e[右键] §f冷却: §b5§f秒"),
                Component.text("§f下一次攻击对目标额外造成§b500%§f进攻属性的伤害"),
                Component.text("§c[齐天-斗战]"),
                Component.text("§e[下蹲+右键] §f冷却: §b30§f秒"),
                Component.text("§f进入§c[斗战]§f状态: 额外获得§b30%§f进攻属性，持续§b10§f秒"),
                Component.text("§f状态持续期间，每次击杀敌人都会使接下来§b2§f秒内移动速度增加§b30%"),
                Component.text("§f在这§b2§f秒内，可以通过§e[跳跃+下蹲]§f进行一次翻滚，向前方移动§b2§f格"),
                Component.text("§f该效果不可叠加，但每次击杀可重置持续时间"),
                WEAPON_ENHANCE,
                ENCHANCE_6,
                ENABLE_1
        );
        WeaponData legendsword61 = new WeaponData(LEGENDSWORD61, Material.NETHERITE_SWORD,
                Component.text("如意金箍棒").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false),
                WEAPON_MODEL + "legendsword6", legendsword61Lore);
        legendsword61.setAttribute(WeaponData.ATTACKBASE, 60.0);
        legendsword61.setAttribute(WeaponData.ARMORBASE, 10.0);
        legendsword61.setAttribute(WeaponData.HEALTHBASE, 10.0);
        legendsword61.setAttribute(WeaponData.ATTACKPERS, 30.0);
        legendsword61.setAttribute(WeaponData.ATTACKSPEED, 0.0);
        legendsword61.setAttribute(WeaponData.SPEED, 30.0);
        legendsword61.setAttribute(WeaponData.ENABLESLOT, 0.0);
        legendsword61.setAttribute(WeaponData.LEVELLIMIT, 60.0);
        legendsword61.setAttribute(WeaponData.JOBLIMIT, 0.0);
        legendsword61.setAttribute(WeaponData.ENCHANTLEVEL, 6.0);
        //endregion

        //region 金箍棒 探云
        WeaponData legendsword62 = new WeaponData(LEGENDSWORD62, Material.NETHERITE_SWORD,
                Component.text("如意金箍棒[探云]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + LEGENDSWORD62, new ArrayList<>()
        );
        //endregion

        //region 金箍棒 撼岳
        WeaponData legendsword63 = new WeaponData(LEGENDSWORD63, Material.NETHERITE_SWORD,
                Component.text("如意金箍棒[撼岳]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + LEGENDSWORD63, new ArrayList<>()
        );
        //endregion

        //region 逐日 狙击
        WeaponData legendbow60 = new WeaponData(LEGENDBOW60, Material.BOW,
                Component.text("逐日[狙击]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + "legendbow6", List.of(ENABLE_1)
        );
        legendbow60.setAttribute(WeaponData.ENABLESLOT, 0.0);
        legendbow60.setAttribute(WeaponData.JOBLIMIT, 1.0);
        legendbow60.setAttribute(WeaponData.LEVELLIMIT, 60.0);
        //endregion

        //region 逐日 游侠
        WeaponData legendbow61 = new WeaponData(LEGENDBOW61, Material.CROSSBOW,
                Component.text("逐日[游侠]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + "legendbow6", new ArrayList<>()
        );
        //endregion

        //region 逐日 灵箭
        WeaponData legendbow62 = new WeaponData(LEGENDBOW62, Material.BOW,
                Component.text("逐日[灵箭]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + "legendbow6", new ArrayList<>()
        );
        //endregion

        //region 逐日 穿星
        WeaponData legendbow63 = new WeaponData(LEGENDBOW63, Material.CROSSBOW,
                Component.text("逐日[穿星]").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + "legendbow6", new ArrayList<>()
        );
        //endregion

        //region 混元神鼎
        WeaponData legendfurnace6 = new WeaponData(LEGENDFURNACE6, Material.RESIN_BRICK,
                Component.text("混元神鼎").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false),
                WEAPON_MODEL + LEGENDFURNACE6, List.of(ENABLE_OFF));
        legendfurnace6.setAttribute(WeaponData.ENABLESLOT, 40.0);
        legendfurnace6.setAttribute(WeaponData.JOBLIMIT, 2.0);
        legendfurnace6.setAttribute(WeaponData.LEVELLIMIT, 60.0);
        //endregion

        //region 统一注册
        WeaponManager.addWeapon(sword1);
        WeaponManager.addWeapon(bow1);
        WeaponManager.addWeapon(furnace1);
        WeaponManager.addWeapon(sword20);
        WeaponManager.addWeapon(sword21);
        WeaponManager.addWeapon(sword22);
        WeaponManager.addWeapon(sword23);
        WeaponManager.addWeapon(bow20);
        WeaponManager.addWeapon(bow21);
        WeaponManager.addWeapon(bow22);
        WeaponManager.addWeapon(bow23);
        WeaponManager.addWeapon(furnace2);
        WeaponManager.addWeapon(sword30);
        WeaponManager.addWeapon(sword31);
        WeaponManager.addWeapon(sword32);
        WeaponManager.addWeapon(sword33);
        WeaponManager.addWeapon(bow30);
        WeaponManager.addWeapon(bow31);
        WeaponManager.addWeapon(bow32);
        WeaponManager.addWeapon(bow33);
        WeaponManager.addWeapon(furnace3);
        WeaponManager.addWeapon(sword40);
        WeaponManager.addWeapon(sword41);
        WeaponManager.addWeapon(sword42);
        WeaponManager.addWeapon(sword43);
        WeaponManager.addWeapon(bow40);
        WeaponManager.addWeapon(bow41);
        WeaponManager.addWeapon(bow42);
        WeaponManager.addWeapon(bow43);
        WeaponManager.addWeapon(furnace4);
        WeaponManager.addWeapon(sword50);
        WeaponManager.addWeapon(sword51);
        WeaponManager.addWeapon(sword52);
        WeaponManager.addWeapon(sword53);
        WeaponManager.addWeapon(bow50);
        WeaponManager.addWeapon(bow51);
        WeaponManager.addWeapon(bow52);
        WeaponManager.addWeapon(bow53);
        WeaponManager.addWeapon(furnace5);
        WeaponManager.addWeapon(sword60);
        WeaponManager.addWeapon(sword61);
        WeaponManager.addWeapon(sword62);
        WeaponManager.addWeapon(sword63);
        WeaponManager.addWeapon(bow60);
        WeaponManager.addWeapon(bow61);
        WeaponManager.addWeapon(bow62);
        WeaponManager.addWeapon(bow63);
        WeaponManager.addWeapon(furnace6);
        WeaponManager.addWeapon(legendsword60);
        WeaponManager.addWeapon(legendsword61);
        WeaponManager.addWeapon(legendsword62);
        WeaponManager.addWeapon(legendsword63);
        WeaponManager.addWeapon(legendbow60);
        WeaponManager.addWeapon(legendbow61);
        WeaponManager.addWeapon(legendbow62);
        WeaponManager.addWeapon(legendbow63);
        WeaponManager.addWeapon(legendfurnace6);
        //endregion
    }
}
