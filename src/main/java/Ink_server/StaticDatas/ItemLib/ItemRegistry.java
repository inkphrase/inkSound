package Ink_server.StaticDatas.ItemLib;

import Ink_server.InkSound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

import static Ink_server.StaticDatas.ItemLib.ItemDataManager.addItem;

public class ItemRegistry {

    public static void init(){
        InkSound.getInstance().getLogger().info("物品库已注册！");
    }

    // ==================== 物品ID常量 ====================
    //region Materials - 2阶
    public static final String WEAPONMETAL2 = "weaponmetal2";
    public static final String ARMORCORE2 = "armorcore2";
    public static final String EAST_WEAPONFORGE_2 = "east.weaponforge.2";
    public static final String EAST_ARMORFORGE_2 = "east.armorforge.2";
    public static final String EAST_MAGICFORGE_2 = "east.magicforge.2";
    public static final String SOUTH_WEAPONFORGE_2 = "south.weaponforge.2";
    public static final String SOUTH_ARMORFORGE_2 = "south.armorforge.2";
    public static final String SOUTH_MAGICFORGE_2 = "south.magicforge.2";
    public static final String WEST_WEAPONFORGE_2 = "west.weaponforge.2";
    public static final String WEST_ARMORFORGE_2 = "west.armorforge.2";
    public static final String WEST_MAGICFORGE_2 = "west.magicforge.2";
    public static final String NORTH_WEAPONFORGE_2 = "north.weaponforge.2";
    public static final String NORTH_ARMORFORGE_2 = "north.armorforge.2";
    public static final String NORTH_MAGICFORGE_2 = "north.magicforge.2";
    public static final String EAST_ZOMBIECORE_2 = "east.zombiecore.2";
    public static final String EAST_BONE_2 = "east.bone.2";
    public static final String EAST_LINE_2 = "east.line.2";
    public static final String EAST_CLOTH_2 = "east.cloth.2";
    public static final String EAST_CRYSTALBONE_2 = "east.crystalbone.2";
    public static final String EAST_CRYSTALCLAW_2 = "east.crystalclaw.2";
    public static final String SOUTH_GOLDSAND_2 = "south.goldsand.2";
    public static final String SOUTH_LINE_2 = "south.line.2";
    public static final String SOUTH_IRON_2 = "south.iron.2";
    public static final String SOUTH_CLOTH_2 = "south.cloth.2";
    public static final String SOUTH_REDCRYSTAL_2 = "south.redcrystal.2";
    public static final String SOUTH_BLUECRYSTAL_2 = "south.bluecrystal.2";
    public static final String WEST_GOLD_2 = "west.gold.2";
    public static final String WEST_HERBAL_2 = "west.herbal.2";
    public static final String WEST_CLOTH_2 = "west.cloth.2";
    public static final String WEST_LINE_2 = "west.line.2";
    public static final String WEST_SLAVEBEAD_2 = "west.slavebead.2";
    public static final String WEST_BLOODBEAD_2 = "west.bloodbead.2";
    public static final String NORTH_ARROW_2 = "north.arrow.2";
    public static final String NORTH_SHELL_2 = "north.shell.2";
    public static final String NORTH_BEAD_2 = "north.bead.2";
    public static final String NORTH_LINE_2 = "north.line.2";
    public static final String NORTH_EYE_2 = "north.eye.2";
    public static final String NORTH_MAGALINE_2 = "north.magaline.2";
    //endregion

    //region Materials - 3阶
    public static final String WEAPONMETAL3 = "weaponmetal3";
    public static final String ARMORCORE3 = "armorcore3";
    public static final String EAST_WEAPONFORGE_3 = "east.weaponforge.3";
    public static final String EAST_ARMORFORGE_3 = "east.armorforge.3";
    public static final String EAST_MAGICFORGE_3 = "east.magicforge.3";
    public static final String SOUTH_WEAPONFORGE_3 = "south.weaponforge.3";
    public static final String SOUTH_ARMORFORGE_3 = "south.armorforge.3";
    public static final String SOUTH_MAGICFORGE_3 = "south.magicforge.3";
    public static final String WEST_WEAPONFORGE_3 = "west.weaponforge.3";
    public static final String WEST_ARMORFORGE_3 = "west.armorforge.3";
    public static final String WEST_MAGICFORGE_3 = "west.magicforge.3";
    public static final String NORTH_WEAPONFORGE_3 = "north.weaponforge.3";
    public static final String NORTH_ARMORFORGE_3 = "north.armorforge.3";
    public static final String NORTH_MAGICFORGE_3 = "north.magicforge.3";
    public static final String EAST_CLOTH_3 = "east.cloth.3";
    public static final String EAST_WOOD_3 = "east.wood.3";
    public static final String EAST_BONE_3 = "east.bone.3";
    public static final String EAST_MARROW_3 = "east.marrow.3";
    public static final String EAST_BAMBOO_3 = "east.bamboo.3";
    public static final String EAST_CLAW_3 = "east.claw.3";
    public static final String EAST_SLIP_3 = "east.slip.3";
    public static final String EAST_CRYSTAL_3 = "east.crystal.3";
    public static final String SOUTH_SPIKE_3 = "south.spike.3";
    public static final String SOUTH_SHELL_3 = "south.shell.3";
    public static final String SOUTH_STONE_3 = "south.stone.3";
    public static final String SOUTH_BEAD_3 = "south.bead.3";
    public static final String SOUTH_FIN_3 = "south.fin.3";
    public static final String SOUTH_BONE_3 = "south.bone.3";
    public static final String SOUTH_FIRECORE_3 = "south.firecore.3";
    public static final String SOUTH_WATERCORE_3 = "south.watercore.3";
    public static final String WEST_GOLDSAND_3 = "west.goldsand.3";
    public static final String WEST_GOLDMETAL_3 = "west.goldmetal.3";
    public static final String WEST_MARROW_3 = "west.marrow.3";
    public static final String WEST_CLOTH_3 = "west.cloth.3";
    public static final String WEST_LINE_3 = "west.line.3";
    public static final String WEST_TOOTH_3 = "west.tooth.3";
    public static final String WEST_BONE_3 = "west.bone.3";
    public static final String WEST_FINGER_3 = "west.finger.3";
    public static final String NORTH_BLADE_3 = "north.blade.3";
    public static final String NORTH_BONE_3 = "north.bone.3";
    public static final String NORTH_CLOTH_3 = "north.cloth.3";
    public static final String NORTH_LINE_3 = "north.line.3";
    public static final String NORTH_LANCER_3 = "north.lancer.3";
    public static final String NORTH_BEAD_3 = "north.bead.3";
    public static final String NORTH_GOLDFEATHER_3 = "north.goldfeather.3";
    public static final String NORTH_GOLDEYE_3 = "north.goldeye.3";
    //endregion

    //region Materials - 4阶
    public static final String WEAPONMETAL4 = "weaponmetal4";
    public static final String ARMORCORE4 = "armorcore4";
    public static final String EAST_WEAPONFORGE_4 = "east.weaponforge.4";
    public static final String EAST_ARMORFORGE_4 = "east.armorforge.4";
    public static final String EAST_MAGICFORGE_4 = "east.magicforge.4";
    public static final String SOUTH_WEAPONFORGE_4 = "south.weaponforge.4";
    public static final String SOUTH_ARMORFORGE_4 = "south.armorforge.4";
    public static final String SOUTH_MAGICFORGE_4 = "south.magicforge.4";
    public static final String WEST_WEAPONFORGE_4 = "west.weaponforge.4";
    public static final String WEST_ARMORFORGE_4 = "west.armorforge.4";
    public static final String WEST_MAGICFORGE_4 = "west.magicforge.4";
    public static final String NORTH_WEAPONFORGE_4 = "north.weaponforge.4";
    public static final String NORTH_ARMORFORGE_4 = "north.armorforge.4";
    public static final String NORTH_MAGICFORGE_4 = "north.magicforge.4";
    public static final String EAST_BUGTOOTH_4 = "east.bugtooth.4";
    public static final String EAST_EXTRACT_4 = "east.extract.4";
    public static final String EAST_BEAD_4 = "east.bead.4";
    public static final String EAST_BONE_4 = "east.bone.4";
    public static final String EAST_SPIDERTOOTH_4 = "east.spidertooth.4";
    public static final String EAST_SPIKE_4 = "east.spike.4";
    public static final String EAST_EYE_4 = "east.eye.4";
    public static final String EAST_SHELL_4 = "east.shell.4";
    public static final String EAST_AMBER_4 = "east.amber.4";
    public static final String EAST_VINE_4 = "east.vine.4";
    public static final String SOUTH_FIRE_4 = "south.fire.4";
    public static final String SOUTH_ROD_4 = "south.rod.4";
    public static final String SOUTH_ARM_4 = "south.arm.4";
    public static final String SOUTH_AXE_4 = "south.axe.4";
    public static final String SOUTH_CORE_4 = "south.core.4";
    public static final String SOUTH_BODY_4 = "south.body.4";
    public static final String SOUTH_DEVILMUD_4 = "south.devilmud.4";
    public static final String SOUTH_DEVILSHELL_4 = "south.devilshell.4";
    public static final String SOUTH_DEVILBEAD_4 = "south.devilbead.4";
    public static final String SOUTH_DEVILNAIL_4 = "south.devilnail.4";
    public static final String WEST_CLAW_4 = "west.claw.4";
    public static final String WEST_CLOTH_4 = "west.cloth.4";
    public static final String WEST_IRON_4 = "west.iron.4";
    public static final String WEST_BONE_4 = "west.bone.4";
    public static final String WEST_EYE_4 = "west.eye.4";
    public static final String WEST_BODY_4 = "west.body.4";
    public static final String WEST_CRYSTAL_4 = "west.crystal.4";
    public static final String WEST_CORE_4 = "west.core.4";
    public static final String WEST_WING_4 = "west.wing.4";
    public static final String WEST_SPIKE_4 = "west.spike.4";
    public static final String NORTH_CLOTH_4 = "north.cloth.4";
    public static final String NORTH_FIRE_4 = "north.fire.4";
    public static final String NORTH_BONE_4 = "north.bone.4";
    public static final String NORTH_CLAW_4 = "north.claw.4";
    public static final String NORTH_MUD_4 = "north.mud.4";
    public static final String NORTH_DIRT_4 = "north.dirt.4";
    public static final String NORTH_FEELER_4 = "north.feeler.4";
    public static final String NORTH_HEART_4 = "north.heart.4";
    public static final String NORTH_TOOTH_4 = "north.tooth.4";
    public static final String NORTH_SOULBONE_4 = "north.soulbone.4";
    //endregion

