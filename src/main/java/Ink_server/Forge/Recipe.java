package Ink_server.Forge;

import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import Ink_server.StaticDatas.WeaponLib.WeaponRegistry;

import java.util.HashMap;
import java.util.Map;

public class Recipe {

    public static void init(){
        InkSound.getInstance().getLogger().info("武器配方已加载!");
    }

    private Recipe(){}

    private static final Map<TempRecipeList, String> recipes = new HashMap<>();
    //武器2
    static {
        //东2
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_2, null, null,
                        ItemRegistry.EAST_CRYSTALCLAW_2, ItemRegistry.EAST_BONE_2, ItemRegistry.ELEMENT_WOOD,
                        WeaponRegistry.SWORD1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.SWORD22);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_2, null, null,
                        ItemRegistry.EAST_CRYSTALCLAW_2, ItemRegistry.EAST_BONE_2, ItemRegistry.ELEMENT_WOOD,
                        WeaponRegistry.BOW1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.BOW23);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_2, null, null,
                        ItemRegistry.EAST_CRYSTALCLAW_2, ItemRegistry.EAST_BONE_2, ItemRegistry.ELEMENT_WOOD,
                        WeaponRegistry.FURNACE1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.FURNACE2);
        //南2
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.SOUTH_REDCRYSTAL_2, ItemRegistry.SOUTH_IRON_2, ItemRegistry.ELEMENT_LAVA,
                        WeaponRegistry.SWORD1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.SWORD20);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.SOUTH_REDCRYSTAL_2, ItemRegistry.SOUTH_IRON_2, ItemRegistry.ELEMENT_LAVA,
                        WeaponRegistry.BOW1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.BOW21);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.SOUTH_REDCRYSTAL_2, ItemRegistry.SOUTH_IRON_2, ItemRegistry.ELEMENT_LAVA,
                        WeaponRegistry.FURNACE1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.FURNACE2);
        //西2
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_2, null, null,
                        ItemRegistry.WEST_BLOODBEAD_2, ItemRegistry.WEST_GOLD_2, ItemRegistry.ELEMENT_METAL,
                        WeaponRegistry.SWORD1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.SWORD21);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_2, null, null,
                        ItemRegistry.WEST_BLOODBEAD_2, ItemRegistry.WEST_GOLD_2, ItemRegistry.ELEMENT_METAL,
                        WeaponRegistry.BOW1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.BOW20);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_2, null, null,
                        ItemRegistry.WEST_BLOODBEAD_2, ItemRegistry.WEST_GOLD_2, ItemRegistry.ELEMENT_METAL,
                        WeaponRegistry.FURNACE1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.FURNACE2);
        //北2
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.NORTH_MAGALINE_2, ItemRegistry.NORTH_ARROW_2, ItemRegistry.ELEMENT_WATER,
                        WeaponRegistry.SWORD1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.SWORD23);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.NORTH_MAGALINE_2, ItemRegistry.NORTH_ARROW_2, ItemRegistry.ELEMENT_WATER,
                        WeaponRegistry.BOW1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.BOW22);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_2, null, null,
                        ItemRegistry.NORTH_MAGALINE_2, ItemRegistry.NORTH_ARROW_2, ItemRegistry.ELEMENT_WATER,
                        WeaponRegistry.FURNACE1},
                new int[]{1, 0, 0, 4, 6, 12, 1}), WeaponRegistry.FURNACE2);
    }
    //武器3
    static {
        //东3
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_3, ItemRegistry.EAST_CLAW_3, null,
                        ItemRegistry.EAST_CRYSTAL_3, ItemRegistry.EAST_WOOD_3, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.SWORD22},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.SWORD32);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_3, ItemRegistry.EAST_CLAW_3, null,
                        ItemRegistry.EAST_CRYSTAL_3, ItemRegistry.EAST_WOOD_3, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.BOW23},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.BOW33);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_3, ItemRegistry.EAST_CLAW_3, null,
                        ItemRegistry.EAST_CRYSTAL_3, ItemRegistry.EAST_WOOD_3, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.FURNACE2},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.FURNACE3);
        //南3
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_3, ItemRegistry.SOUTH_STONE_3, null,
                        ItemRegistry.SOUTH_FIRECORE_3, ItemRegistry.SOUTH_BONE_3, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.SWORD20},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.SWORD30);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_3, ItemRegistry.SOUTH_STONE_3, null,
                        ItemRegistry.SOUTH_FIRECORE_3, ItemRegistry.SOUTH_BONE_3, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.BOW21},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.BOW31);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_3, ItemRegistry.SOUTH_STONE_3, null,
                        ItemRegistry.SOUTH_FIRECORE_3, ItemRegistry.SOUTH_BONE_3, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.FURNACE2},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.FURNACE3);
        //西3
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_3, ItemRegistry.WEST_TOOTH_3, null,
                        ItemRegistry.WEST_BONE_3, ItemRegistry.WEST_GOLDMETAL_3, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.SWORD21},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.SWORD31);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_3, ItemRegistry.WEST_TOOTH_3, null,
                        ItemRegistry.WEST_BONE_3, ItemRegistry.WEST_GOLDMETAL_3, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.BOW20},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.BOW30);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_3, ItemRegistry.WEST_TOOTH_3, null,
                        ItemRegistry.WEST_BONE_3, ItemRegistry.WEST_GOLDMETAL_3, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.FURNACE2},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.FURNACE3);
        //北3
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_3, ItemRegistry.NORTH_LANCER_3, null,
                        ItemRegistry.NORTH_GOLDFEATHER_3, ItemRegistry.NORTH_BONE_3, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.SWORD23},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.SWORD33);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_3, ItemRegistry.NORTH_LANCER_3, null,
                        ItemRegistry.NORTH_GOLDFEATHER_3, ItemRegistry.NORTH_BONE_3, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.BOW22},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.BOW32);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_3, ItemRegistry.NORTH_LANCER_3, null,
                        ItemRegistry.NORTH_GOLDFEATHER_3, ItemRegistry.NORTH_BONE_3, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.FURNACE2},
                new int[]{1, 8, 0, 6, 8, 8, 1}), WeaponRegistry.FURNACE3);
    }
    //武器4
    static {
        //东4
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_4, ItemRegistry.EAST_EXTRACT_4, ItemRegistry.EAST_SPIKE_4,
                        ItemRegistry.EAST_AMBER_4, ItemRegistry.EAST_BUGTOOTH_4, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.SWORD32},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.SWORD42);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_4, ItemRegistry.EAST_EXTRACT_4, ItemRegistry.EAST_SPIKE_4,
                        ItemRegistry.EAST_AMBER_4, ItemRegistry.EAST_BUGTOOTH_4, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.BOW33},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.BOW43);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_4, ItemRegistry.EAST_EXTRACT_4, ItemRegistry.EAST_SPIKE_4,
                        ItemRegistry.EAST_AMBER_4, ItemRegistry.EAST_BUGTOOTH_4, ItemRegistry.REFINED_WOOD,
                        WeaponRegistry.FURNACE3},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.FURNACE4);
        //南4
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_4, ItemRegistry.SOUTH_FIRE_4, ItemRegistry.SOUTH_ARM_4,
                        ItemRegistry.SOUTH_DEVILMUD_4, ItemRegistry.SOUTH_AXE_4, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.SWORD30},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.SWORD40);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_4, ItemRegistry.SOUTH_FIRE_4, ItemRegistry.SOUTH_ARM_4,
                        ItemRegistry.SOUTH_DEVILMUD_4, ItemRegistry.SOUTH_AXE_4, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.BOW31},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.BOW41);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_4, ItemRegistry.SOUTH_FIRE_4, ItemRegistry.SOUTH_ARM_4,
                        ItemRegistry.SOUTH_DEVILMUD_4, ItemRegistry.SOUTH_AXE_4, ItemRegistry.REFINED_LAVA,
                        WeaponRegistry.FURNACE3},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.FURNACE4);
        //西4
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_4, ItemRegistry.WEST_EYE_4, ItemRegistry.WEST_CLAW_4,
                        ItemRegistry.WEST_SPIKE_4, ItemRegistry.WEST_BONE_4, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.SWORD31},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.SWORD41);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_4, ItemRegistry.WEST_EYE_4, ItemRegistry.WEST_CLAW_4,
                        ItemRegistry.WEST_SPIKE_4, ItemRegistry.WEST_BONE_4, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.BOW30},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.BOW40);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_4, ItemRegistry.WEST_EYE_4, ItemRegistry.WEST_CLAW_4,
                        ItemRegistry.WEST_SPIKE_4, ItemRegistry.WEST_BONE_4, ItemRegistry.REFINED_METAL,
                        WeaponRegistry.FURNACE3},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.FURNACE4);
        //北4
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_4, ItemRegistry.NORTH_FIRE_4, ItemRegistry.NORTH_CLAW_4,
                        ItemRegistry.NORTH_TOOTH_4, ItemRegistry.NORTH_DIRT_4, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.SWORD33},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.SWORD43);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_4, ItemRegistry.NORTH_FIRE_4, ItemRegistry.NORTH_CLAW_4,
                        ItemRegistry.NORTH_TOOTH_4, ItemRegistry.NORTH_DIRT_4, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.BOW32},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.BOW42);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_4, ItemRegistry.NORTH_FIRE_4, ItemRegistry.NORTH_CLAW_4,
                        ItemRegistry.NORTH_TOOTH_4, ItemRegistry.NORTH_DIRT_4, ItemRegistry.REFINED_WATER,
                        WeaponRegistry.FURNACE3},
                new int[]{1, 16, 16, 12, 16, 20, 1}), WeaponRegistry.FURNACE4);
    }
    //武器5
    static {
        //东5
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WOOD, WeaponRegistry.SWORD42},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.SWORD52);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WOOD, WeaponRegistry.BOW43},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.BOW53);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.EAST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WOOD, WeaponRegistry.FURNACE4},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.FURNACE5);
        //南5
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_LAVA, WeaponRegistry.SWORD40},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.SWORD50);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_LAVA, WeaponRegistry.BOW41},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.BOW51);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.SOUTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_LAVA, WeaponRegistry.FURNACE4},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.FURNACE5);
        //西5
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_METAL, WeaponRegistry.SWORD41},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.SWORD51);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_METAL, WeaponRegistry.BOW40},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.BOW50);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.WEST_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_METAL, WeaponRegistry.FURNACE4},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.FURNACE5);
        //北5
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WATER, WeaponRegistry.SWORD43},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.SWORD53);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WATER, WeaponRegistry.BOW42},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.BOW52);
        recipes.put(new TempRecipeList(
                new String[]{ItemRegistry.NORTH_WEAPONFORGE_5,null, null,
                        null, null, ItemRegistry.ADVANCED_WATER, WeaponRegistry.FURNACE4},
                new int[]{1, 0, 0, 0, 0, 16, 1}), WeaponRegistry.FURNACE5);
    }
    public static Map<TempRecipeList, String> getRecipes(){
        return recipes;
    }
}