    //region Materials - 5阶
    public static final String WEAPONMETAL5 = "weaponmetal5";
    public static final String ARMORCORE5 = "armorcore5";
    public static final String EAST_WEAPONFORGE_5 = "east.weaponforge.5";
    public static final String EAST_ARMORFORGE_5 = "east.armorforge.5";
    public static final String EAST_MAGICFORGE_5 = "east.magicforge.5";
    public static final String SOUTH_WEAPONFORGE_5 = "south.weaponforge.5";
    public static final String SOUTH_ARMORFORGE_5 = "south.armorforge.5";
    public static final String SOUTH_MAGICFORGE_5 = "south.magicforge.5";
    public static final String WEST_WEAPONFORGE_5 = "west.weaponforge.5";
    public static final String WEST_ARMORFORGE_5 = "west.armorforge.5";
    public static final String WEST_MAGICFORGE_5 = "west.magicforge.5";
    public static final String NORTH_WEAPONFORGE_5 = "north.weaponforge.5";
    public static final String NORTH_ARMORFORGE_5 = "north.armorforge.5";
    public static final String NORTH_MAGICFORGE_5 = "north.magicforge.5";
    //endregion

    //region Others
    public static final String REBORN_STONE = "rebornStone";
    public static final String MONEY0 = "money0";
    public static final String MONEY1 = "money1";
    public static final String MONEY2 = "money2";
    public static final String MONEY3 = "money3";
    public static final String MONEY4 = "money4";
    public static final String ELEMENT_METAL = "element.metal";
    public static final String ELEMENT_WOOD = "element.wood";
    public static final String ELEMENT_WATER = "element.water";
    public static final String ELEMENT_LAVA = "element.lava";
    public static final String ELEMENT_EARTH = "element.earth";
    public static final String REFINED_METAL = "refined.metal";
    public static final String REFINED_WOOD = "refined.wood";
    public static final String REFINED_WATER = "refined.water";
    public static final String REFINED_LAVA = "refined.lava";
    public static final String REFINED_EARTH = "refined.earth";
    public static final String ADVANCED_METAL = "advanced.metal";
    public static final String ADVANCED_WOOD = "advanced.wood";
    public static final String ADVANCED_WATER = "advanced.water";
    public static final String ADVANCED_LAVA = "advanced.lava";
    public static final String ADVANCED_EARTH = "advanced.earth";
    public static final String WEST_GOODS_2 = "west.goods.2";
    public static final String WEST_GOODS_3 = "west.goods.3";
    public static final String WEST_GOODS_4 = "west.goods.4";
    public static final String SOUTH_GOODS_2 = "south.goods.2";
    public static final String SOUTH_GOODS_3 = "south.goods.3";
    public static final String SOUTH_GOODS_4 = "south.goods.4";
    public static final String EAST_GOODS_2 = "east.goods.2";
    public static final String EAST_GOODS_3 = "east.goods.3";
    public static final String EAST_GOODS_4 = "east.goods.4";
    public static final String NORTH_GOODS_2 = "north.goods.2";
    public static final String NORTH_GOODS_3 = "north.goods.3";
    public static final String NORTH_GOODS_4 = "north.goods.4";
    //endregion

    static {
        // ==================== 模型前缀常量 ====================
        final String MATERIALS_MODEL = "materials/";
        final String OTHERS_MODEL = "others/";

        //region ==================== 稀有度常量 ====================
        final Component RARITY_2 = Component.text("稀有度:★★")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.ITALIC, false);

        final Component RARITY_3 = Component.text("稀有度:★★★")
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false);

        final Component RARITY_4 = Component.text("稀有度:★★★★")
                .color(NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false);

        final Component RARITY_5 = Component.text("稀有度:★★★★★")
                .color(NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, false);
        //endregion


        //region 二阶核心
        ItemData weaponmetal2 = new ItemData(WEAPONMETAL2, Material.RAW_IRON,
                Component.text("青钢砂").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEAPONMETAL2,
                List.of(RARITY_2, Component.text("闪烁着青色光泽的金属粉末").color(NamedTextColor.GRAY), Component.text("可塑性强，适合制作二阶武器").color(NamedTextColor.GRAY)));
        addItem(weaponmetal2);

        ItemData armorcore2 = new ItemData(ARMORCORE2, Material.RAW_GOLD,
                Component.text("二阶装备原核").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + ARMORCORE2,
                List.of(RARITY_2, Component.text("稀有的装备核心").color(NamedTextColor.GRAY), Component.text("可以制作二阶防具").color(NamedTextColor.GRAY)));
        addItem(armorcore2);

        ItemData eastWeaponforge2 = new ItemData(EAST_WEAPONFORGE_2, Material.DIAMOND,
                Component.text("二阶武器锻核[森林]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.2",
                List.of(RARITY_2, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶武器").color(NamedTextColor.GRAY)));
        addItem(eastWeaponforge2);

        ItemData eastArmorforge2 = new ItemData(EAST_ARMORFORGE_2, Material.DIAMOND,
                Component.text("二阶防具锻核[森林]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.2",
                List.of(RARITY_2, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶防具").color(NamedTextColor.GRAY)));
        addItem(eastArmorforge2);

        ItemData eastMagicforge2 = new ItemData(EAST_MAGICFORGE_2, Material.DIAMOND,
                Component.text("二阶法宝锻核[森林]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.2",
                List.of(RARITY_2, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶法宝").color(NamedTextColor.GRAY)));
        addItem(eastMagicforge2);

        ItemData southWeaponforge2 = new ItemData(SOUTH_WEAPONFORGE_2, Material.DIAMOND,
                Component.text("二阶武器锻核[沙漠]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.2",
                List.of(RARITY_2, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶武器").color(NamedTextColor.GRAY)));
        addItem(southWeaponforge2);

        ItemData southArmorforge2 = new ItemData(SOUTH_ARMORFORGE_2, Material.DIAMOND,
                Component.text("二阶防具锻核[沙漠]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.2",
                List.of(RARITY_2, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶防具").color(NamedTextColor.GRAY)));
        addItem(southArmorforge2);

        ItemData southMagicforge2 = new ItemData(SOUTH_MAGICFORGE_2, Material.DIAMOND,
                Component.text("二阶法宝锻核[沙漠]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.2",
                List.of(RARITY_2, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶法宝").color(NamedTextColor.GRAY)));
        addItem(southMagicforge2);

        ItemData westWeaponforge2 = new ItemData(WEST_WEAPONFORGE_2, Material.DIAMOND,
                Component.text("二阶武器锻核[雪山]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.2",
                List.of(RARITY_2, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶武器").color(NamedTextColor.GRAY)));
        addItem(westWeaponforge2);

        ItemData westArmorforge2 = new ItemData(WEST_ARMORFORGE_2, Material.DIAMOND,
                Component.text("二阶防具锻核[雪山]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.2",
                List.of(RARITY_2, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶防具").color(NamedTextColor.GRAY)));
        addItem(westArmorforge2);

        ItemData westMagicforge2 = new ItemData(WEST_MAGICFORGE_2, Material.DIAMOND,
                Component.text("二阶法宝锻核[雪山]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.2",
                List.of(RARITY_2, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶法宝").color(NamedTextColor.GRAY)));
        addItem(westMagicforge2);

        ItemData northWeaponforge2 = new ItemData(NORTH_WEAPONFORGE_2, Material.DIAMOND,
                Component.text("二阶武器锻核[湖泊]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.2",
                List.of(RARITY_2, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶武器").color(NamedTextColor.GRAY)));
        addItem(northWeaponforge2);

        ItemData northArmorforge2 = new ItemData(NORTH_ARMORFORGE_2, Material.DIAMOND,
                Component.text("二阶防具锻核[湖泊]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.2",
                List.of(RARITY_2, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶防具").color(NamedTextColor.GRAY)));
        addItem(northArmorforge2);

        ItemData northMagicforge2 = new ItemData(NORTH_MAGICFORGE_2, Material.DIAMOND,
                Component.text("二阶法宝锻核[湖泊]").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.2",
                List.of(RARITY_2, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造二阶法宝").color(NamedTextColor.GRAY)));
        addItem(northMagicforge2);
        //endregion

        //region 二阶东方
        ItemData eastZombiecore2 = new ItemData(EAST_ZOMBIECORE_2, Material.COPPER_NUGGET,
                Component.text("腐尸结晶").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_ZOMBIECORE_2,
                List.of(RARITY_2, Component.text("被魔气侵染的妖族内丹").color(NamedTextColor.GRAY), Component.text("原本蕴含的自然灵力已扭曲成魔化妖气").color(NamedTextColor.GRAY)));
        addItem(eastZombiecore2);

        ItemData eastBone2 = new ItemData(EAST_BONE_2, Material.BONE,
                Component.text("硬骨").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BONE_2,
                List.of(RARITY_2, Component.text("被魔气侵染的妖族骨骼").color(NamedTextColor.GRAY), Component.text("散发的邪气令人胆寒").color(NamedTextColor.GRAY)));
        addItem(eastBone2);

        ItemData eastLine2 = new ItemData(EAST_LINE_2, Material.STRING,
                Component.text("蛛丝").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_LINE_2,
                List.of(RARITY_2, Component.text("蛛妖所生产的丝线").color(NamedTextColor.GRAY), Component.text("受魔气侵染后韧性有所提升").color(NamedTextColor.GRAY)));
        addItem(eastLine2);

        ItemData eastCloth2 = new ItemData(EAST_CLOTH_2, Material.LEATHER,
                Component.text("破布").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CLOTH_2,
                List.of(RARITY_2, Component.text("魔化妖族穿着的粗布皮").color(NamedTextColor.GRAY), Component.text("已经有些破损，但足够结实").color(NamedTextColor.GRAY)));
        addItem(eastCloth2);

        ItemData eastCrystalbone2 = new ItemData(EAST_CRYSTALBONE_2, Material.AMETHYST_SHARD,
                Component.text("晶化骨").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CRYSTALBONE_2,
                List.of(RARITY_2, Component.text("呈水晶状的坚硬臂骨").color(NamedTextColor.GRAY), Component.text("其中纠缠着自然灵力与魔化妖气").color(NamedTextColor.GRAY)));
        addItem(eastCrystalbone2);

        ItemData eastCrystalclaw2 = new ItemData(EAST_CRYSTALCLAW_2, Material.RABBIT_FOOT,
                Component.text("寒骨爪").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CRYSTALCLAW_2,
                List.of(RARITY_2, Component.text("散发寒气的妖魔骨爪").color(NamedTextColor.GRAY), Component.text("残留的自然灵力闪烁着晶莹").color(NamedTextColor.GRAY)));
        addItem(eastCrystalclaw2);
        //endregion

        //region 二阶南方
        ItemData southGoldsand2 = new ItemData(SOUTH_GOLDSAND_2, Material.GOLD_NUGGET,
                Component.text("金砂").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_GOLDSAND_2,
                List.of(RARITY_2, Component.text("炎砂大漠中的零散金砂").color(NamedTextColor.GRAY), Component.text("马贼团曾大规模开采").color(NamedTextColor.GRAY)));
        addItem(southGoldsand2);

        ItemData southLine2 = new ItemData(SOUTH_LINE_2, Material.STRING,
                Component.text("蚕丝").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_LINE_2,
                List.of(RARITY_2, Component.text("在马贼身上搜刮到的精致丝线").color(NamedTextColor.GRAY), Component.text("据说在皇城有相当大的市场").color(NamedTextColor.GRAY)));
        addItem(southLine2);

        ItemData southIron2 = new ItemData(SOUTH_IRON_2, Material.IRON_NUGGET,
                Component.text("精铁").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_IRON_2,
                List.of(RARITY_2, Component.text("炎砂大漠的重要矿产").color(NamedTextColor.GRAY), Component.text("矿脉已被马贼团霸占，售价居高不下").color(NamedTextColor.GRAY)));
        addItem(southIron2);

        ItemData southCloth2 = new ItemData(SOUTH_CLOTH_2, Material.LEATHER,
                Component.text("绸缎").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_CLOTH_2,
                List.of(RARITY_2, Component.text("沙漠商人销往皇城的精致布料").color(NamedTextColor.GRAY), Component.text("马贼祸乱后，供应量大幅减少").color(NamedTextColor.GRAY)));
        addItem(southCloth2);

        ItemData southRedcrystal2 = new ItemData(SOUTH_REDCRYSTAL_2, Material.REDSTONE,
                Component.text("红宝石").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_REDCRYSTAL_2,
                List.of(RARITY_2, Component.text("马贼团所抢夺的红色宝石").color(NamedTextColor.GRAY), Component.text("散发着温热的灵力").color(NamedTextColor.GRAY)));
        addItem(southRedcrystal2);

        ItemData southBluecrystal2 = new ItemData(SOUTH_BLUECRYSTAL_2, Material.LAPIS_LAZULI,
                Component.text("蓝宝石").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_BLUECRYSTAL_2,
                List.of(RARITY_2, Component.text("马贼团所抢夺的蓝色宝石").color(NamedTextColor.GRAY), Component.text("散发着清冷的灵力").color(NamedTextColor.GRAY)));
        addItem(southBluecrystal2);
        //endregion

        //region 二阶西方
        ItemData westGold2 = new ItemData(WEST_GOLD_2, Material.GOLD_NUGGET,
                Component.text("金矿").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_GOLD_2,
                List.of(RARITY_2, Component.text("黑市团伙偷挖的金矿").color(NamedTextColor.GRAY), Component.text("曾是虎爪山脉的重要特产").color(NamedTextColor.GRAY)));
        addItem(westGold2);

        ItemData westHerbal2 = new ItemData(WEST_HERBAL_2, Material.KELP,
                Component.text("草药").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_HERBAL_2,
                List.of(RARITY_2, Component.text("黑市团伙偷采的药草").color(NamedTextColor.GRAY), Component.text("具有极高的药用价值").color(NamedTextColor.GRAY)));
        addItem(westHerbal2);

        ItemData westCloth2 = new ItemData(WEST_CLOTH_2, Material.LEATHER,
                Component.text("兽皮").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CLOTH_2,
                List.of(RARITY_2, Component.text("黑市团伙豢养鬼兽的毛皮").color(NamedTextColor.GRAY), Component.text("材质坚韧，适合制作简易的皮具").color(NamedTextColor.GRAY)));
        addItem(westCloth2);

        ItemData westLine2 = new ItemData(WEST_LINE_2, Material.STRING,
                Component.text("黑血线").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_LINE_2,
                List.of(RARITY_2, Component.text("黑市成员在脉搏处缝上的黑线").color(NamedTextColor.GRAY), Component.text("浸满了血液，似乎是一种操纵手段").color(NamedTextColor.GRAY)));
        addItem(westLine2);

        ItemData westSlavebead2 = new ItemData(WEST_SLAVEBEAD_2, Material.EMERALD,
                Component.text("鬼役丹").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_SLAVEBEAD_2,
                List.of(RARITY_2, Component.text("黑市头目身上搜刮的黑色丹珠").color(NamedTextColor.GRAY), Component.text("似乎可以控制其他黑市成员").color(NamedTextColor.GRAY)));
        addItem(westSlavebead2);

        ItemData westBloodbead2 = new ItemData(WEST_BLOODBEAD_2, Material.EMERALD,
                Component.text("狂血珠").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_BLOODBEAD_2,
                List.of(RARITY_2, Component.text("黑市头目身上搜刮的血色丹珠").color(NamedTextColor.GRAY), Component.text("狂暴状态就是用其引动的").color(NamedTextColor.GRAY)));
        addItem(westBloodbead2);
        //endregion

        //region 二阶北方
        ItemData northArrow2 = new ItemData(NORTH_ARROW_2, Material.FLINT,
                Component.text("妖影箭").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_ARROW_2,
                List.of(RARITY_2, Component.text("缠绕着玄水灵气的箭矢").color(NamedTextColor.GRAY), Component.text("有着调动水元素的能力").color(NamedTextColor.GRAY)));
        addItem(northArrow2);

        ItemData northShell2 = new ItemData(NORTH_SHELL_2, Material.SHULKER_SHELL,
                Component.text("玄甲壳").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_SHELL_2,
                List.of(RARITY_2, Component.text("碧色的晶莹甲壳").color(NamedTextColor.GRAY), Component.text("其上的玄水灵气提供了更强的防御力").color(NamedTextColor.GRAY)));
        addItem(northShell2);

        ItemData northBead2 = new ItemData(NORTH_BEAD_2, Material.EMERALD,
                Component.text("玄灵珠").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_BEAD_2,
                List.of(RARITY_2, Component.text("散发着温润灵气的蓝色珍珠").color(NamedTextColor.GRAY), Component.text("可以当作施法媒介").color(NamedTextColor.GRAY)));
        addItem(northBead2);

        ItemData northLine2 = new ItemData(NORTH_LINE_2, Material.STRING,
                Component.text("玄水线").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_LINE_2,
                List.of(RARITY_2, Component.text("玄水灵气所编织的丝线").color(NamedTextColor.GRAY), Component.text("赋予了玄水生物水元素相性").color(NamedTextColor.GRAY)));
        addItem(northLine2);

        ItemData northEye2 = new ItemData(NORTH_EYE_2, Material.HEART_OF_THE_SEA,
                Component.text("望天瞳").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_EYE_2,
                List.of(RARITY_2, Component.text("千里神射手的蓝色眼瞳").color(NamedTextColor.GRAY), Component.text("似乎可以望穿天际").color(NamedTextColor.GRAY)));
        addItem(northEye2);

        ItemData northMagaline2 = new ItemData(NORTH_MAGALINE_2, Material.STRING,
                Component.text("贯云弦").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_MAGALINE_2,
                List.of(RARITY_2, Component.text("千里神射手所使用的弓弦").color(NamedTextColor.GRAY), Component.text("可以令箭矢贯穿云雾").color(NamedTextColor.GRAY)));
        addItem(northMagaline2);
        //endregion

        //region 三阶核心
        ItemData weaponmetal3 = new ItemData(WEAPONMETAL3, Material.RAW_IRON,
                Component.text("赤铜锭").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEAPONMETAL3,
                List.of(RARITY_3, Component.text("温度极高的赤色金属").color(NamedTextColor.GRAY), Component.text("可塑性强，适合制作三阶武器").color(NamedTextColor.GRAY)));
        addItem(weaponmetal3);

        ItemData armorcore3 = new ItemData(ARMORCORE3, Material.RAW_GOLD,
                Component.text("三阶装备原核").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + ARMORCORE3,
                List.of(RARITY_3, Component.text("稀有的装备核心").color(NamedTextColor.GRAY), Component.text("可以制作三阶防具").color(NamedTextColor.GRAY)));
        addItem(armorcore3);

        ItemData eastWeaponforge3 = new ItemData(EAST_WEAPONFORGE_3, Material.DIAMOND,
                Component.text("三阶武器锻核[森林]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.3",
                List.of(RARITY_3, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶武器").color(NamedTextColor.GRAY)));
        addItem(eastWeaponforge3);

        ItemData eastArmorforge3 = new ItemData(EAST_ARMORFORGE_3, Material.DIAMOND,
                Component.text("三阶防具锻核[森林]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.3",
                List.of(RARITY_3, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶防具").color(NamedTextColor.GRAY)));
        addItem(eastArmorforge3);

        ItemData eastMagicforge3 = new ItemData(EAST_MAGICFORGE_3, Material.DIAMOND,
                Component.text("三阶法宝锻核[森林]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.3",
                List.of(RARITY_3, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶法宝").color(NamedTextColor.GRAY)));
        addItem(eastMagicforge3);

        ItemData southWeaponforge3 = new ItemData(SOUTH_WEAPONFORGE_3, Material.DIAMOND,
                Component.text("三阶武器锻核[沙漠]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.3",
                List.of(RARITY_3, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶武器").color(NamedTextColor.GRAY)));
        addItem(southWeaponforge3);

        ItemData southArmorforge3 = new ItemData(SOUTH_ARMORFORGE_3, Material.DIAMOND,
                Component.text("三阶防具锻核[沙漠]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.3",
                List.of(RARITY_3, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶防具").color(NamedTextColor.GRAY)));
        addItem(southArmorforge3);

        ItemData southMagicforge3 = new ItemData(SOUTH_MAGICFORGE_3, Material.DIAMOND,
                Component.text("三阶法宝锻核[沙漠]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.3",
                List.of(RARITY_3, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶法宝").color(NamedTextColor.GRAY)));
        addItem(southMagicforge3);

        ItemData westWeaponforge3 = new ItemData(WEST_WEAPONFORGE_3, Material.DIAMOND,
                Component.text("三阶武器锻核[雪山]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.3",
                List.of(RARITY_3, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶武器").color(NamedTextColor.GRAY)));
        addItem(westWeaponforge3);

        ItemData westArmorforge3 = new ItemData(WEST_ARMORFORGE_3, Material.DIAMOND,
                Component.text("三阶防具锻核[雪山]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.3",
                List.of(RARITY_3, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶防具").color(NamedTextColor.GRAY)));
        addItem(westArmorforge3);

        ItemData westMagicforge3 = new ItemData(WEST_MAGICFORGE_3, Material.DIAMOND,
                Component.text("三阶法宝锻核[雪山]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.3",
                List.of(RARITY_3, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶法宝").color(NamedTextColor.GRAY)));
        addItem(westMagicforge3);

        ItemData northWeaponforge3 = new ItemData(NORTH_WEAPONFORGE_3, Material.DIAMOND,
                Component.text("三阶武器锻核[湖泊]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.3",
                List.of(RARITY_3, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶武器").color(NamedTextColor.GRAY)));
        addItem(northWeaponforge3);

        ItemData northArmorforge3 = new ItemData(NORTH_ARMORFORGE_3, Material.DIAMOND,
                Component.text("三阶防具锻核[湖泊]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.3",
                List.of(RARITY_3, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶防具").color(NamedTextColor.GRAY)));
        addItem(northArmorforge3);

        ItemData northMagicforge3 = new ItemData(NORTH_MAGICFORGE_3, Material.DIAMOND,
                Component.text("三阶法宝锻核[湖泊]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.3",
                List.of(RARITY_3, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造三阶法宝").color(NamedTextColor.GRAY)));
        addItem(northMagicforge3);
        //endregion

        //region 三阶东方
        ItemData eastCloth3 = new ItemData(EAST_CLOTH_3, Material.LEATHER,
                Component.text("裹尸布").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CLOTH_3,
                List.of(RARITY_3, Component.text("被灵力与尸气共同感染的布料").color(NamedTextColor.GRAY), Component.text("质地与法术相性都变得极佳").color(NamedTextColor.GRAY)));
        addItem(eastCloth3);

        ItemData eastWood3 = new ItemData(EAST_WOOD_3, Material.OAK_LOG,
                Component.text("妖灵木").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_WOOD_3,
                List.of(RARITY_3, Component.text("散发妖气的木材").color(NamedTextColor.GRAY), Component.text("其上的纹路闪烁着青色光晕").color(NamedTextColor.GRAY)));
        addItem(eastWood3);

        ItemData eastBone3 = new ItemData(EAST_BONE_3, Material.BONE,
                Component.text("缠须骨").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BONE_3,
                List.of(RARITY_3, Component.text("缠绕着木须的骨头").color(NamedTextColor.GRAY), Component.text("木须模拟了肌肉的结构与功能").color(NamedTextColor.GRAY)));
        addItem(eastBone3);

        ItemData eastMarrow3 = new ItemData(EAST_MARROW_3, Material.HONEYCOMB,
                Component.text("汲灵髓").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_MARROW_3,
                List.of(RARITY_3, Component.text("吸收了森林灵气的木妖骨髓").color(NamedTextColor.GRAY), Component.text("呈青色粘稠状，蕴含极高的生命能量").color(NamedTextColor.GRAY)));
        addItem(eastMarrow3);

        ItemData eastBamboo3 = new ItemData(EAST_BAMBOO_3, Material.BAMBOO,
                Component.text("韧竹节").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BAMBOO_3,
                List.of(RARITY_3, Component.text("食铁兽钟爱植物").color(NamedTextColor.GRAY), Component.text("缠绕着森林灵气，坚硬且富有韧性").color(NamedTextColor.GRAY)));
        addItem(eastBamboo3);

        ItemData eastClaw3 = new ItemData(EAST_CLAW_3, Material.RABBIT_FOOT,
                Component.text("斩棘爪").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CLAW_3,
                List.of(RARITY_3, Component.text("食铁兽尖锐的利爪").color(NamedTextColor.GRAY), Component.text("可以轻松切断金属器具").color(NamedTextColor.GRAY)));
        addItem(eastClaw3);

        ItemData eastSlip3 = new ItemData(EAST_SLIP_3, Material.PAPER,
                Component.text("织咒简").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_SLIP_3,
                List.of(RARITY_3, Component.text("写有巫师咒文的竹简").color(NamedTextColor.GRAY), Component.text("字迹仿佛在诱人阅读").color(NamedTextColor.GRAY)));
        addItem(eastSlip3);

        ItemData eastCrystal3 = new ItemData(EAST_CRYSTAL_3, Material.AMETHYST_SHARD,
                Component.text("树灵晶").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_CRYSTAL_3,
                List.of(RARITY_3, Component.text("刻有树木纹路青色的水晶").color(NamedTextColor.GRAY), Component.text("树妖巫师的法力就来自于此").color(NamedTextColor.GRAY)));
        addItem(eastCrystal3);
        //endregion

        //region 三阶南方
        ItemData southSpike3 = new ItemData(SOUTH_SPIKE_3, Material.PRISMARINE_SHARD,
                Component.text("精怪蛰刺").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_SPIKE_3,
                List.of(RARITY_3, Component.text("刺豚的尖刺").color(NamedTextColor.GRAY), Component.text("在水阵法的影响下变得更加锋利").color(NamedTextColor.GRAY)));
        addItem(southSpike3);

        ItemData southShell3 = new ItemData(SOUTH_SHELL_3, Material.SHULKER_SHELL,
                Component.text("焦炎甲壳").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_SHELL_3,
                List.of(RARITY_3, Component.text("灵虫的甲壳").color(NamedTextColor.GRAY), Component.text("在火阵法的影响下变得焦黑且更加坚硬").color(NamedTextColor.GRAY)));
        addItem(southShell3);

        ItemData southStone3 = new ItemData(SOUTH_STONE_3, Material.STONE,
                Component.text("阵基石").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_STONE_3,
                List.of(RARITY_3, Component.text("稳固千思谷阵法的灵石").color(NamedTextColor.GRAY), Component.text("具有融合水火阵法灵力的作用").color(NamedTextColor.GRAY)));
        addItem(southStone3);

        ItemData southBead3 = new ItemData(SOUTH_BEAD_3, Material.EMERALD,
                Component.text("阵灵核").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_BEAD_3,
                List.of(RARITY_3, Component.text("千思谷阵眼的核心").color(NamedTextColor.GRAY), Component.text("可以调用大量水火阵法灵力").color(NamedTextColor.GRAY)));
        addItem(southBead3);

        ItemData southFin3 = new ItemData(SOUTH_FIN_3, Material.GLOW_INK_SAC,
                Component.text("幽蓝妖鳍").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_FIN_3,
                List.of(RARITY_3, Component.text("海妖的鱼鳍").color(NamedTextColor.GRAY), Component.text("在水阵法的影响下变得更加坚韧").color(NamedTextColor.GRAY)));
        addItem(southFin3);

        ItemData southBone3 = new ItemData(SOUTH_BONE_3, Material.BONE,
                Component.text("灰烬焦骨").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_BONE_3,
                List.of(RARITY_3, Component.text("焦尸的骨头").color(NamedTextColor.GRAY), Component.text("在火阵法的影响下变得焦黑且灼热").color(NamedTextColor.GRAY)));
        addItem(southBone3);

        ItemData southFirecore3 = new ItemData(SOUTH_FIRECORE_3, Material.AMETHYST_SHARD,
                Component.text("阵法石核心[火]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_FIRECORE_3,
                List.of(RARITY_3, Component.text("千思谷火阵法的灵气核心").color(NamedTextColor.GRAY), Component.text("可以调动狂暴的火属性灵力").color(NamedTextColor.GRAY)));
        addItem(southFirecore3);

        ItemData southWatercore3 = new ItemData(SOUTH_WATERCORE_3, Material.AMETHYST_SHARD,
                Component.text("阵法石核心[水]").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_WATERCORE_3,
                List.of(RARITY_3, Component.text("千思谷水阵法的灵气核心").color(NamedTextColor.GRAY), Component.text("可以调动浩瀚的水属性灵力").color(NamedTextColor.GRAY)));
        addItem(southWatercore3);
        //endregion

        //region 三阶西方
        ItemData westGoldsand3 = new ItemData(WEST_GOLDSAND_3, Material.GOLD_NUGGET,
                Component.text("怨念金砂").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_GOLDSAND_3,
                List.of(RARITY_3, Component.text("缠绕着亡魂怨念的金矿石").color(NamedTextColor.GRAY), Component.text("其上可怖的怨气有极强的咒术潜力").color(NamedTextColor.GRAY)));
        addItem(westGoldsand3);

        ItemData westGoldmetal3 = new ItemData(WEST_GOLDMETAL_3, Material.GOLD_INGOT,
                Component.text("亡蚀虎金").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_GOLDMETAL_3,
                List.of(RARITY_3, Component.text("虎爪山脉蕴藏的极品金矿").color(NamedTextColor.GRAY), Component.text("被亡魂的尸气侵蚀，棱角变得锋利且致命").color(NamedTextColor.GRAY)));
        addItem(westGoldmetal3);

        ItemData westMarrow3 = new ItemData(WEST_MARROW_3, Material.HONEYCOMB,
                Component.text("幽魂骨髓").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_MARROW_3,
                List.of(RARITY_3, Component.text("凝聚在骨骼内部的液态死气").color(NamedTextColor.GRAY), Component.text("似乎是鬼影的能力来源").color(NamedTextColor.GRAY)));
        addItem(westMarrow3);

        ItemData westCloth3 = new ItemData(WEST_CLOTH_3, Material.FEATHER,
                Component.text("冷彻箭羽").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CLOTH_3,
                List.of(RARITY_3, Component.text("鬼影所使用箭矢的阴寒箭羽").color(NamedTextColor.GRAY), Component.text("散发着可以撕裂山石的寒气").color(NamedTextColor.GRAY)));
        addItem(westCloth3);

        ItemData westLine3 = new ItemData(WEST_LINE_3, Material.STRING,
                Component.text("冤魂丝线").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_LINE_3,
                List.of(RARITY_3, Component.text("毒蛛所吐的紫黑色丝线").color(NamedTextColor.GRAY), Component.text("似乎是其吞噬的冤魂编织而成").color(NamedTextColor.GRAY)));
        addItem(westLine3);

        ItemData westTooth3 = new ItemData(WEST_TOOTH_3, Material.ARMADILLO_SCUTE,
                Component.text("噬魂鳌牙").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_TOOTH_3,
                List.of(RARITY_3, Component.text("毒蛛可怖的怪牙").color(NamedTextColor.GRAY), Component.text("可以透过肉体毒伤灵魂").color(NamedTextColor.GRAY)));
        addItem(westTooth3);

        ItemData westBone3 = new ItemData(WEST_BONE_3, Material.BONE,
                Component.text("凝怨焦骨").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_BONE_3,
                List.of(RARITY_3, Component.text("焦骨战士燃烧着阴火臂骨").color(NamedTextColor.GRAY), Component.text("缠绕的死气令其冰冷阴森").color(NamedTextColor.GRAY)));
        addItem(westBone3);

        ItemData westFinger3 = new ItemData(WEST_FINGER_3, Material.BONE,
                Component.text("摄魂指节").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_FINGER_3,
                List.of(RARITY_3, Component.text("焦骨战士尖锐的指骨").color(NamedTextColor.GRAY), Component.text("其摄取的魂魄将会转化为缠身的死气").color(NamedTextColor.GRAY)));
        addItem(westFinger3);
        //endregion

        //region 三阶北方
        ItemData northBlade3 = new ItemData(NORTH_BLADE_3, Material.IRON_INGOT,
                Component.text("斩魂剑刃").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_BLADE_3,
                List.of(RARITY_3, Component.text("金乌教徒所冶炼的金属剑刃").color(NamedTextColor.GRAY), Component.text("其上的金色纹路能斩断魂魄").color(NamedTextColor.GRAY)));
        addItem(northBlade3);

        ItemData northBone3 = new ItemData(NORTH_BONE_3, Material.BONE,
                Component.text("金纹朽骨").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_BONE_3,
                List.of(RARITY_3, Component.text("金乌教徒刻有金色纹路的骨骼").color(NamedTextColor.GRAY), Component.text("教徒可凭借它借用金乌妖力").color(NamedTextColor.GRAY)));
        addItem(northBone3);

        ItemData northCloth3 = new ItemData(NORTH_CLOTH_3, Material.FEATHER,
                Component.text("妖翎箭羽").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_CLOTH_3,
                List.of(RARITY_3, Component.text("金乌教徒所使用的燃烧箭羽").color(NamedTextColor.GRAY), Component.text("能听到轻微的爆破声，仿佛空气都被引燃").color(NamedTextColor.GRAY)));
        addItem(northCloth3);

        ItemData northLine3 = new ItemData(NORTH_LINE_3, Material.STRING,
                Component.text("妖魂弓弦").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_LINE_3,
                List.of(RARITY_3, Component.text("金乌教徒所编织的烈焰弓弦").color(NamedTextColor.GRAY), Component.text("散发着耀眼的光辉，仿佛在灼烧灵魂").color(NamedTextColor.GRAY)));
        addItem(northLine3);

        ItemData northLancer3 = new ItemData(NORTH_LANCER_3, Material.IRON_INGOT,
                Component.text("穿魂枪尖").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_LANCER_3,
                List.of(RARITY_3, Component.text("金乌教徒所冶炼的金属枪刃").color(NamedTextColor.GRAY), Component.text("其上的金色寒芒能穿透灵魂").color(NamedTextColor.GRAY)));
        addItem(northLancer3);

        ItemData northBead3 = new ItemData(NORTH_BEAD_3, Material.EMERALD,
                Component.text("金焰纹章").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_BEAD_3,
                List.of(RARITY_3, Component.text("金乌教徒镶嵌在武器上的金色徽章").color(NamedTextColor.GRAY), Component.text("燃烧着如同日晖的灵魂火焰").color(NamedTextColor.GRAY)));
        addItem(northBead3);

        ItemData northGoldfeather3 = new ItemData(NORTH_GOLDFEATHER_3, Material.FEATHER,
                Component.text("妖鸟金翎").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_GOLDFEATHER_3,
                List.of(RARITY_3, Component.text("金乌成长时脱落的翎羽").color(NamedTextColor.GRAY), Component.text("每一根羽毛都燃烧着不灭的烈焰").color(NamedTextColor.GRAY)));
        addItem(northGoldfeather3);

        ItemData northGoldeye3 = new ItemData(NORTH_GOLDEYE_3, Material.HEART_OF_THE_SEA,
                Component.text("烈日金瞳").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_GOLDEYE_3,
                List.of(RARITY_3, Component.text("凝聚着金乌烈焰的眼瞳").color(NamedTextColor.GRAY), Component.text("磅礴的妖力仿佛来自高悬的烈日").color(NamedTextColor.GRAY)));
        addItem(northGoldeye3);
        //endregion

        //region 四阶核心
        ItemData weaponmetal4 = new ItemData(WEAPONMETAL4, Material.RAW_IRON,
                Component.text("玄铁锭").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEAPONMETAL4,
                List.of(RARITY_4, Component.text("清冷锋利的玄色金属").color(NamedTextColor.GRAY), Component.text("可塑性强，适合制作四阶武器").color(NamedTextColor.GRAY)));
        addItem(weaponmetal4);

        ItemData armorcore4 = new ItemData(ARMORCORE4, Material.RAW_GOLD,
                Component.text("四阶装备原核").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + ARMORCORE4,
                List.of(RARITY_4, Component.text("稀有的装备核心").color(NamedTextColor.GRAY), Component.text("可以制作四阶防具").color(NamedTextColor.GRAY)));
        addItem(armorcore4);

        ItemData eastWeaponforge4 = new ItemData(EAST_WEAPONFORGE_4, Material.DIAMOND,
                Component.text("四阶武器锻核[森林]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.4",
                List.of(RARITY_4, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶武器").color(NamedTextColor.GRAY)));
        addItem(eastWeaponforge4);

        ItemData eastArmorforge4 = new ItemData(EAST_ARMORFORGE_4, Material.DIAMOND,
                Component.text("四阶防具锻核[森林]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.4",
                List.of(RARITY_4, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶防具").color(NamedTextColor.GRAY)));
        addItem(eastArmorforge4);

        ItemData eastMagicforge4 = new ItemData(EAST_MAGICFORGE_4, Material.DIAMOND,
                Component.text("四阶法宝锻核[森林]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.4",
                List.of(RARITY_4, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶法宝").color(NamedTextColor.GRAY)));
        addItem(eastMagicforge4);

        ItemData southWeaponforge4 = new ItemData(SOUTH_WEAPONFORGE_4, Material.DIAMOND,
                Component.text("四阶武器锻核[沙漠]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.4",
                List.of(RARITY_4, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶武器").color(NamedTextColor.GRAY)));
        addItem(southWeaponforge4);

        ItemData southArmorforge4 = new ItemData(SOUTH_ARMORFORGE_4, Material.DIAMOND,
                Component.text("四阶防具锻核[沙漠]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.4",
                List.of(RARITY_4, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶防具").color(NamedTextColor.GRAY)));
        addItem(southArmorforge4);

        ItemData southMagicforge4 = new ItemData(SOUTH_MAGICFORGE_4, Material.DIAMOND,
                Component.text("四阶法宝锻核[沙漠]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.4",
                List.of(RARITY_4, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶法宝").color(NamedTextColor.GRAY)));
        addItem(southMagicforge4);

        ItemData westWeaponforge4 = new ItemData(WEST_WEAPONFORGE_4, Material.DIAMOND,
                Component.text("四阶武器锻核[雪山]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.4",
                List.of(RARITY_4, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶武器").color(NamedTextColor.GRAY)));
        addItem(westWeaponforge4);

        ItemData westArmorforge4 = new ItemData(WEST_ARMORFORGE_4, Material.DIAMOND,
                Component.text("四阶防具锻核[雪山]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.4",
                List.of(RARITY_4, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶防具").color(NamedTextColor.GRAY)));
        addItem(westArmorforge4);

        ItemData westMagicforge4 = new ItemData(WEST_MAGICFORGE_4, Material.DIAMOND,
                Component.text("四阶法宝锻核[雪山]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.4",
                List.of(RARITY_4, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶法宝").color(NamedTextColor.GRAY)));
        addItem(westMagicforge4);

        ItemData northWeaponforge4 = new ItemData(NORTH_WEAPONFORGE_4, Material.DIAMOND,
                Component.text("四阶武器锻核[湖泊]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.4",
                List.of(RARITY_4, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶武器").color(NamedTextColor.GRAY)));
        addItem(northWeaponforge4);

        ItemData northArmorforge4 = new ItemData(NORTH_ARMORFORGE_4, Material.DIAMOND,
                Component.text("四阶防具锻核[湖泊]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.4",
                List.of(RARITY_4, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶防具").color(NamedTextColor.GRAY)));
        addItem(northArmorforge4);

        ItemData northMagicforge4 = new ItemData(NORTH_MAGICFORGE_4, Material.DIAMOND,
                Component.text("四阶法宝锻核[湖泊]").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.4",
                List.of(RARITY_4, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造四阶法宝").color(NamedTextColor.GRAY)));
        addItem(northMagicforge4);
        //endregion

        //region 四阶东方
        ItemData eastBugtooth4 = new ItemData(EAST_BUGTOOTH_4, Material.ARMADILLO_SCUTE,
                Component.text("噬生尖牙").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BUGTOOTH_4,
                List.of(RARITY_4, Component.text("吞噬灵植生命力的尖牙").color(NamedTextColor.GRAY), Component.text("似乎被其划破就会失去所有生命力").color(NamedTextColor.GRAY)));
        addItem(eastBugtooth4);

        ItemData eastExtract4 = new ItemData(EAST_EXTRACT_4, Material.DRAGON_BREATH,
                Component.text("狂萃精华").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_EXTRACT_4,
                List.of(RARITY_4, Component.text("啮根虫存储灵力的腺体中所提取的精华液").color(NamedTextColor.GRAY), Component.text("蕴含极高浓度的生命灵力，能为金属赋予活力").color(NamedTextColor.GRAY)));
        addItem(eastExtract4);

        ItemData eastBead4 = new ItemData(EAST_BEAD_4, Material.EMERALD,
                Component.text("青纹内丹").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BEAD_4,
                List.of(RARITY_4, Component.text("吸收了大量生命灵气的内丹").color(NamedTextColor.GRAY), Component.text("其上青色的纹路可以调动周围的草木灵力").color(NamedTextColor.GRAY)));
        addItem(eastBead4);

        ItemData eastBone4 = new ItemData(EAST_BONE_4, Material.BONE,
                Component.text("复生根骨").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_BONE_4,
                List.of(RARITY_4, Component.text("灵尸虬结一体的筋脉与骨骼").color(NamedTextColor.GRAY), Component.text("磅礴的生命灵力赋予其再生的能力").color(NamedTextColor.GRAY)));
        addItem(eastBone4);

        ItemData eastSpidertooth4 = new ItemData(EAST_SPIDERTOOTH_4, Material.ARMADILLO_SCUTE,
                Component.text("剧毒虬齿").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_SPIDERTOOTH_4,
                List.of(RARITY_4, Component.text("毒蛛巨大而扭曲的牙齿").color(NamedTextColor.GRAY), Component.text("其分泌的毒液会腐蚀生命灵力").color(NamedTextColor.GRAY)));
        addItem(eastSpidertooth4);

        ItemData eastSpike4 = new ItemData(EAST_SPIKE_4, Material.PRISMARINE_SHARD,
                Component.text("裂骨棘刺").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_SPIKE_4,
                List.of(RARITY_4, Component.text("毒蛛腿上锋利的棘刺").color(NamedTextColor.GRAY), Component.text("其伸出的无数倒刺能轻松刺破骨骼").color(NamedTextColor.GRAY)));
        addItem(eastSpike4);

        ItemData eastEye4 = new ItemData(EAST_EYE_4, Material.HEART_OF_THE_SEA,
                Component.text("森灵复眼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_EYE_4,
                List.of(RARITY_4, Component.text("蜘蛛女王的复眼").color(NamedTextColor.GRAY), Component.text("透过它能够看穿生命灵力的脉络").color(NamedTextColor.GRAY)));
        addItem(eastEye4);

        ItemData eastShell4 = new ItemData(EAST_SHELL_4, Material.SHULKER_SHELL,
                Component.text("蛛王坚壳").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_SHELL_4,
                List.of(RARITY_4, Component.text("蜘蛛女王的坚硬的外甲壳").color(NamedTextColor.GRAY), Component.text("内部分布着复杂的灵力脉络，能够抵御并吸收强大的攻击").color(NamedTextColor.GRAY)));
        addItem(eastShell4);

        ItemData eastAmber4 = new ItemData(EAST_AMBER_4, Material.AMETHYST_SHARD,
                Component.text("龙纹树晶").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_AMBER_4,
                List.of(RARITY_4, Component.text("千年灵树中埋藏的水晶").color(NamedTextColor.GRAY), Component.text("内有青龙纹路，蕴含强大的法力").color(NamedTextColor.GRAY)));
        addItem(eastAmber4);

        ItemData eastVine4 = new ItemData(EAST_VINE_4, Material.FERN,
                Component.text("天青藤").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + EAST_VINE_4,
                List.of(RARITY_4, Component.text("千年灵树上缠绕的藤蔓").color(NamedTextColor.GRAY), Component.text("受其千年灵力的影响，极为坚韧").color(NamedTextColor.GRAY)));
        addItem(eastVine4);
        //endregion

        //region 四阶南方
        ItemData southFire4 = new ItemData(SOUTH_FIRE_4, Material.BLAZE_POWDER,
                Component.text("燃心魔火").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_FIRE_4,
                List.of(RARITY_4, Component.text("焰魔的生命之火").color(NamedTextColor.GRAY), Component.text("常年吸收火山灵力，可燃烧千年").color(NamedTextColor.GRAY)));
        addItem(southFire4);

        ItemData southRod4 = new ItemData(SOUTH_ROD_4, Material.BLAZE_ROD,
                Component.text("燃躯火臂").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_ROD_4,
                List.of(RARITY_4, Component.text("环绕焰魔周身的火柱").color(NamedTextColor.GRAY), Component.text("受魔火影响，其上燃烧的火焰难以熄灭").color(NamedTextColor.GRAY)));
        addItem(southRod4);

        ItemData southArm4 = new ItemData(SOUTH_ARM_4, Material.GOLD_INGOT,
                Component.text("炎金臂铠").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_ARM_4,
                List.of(RARITY_4, Component.text("兽人右臂的特制铠甲").color(NamedTextColor.GRAY), Component.text("能够驾驭狂暴的火山灵力以强化攻击").color(NamedTextColor.GRAY)));
        addItem(southArm4);

        ItemData southAxe4 = new ItemData(SOUTH_AXE_4, Material.GOLD_INGOT,
                Component.text("熔金斧刃").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_AXE_4,
                List.of(RARITY_4, Component.text("兽人巨斧的沉重斧刃").color(NamedTextColor.GRAY), Component.text("其逸散的狂暴气息让人望而生畏").color(NamedTextColor.GRAY)));
        addItem(southAxe4);

        ItemData southCore4 = new ItemData(SOUTH_CORE_4, Material.AMETHYST_SHARD,
                Component.text("燃烬魔核").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_CORE_4,
                List.of(RARITY_4, Component.text("魔像的力量核心").color(NamedTextColor.GRAY), Component.text("似乎是焚烧为灰烬的沙石糅合而成的").color(NamedTextColor.GRAY)));
        addItem(southCore4);

        ItemData southBody4 = new ItemData(SOUTH_BODY_4, Material.COPPER_GOLEM_STATUE,
                Component.text("灰烬躯壳").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_BODY_4,
                List.of(RARITY_4, Component.text("魔像失去力量后留下的躯壳").color(NamedTextColor.GRAY), Component.text("依然保留着强大的防御力").color(NamedTextColor.GRAY)));
        addItem(southBody4);

        ItemData southDevilmud4 = new ItemData(SOUTH_DEVILMUD_4, Material.SLIME_BLOCK,
                Component.text("太岁土").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_DEVILMUD_4,
                List.of(RARITY_4, Component.text("剥离下的凶神太岁身躯").color(NamedTextColor.GRAY), Component.text("即使脱离了本体，也依然像是活物").color(NamedTextColor.GRAY)));
        addItem(southDevilmud4);

        ItemData southDevilshell4 = new ItemData(SOUTH_DEVILSHELL_4, Material.SHULKER_SHELL,
                Component.text("凶神壳").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_DEVILSHELL_4,
                List.of(RARITY_4, Component.text("凶神太岁的外层坚壳").color(NamedTextColor.GRAY), Component.text("坚硬无比，可以轻易凿穿山体岩石").color(NamedTextColor.GRAY)));
        addItem(southDevilshell4);

        ItemData southDevilbead4 = new ItemData(SOUTH_DEVILBEAD_4, Material.EMERALD,
                Component.text("古尸妖丹").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_DEVILBEAD_4,
                List.of(RARITY_4, Component.text("旱魃古尸保存千年的尸丹").color(NamedTextColor.GRAY), Component.text("压制着躁动的死气与烈焰，似乎要将大漠焚尽").color(NamedTextColor.GRAY)));
        addItem(southDevilbead4);

        ItemData southDevilnail4 = new ItemData(SOUTH_DEVILNAIL_4, Material.NETHERITE_INGOT,
                Component.text("邪祟骨钉").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + SOUTH_DEVILNAIL_4,
                List.of(RARITY_4, Component.text("旱魃古尸头颅上的骨钉").color(NamedTextColor.GRAY), Component.text("吸收了千年尸气，仿佛要将大漠吞食").color(NamedTextColor.GRAY)));
        addItem(southDevilnail4);
        //endregion

        //region 四阶西方
        ItemData westClaw4 = new ItemData(WEST_CLAW_4, Material.RABBIT_FOOT,
                Component.text("坚冰利爪").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CLAW_4,
                List.of(RARITY_4, Component.text("覆盖着坚冰的魔熊爪").color(NamedTextColor.GRAY), Component.text("妖力与冰晶融合，拥有极强的破坏力").color(NamedTextColor.GRAY)));
        addItem(westClaw4);

        ItemData westCloth4 = new ItemData(WEST_CLOTH_4, Material.LEATHER,
                Component.text("白熊毛皮").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CLOTH_4,
                List.of(RARITY_4, Component.text("魔熊的坚韧毛皮").color(NamedTextColor.GRAY), Component.text("毛发上凝结着冰晶，昭示着其强大的反击能力").color(NamedTextColor.GRAY)));
        addItem(westCloth4);

        ItemData westIron4 = new ItemData(WEST_IRON_4, Material.FLINT,
                Component.text("寒芒晶铁").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_IRON_4,
                List.of(RARITY_4, Component.text("凝固着六棱冰晶的金属").color(NamedTextColor.GRAY), Component.text("能够刺穿骨髓，冻结灵魂").color(NamedTextColor.GRAY)));
        addItem(westIron4);

        ItemData westBone4 = new ItemData(WEST_BONE_4, Material.BONE,
                Component.text("严寒冻骨").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_BONE_4,
                List.of(RARITY_4, Component.text("寒天射手覆盖冰晶的骨骼").color(NamedTextColor.GRAY), Component.text("在极地严寒的影响下，变得极为坚固冰寒").color(NamedTextColor.GRAY)));
        addItem(westBone4);

        ItemData westEye4 = new ItemData(WEST_EYE_4, Material.HEART_OF_THE_SEA,
                Component.text("惑心魔眼").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_EYE_4,
                List.of(RARITY_4, Component.text("鬼影用于蛊惑灵魂的魔眼").color(NamedTextColor.GRAY), Component.text("仿佛看着它就会陷入严寒与恐惧").color(NamedTextColor.GRAY)));
        addItem(westEye4);

        ItemData westBody4 = new ItemData(WEST_BODY_4, Material.COPPER_GOLEM_STATUE,
                Component.text("幽影残躯").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_BODY_4,
                List.of(RARITY_4, Component.text("鬼魂消散后留下的残躯").color(NamedTextColor.GRAY), Component.text("难以想象如此瘦弱的身躯能爆发出令人胆寒的速度").color(NamedTextColor.GRAY)));
        addItem(westBody4);

        ItemData westCrystal4 = new ItemData(WEST_CRYSTAL_4, Material.AMETHYST_SHARD,
                Component.text("埋雪魂晶").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CRYSTAL_4,
                List.of(RARITY_4, Component.text("幽魂的灵魂结晶").color(NamedTextColor.GRAY), Component.text("拥有能将空气冻结的可怕魔力").color(NamedTextColor.GRAY)));
        addItem(westCrystal4);

        ItemData westCore4 = new ItemData(WEST_CORE_4, Material.AMETHYST_SHARD,
                Component.text("聚风魂核").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_CORE_4,
                List.of(RARITY_4, Component.text("幽魂的力量核心").color(NamedTextColor.GRAY), Component.text("拥有引动聚风的可怕魔力").color(NamedTextColor.GRAY)));
        addItem(westCore4);

        ItemData westWing4 = new ItemData(WEST_WING_4, Material.PHANTOM_MEMBRANE,
                Component.text("幽怨幻羽").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_WING_4,
                List.of(RARITY_4, Component.text("幽翼翅膀上最璀璨的羽毛").color(NamedTextColor.GRAY), Component.text("能够扭曲光线，隐匿自身").color(NamedTextColor.GRAY)));
        addItem(westWing4);

        ItemData westSpike4 = new ItemData(WEST_SPIKE_4, Material.PRISMARINE_SHARD,
                Component.text("寒晶尾刺").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEST_SPIKE_4,
                List.of(RARITY_4, Component.text("幽翼尾骨上的尖刺").color(NamedTextColor.GRAY), Component.text("凝聚了雪山的极寒灵力，能够冻结刺中的敌人").color(NamedTextColor.GRAY)));
        addItem(westSpike4);
        //endregion

        //region 四阶北方
        ItemData northCloth4 = new ItemData(NORTH_CLOTH_4, Material.LEATHER,
                Component.text("缠怨衣冢").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_CLOTH_4,
                List.of(RARITY_4, Component.text("幽魂身穿的衣物").color(NamedTextColor.GRAY), Component.text("似乎是哪座墓葬中的衣冠冢").color(NamedTextColor.GRAY)));
        addItem(northCloth4);

        ItemData northFire4 = new ItemData(NORTH_FIRE_4, Material.EMERALD,
                Component.text("怨念魂火").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_FIRE_4,
                List.of(RARITY_4, Component.text("融合了大量幽魂的阴火").color(NamedTextColor.GRAY), Component.text("靠近时可以听到若有若无的嚎哭").color(NamedTextColor.GRAY)));
        addItem(northFire4);

        ItemData northBone4 = new ItemData(NORTH_BONE_4, Material.BONE,
                Component.text("阴冷幽骨").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_BONE_4,
                List.of(RARITY_4, Component.text("墓园里的阴森白骨").color(NamedTextColor.GRAY), Component.text("鬼影似乎钟爱把它当作凶器").color(NamedTextColor.GRAY)));
        addItem(northBone4);

        ItemData northClaw4 = new ItemData(NORTH_CLAW_4, Material.RABBIT_FOOT,
                Component.text("阴森鬼爪").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_CLAW_4,
                List.of(RARITY_4, Component.text("鬼影的恐怖手爪").color(NamedTextColor.GRAY), Component.text("看一眼就会产生头颅被抓握的恐怖幻觉").color(NamedTextColor.GRAY)));
        addItem(northClaw4);

        ItemData northMud4 = new ItemData(NORTH_MUD_4, Material.SLIME_BLOCK,
                Component.text("腐生息壤").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_MUD_4,
                List.of(RARITY_4, Component.text("扭曲增殖的活体土壤").color(NamedTextColor.GRAY), Component.text("一旦触碰就会被缠绕寄生").color(NamedTextColor.GRAY)));
        addItem(northMud4);

        ItemData northDirt4 = new ItemData(NORTH_DIRT_4, Material.DIRT,
                Component.text("浸血尘土").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_DIRT_4,
                List.of(RARITY_4, Component.text("血色可怖的土壤").color(NamedTextColor.GRAY), Component.text("似乎是在主动吸收墓葬中尸体的血液").color(NamedTextColor.GRAY)));
        addItem(northDirt4);

        ItemData northFeeler4 = new ItemData(NORTH_FEELER_4, Material.SCULK_VEIN,
                Component.text("煞魂触角").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_FEELER_4,
                List.of(RARITY_4, Component.text("丧魔头颅生长的触角").color(NamedTextColor.GRAY), Component.text("似乎可以感知到灵魂的波动").color(NamedTextColor.GRAY)));
        addItem(northFeeler4);

        ItemData northHeart4 = new ItemData(NORTH_HEART_4, Material.ECHO_SHARD,
                Component.text("幽冥心骸").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_HEART_4,
                List.of(RARITY_4, Component.text("丧魔被骸骨包裹的心脏").color(NamedTextColor.GRAY), Component.text("即使脱离身躯也依然在跳动").color(NamedTextColor.GRAY)));
        addItem(northHeart4);

        ItemData northTooth4 = new ItemData(NORTH_TOOTH_4, Material.ECHO_SHARD,
                Component.text("汲魂獠牙").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_TOOTH_4,
                List.of(RARITY_4, Component.text("鬼彘尖锐的獠牙").color(NamedTextColor.GRAY), Component.text("被其贯穿的生物将被汲取灵魂").color(NamedTextColor.GRAY)));
        addItem(northTooth4);

        ItemData northSoulbone4 = new ItemData(NORTH_SOULBONE_4, Material.BONE,
                Component.text("囚魂骇骨").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + NORTH_SOULBONE_4,
                List.of(RARITY_4, Component.text("鬼彘暴露在体外的肋骨").color(NamedTextColor.GRAY), Component.text("獠牙所汲取的灵魂将被囚禁于此").color(NamedTextColor.GRAY)));
        addItem(northSoulbone4);
        //endregion

        //region 五阶核心
        ItemData weaponmetal5 = new ItemData(WEAPONMETAL5, Material.RAW_IRON,
                Component.text("灵玉简").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + WEAPONMETAL5,
                List.of(RARITY_5, Component.text("晶莹剔透的玉质书简").color(NamedTextColor.GRAY), Component.text("灵力深厚，适合制作五阶武器").color(NamedTextColor.GRAY)));
        addItem(weaponmetal5);

        ItemData armorcore5 = new ItemData(ARMORCORE5, Material.RAW_GOLD,
                Component.text("五阶装备原核").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                MATERIALS_MODEL + ARMORCORE5,
                List.of(RARITY_5, Component.text("稀有的装备核心").color(NamedTextColor.GRAY), Component.text("可以制作五阶防具").color(NamedTextColor.GRAY)));
        addItem(armorcore5);

        ItemData eastWeaponforge5 = new ItemData(EAST_WEAPONFORGE_5, Material.DIAMOND,
                Component.text("五阶武器锻核[森林]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.5",
                List.of(RARITY_5, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶武器").color(NamedTextColor.GRAY)));
        addItem(eastWeaponforge5);

        ItemData eastArmorforge5 = new ItemData(EAST_ARMORFORGE_5, Material.DIAMOND,
                Component.text("五阶防具锻核[森林]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.5",
                List.of(RARITY_5, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶防具").color(NamedTextColor.GRAY)));
        addItem(eastArmorforge5);

        ItemData eastMagicforge5 = new ItemData(EAST_MAGICFORGE_5, Material.DIAMOND,
                Component.text("五阶法宝锻核[森林]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.5",
                List.of(RARITY_5, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶法宝").color(NamedTextColor.GRAY)));
        addItem(eastMagicforge5);

        ItemData southWeaponforge5 = new ItemData(SOUTH_WEAPONFORGE_5, Material.DIAMOND,
                Component.text("五阶武器锻核[沙漠]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.5",
                List.of(RARITY_5, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶武器").color(NamedTextColor.GRAY)));
        addItem(southWeaponforge5);

        ItemData southArmorforge5 = new ItemData(SOUTH_ARMORFORGE_5, Material.DIAMOND,
                Component.text("五阶防具锻核[沙漠]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.5",
                List.of(RARITY_5, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶防具").color(NamedTextColor.GRAY)));
        addItem(southArmorforge5);

        ItemData southMagicforge5 = new ItemData(SOUTH_MAGICFORGE_5, Material.DIAMOND,
                Component.text("五阶法宝锻核[沙漠]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.5",
                List.of(RARITY_5, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶法宝").color(NamedTextColor.GRAY)));
        addItem(southMagicforge5);

        ItemData westWeaponforge5 = new ItemData(WEST_WEAPONFORGE_5, Material.DIAMOND,
                Component.text("五阶武器锻核[雪山]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.5",
                List.of(RARITY_5, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶武器").color(NamedTextColor.GRAY)));
        addItem(westWeaponforge5);

        ItemData westArmorforge5 = new ItemData(WEST_ARMORFORGE_5, Material.DIAMOND,
                Component.text("五阶防具锻核[雪山]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.5",
                List.of(RARITY_5, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶防具").color(NamedTextColor.GRAY)));
        addItem(westArmorforge5);

        ItemData westMagicforge5 = new ItemData(WEST_MAGICFORGE_5, Material.DIAMOND,
                Component.text("五阶法宝锻核[雪山]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.5",
                List.of(RARITY_5, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶法宝").color(NamedTextColor.GRAY)));
        addItem(westMagicforge5);

        ItemData northWeaponforge5 = new ItemData(NORTH_WEAPONFORGE_5, Material.DIAMOND,
                Component.text("五阶武器锻核[湖泊]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/weaponforge.5",
                List.of(RARITY_5, Component.text("经过加工的武器锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶武器").color(NamedTextColor.GRAY)));
        addItem(northWeaponforge5);

        ItemData northArmorforge5 = new ItemData(NORTH_ARMORFORGE_5, Material.DIAMOND,
                Component.text("五阶防具锻核[湖泊]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/armorforge.5",
                List.of(RARITY_5, Component.text("经过加工的防具锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶防具").color(NamedTextColor.GRAY)));
        addItem(northArmorforge5);

        ItemData northMagicforge5 = new ItemData(NORTH_MAGICFORGE_5, Material.DIAMOND,
                Component.text("五阶法宝锻核[湖泊]").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                "materials/magicforge.5",
                List.of(RARITY_5, Component.text("经过加工的法宝锻核").color(NamedTextColor.GRAY), Component.text("适合用作锻造五阶法宝").color(NamedTextColor.GRAY)));
        addItem(northMagicforge5);
        //endregion

        ItemData rebornStone = new ItemData(REBORN_STONE, Material.NETHER_STAR,
                Component.text("重生石").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC,false),
                null, new ArrayList<>());
        addItem(rebornStone);

        //region 钱币
        ItemData money0 = new ItemData(MONEY0, Material.RESIN_CLUMP,
                Component.text("铜钱").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + MONEY0,
                List.of());
        addItem(money0);

        ItemData money1 = new ItemData(MONEY1, Material.RESIN_CLUMP,
                Component.text("金元宝").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + MONEY1,
                List.of());
        addItem(money1);

        ItemData money2 = new ItemData(MONEY2, Material.RESIN_CLUMP,
                Component.text("银票").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + MONEY2,
                List.of());
        addItem(money2);

        ItemData money3 = new ItemData(MONEY3, Material.RESIN_CLUMP,
                Component.text("金券").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + MONEY3,
                List.of());
        addItem(money3);

        ItemData money4 = new ItemData(MONEY4, Material.RESIN_CLUMP,
                Component.text("宝钞").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + MONEY4,
                List.of());
        addItem(money4);
        //endregion

        //region 普通元素
        ItemData elementMetal = new ItemData(ELEMENT_METAL, Material.CLAY_BALL,
                Component.text("金元素").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ELEMENT_METAL,
                List.of());
        addItem(elementMetal);

        ItemData elementWood = new ItemData(ELEMENT_WOOD, Material.CLAY_BALL,
                Component.text("木元素").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ELEMENT_WOOD,
                List.of());
        addItem(elementWood);

        ItemData elementWater = new ItemData(ELEMENT_WATER, Material.CLAY_BALL,
                Component.text("水元素").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ELEMENT_WATER,
                List.of());
        addItem(elementWater);

        ItemData elementLava = new ItemData(ELEMENT_LAVA, Material.CLAY_BALL,
                Component.text("火元素").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ELEMENT_LAVA,
                List.of());
        addItem(elementLava);

        ItemData elementEarth = new ItemData(ELEMENT_EARTH, Material.CLAY_BALL,
                Component.text("土元素").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ELEMENT_EARTH,
                List.of());
        addItem(elementEarth);
        //endregion

        //region 精炼元素
        ItemData refinedMetal = new ItemData(REFINED_METAL, Material.CLAY_BALL,
                Component.text("精炼金元素").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + REFINED_METAL,
                List.of());
        addItem(refinedMetal);

        ItemData refinedWood = new ItemData(REFINED_WOOD, Material.CLAY_BALL,
                Component.text("精炼木元素").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + REFINED_WOOD,
                List.of());
        addItem(refinedWood);

        ItemData refinedWater = new ItemData(REFINED_WATER, Material.CLAY_BALL,
                Component.text("精炼水元素").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + REFINED_WATER,
                List.of());
        addItem(refinedWater);

        ItemData refinedLava = new ItemData(REFINED_LAVA, Material.CLAY_BALL,
                Component.text("精炼火元素").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + REFINED_LAVA,
                List.of());
        addItem(refinedLava);

        ItemData refinedEarth = new ItemData(REFINED_EARTH, Material.CLAY_BALL,
                Component.text("精炼土元素").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + REFINED_EARTH,
                List.of());
        addItem(refinedEarth);
        //endregion

        //region 浓缩元素
        ItemData advancedMetal = new ItemData(ADVANCED_METAL, Material.CLAY_BALL,
                Component.text("浓缩金元素").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ADVANCED_METAL,
                List.of());
        addItem(advancedMetal);

        ItemData advancedWood = new ItemData(ADVANCED_WOOD, Material.CLAY_BALL,
                Component.text("浓缩木元素").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ADVANCED_WOOD,
                List.of());
        addItem(advancedWood);

        ItemData advancedWater = new ItemData(ADVANCED_WATER, Material.CLAY_BALL,
                Component.text("浓缩水元素").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ADVANCED_WATER,
                List.of());
        addItem(advancedWater);

        ItemData advancedLava = new ItemData(ADVANCED_LAVA, Material.CLAY_BALL,
                Component.text("浓缩火元素").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ADVANCED_LAVA,
                List.of());
        addItem(advancedLava);

        ItemData advancedEarth = new ItemData(ADVANCED_EARTH, Material.CLAY_BALL,
                Component.text("浓缩土元素").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + ADVANCED_EARTH,
                List.of());
        addItem(advancedEarth);
        //endregion

        //region 各地货物
        ItemData westGoods2 = new ItemData(WEST_GOODS_2, Material.BRICK,
                Component.text("魔化妖丹").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + WEST_GOODS_2,
                List.of());
        addItem(westGoods2);

        ItemData westGoods3 = new ItemData(WEST_GOODS_3, Material.BRICK,
                Component.text("蕴灵树皮").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + WEST_GOODS_3,
                List.of());
        addItem(westGoods3);

        ItemData westGoods4 = new ItemData(WEST_GOODS_4, Material.BRICK,
                Component.text("山神庙的贡品").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + WEST_GOODS_4,
                List.of());
        addItem(westGoods4);

        ItemData southGoods2 = new ItemData(SOUTH_GOODS_2, Material.BRICK,
                Component.text("王员外的货物").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + SOUTH_GOODS_2,
                List.of());
        addItem(southGoods2);

        ItemData southGoods3 = new ItemData(SOUTH_GOODS_3, Material.BRICK,
                Component.text("阵法石碎片").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + SOUTH_GOODS_3,
                List.of());
        addItem(southGoods3);

        ItemData southGoods4 = new ItemData(SOUTH_GOODS_4, Material.BRICK,
                Component.text("篆元上仙的文献").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + SOUTH_GOODS_4,
                List.of());
        addItem(southGoods4);

        ItemData eastGoods2 = new ItemData(EAST_GOODS_2, Material.BRICK,
                Component.text("陈大夫的草药束").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + EAST_GOODS_2,
                List.of());
        addItem(eastGoods2);

        ItemData eastGoods3 = new ItemData(EAST_GOODS_3, Material.BRICK,
                Component.text("虎爪碎金").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + EAST_GOODS_3,
                List.of());
        addItem(eastGoods3);

        ItemData eastGoods4 = new ItemData(EAST_GOODS_4, Material.BRICK,
                Component.text("虎灵冰晶").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + EAST_GOODS_4,
                List.of());
        addItem(eastGoods4);

        ItemData northGoods2 = new ItemData(NORTH_GOODS_2, Material.BRICK,
                Component.text("洄游祭的祭品").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + NORTH_GOODS_2,
                List.of());
        addItem(northGoods2);

        ItemData northGoods3 = new ItemData(NORTH_GOODS_3, Material.BRICK,
                Component.text("金乌落羽").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + NORTH_GOODS_3,
                List.of());
        addItem(northGoods3);

        ItemData northGoods4 = new ItemData(NORTH_GOODS_4, Material.BRICK,
                Component.text("缠怨布").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false),
                OTHERS_MODEL + NORTH_GOODS_4,
                List.of());
        addItem(northGoods4);
        //endregion

    }
}
