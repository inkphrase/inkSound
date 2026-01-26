package Ink_server.StaticDatas.MonsterLib;

import Ink_server.StaticDatas.ItemLib.ItemDataManager;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public enum MonsterDrop {
    EAST_ZOMBIE_2(MonsterRegistry.EAST_ZOMBIE_2){
        @Override
        public void handle(List<ItemStack> drops){
            double r1 = new Random().nextDouble();
            double r2 = new Random().nextDouble();
            double r3 = new Random().nextDouble();
            //池1：元素、铜钱、重生石
            if (r1 < 0.6){
                drops.add(ItemDataManager.getItem(ItemRegistry.ELEMENT_WOOD));
            }else if (r1 < 0.8){
                drops.add(ItemDataManager.getItem(ItemRegistry.ELEMENT_WOOD));
                drops.add(ItemDataManager.getItem(ItemRegistry.MONEY0));
            }else  if (r1 < 0.9){
                drops.add(ItemDataManager.getItem(ItemRegistry.REBORN_STONE));
                drops.add(ItemDataManager.getItem(ItemRegistry.MONEY0));
            }else  {
                drops.add(ItemDataManager.getItem(ItemRegistry.REBORN_STONE));
                drops.add(ItemDataManager.getItem(ItemRegistry.MONEY0));
                drops.add(ItemDataManager.getItem(ItemRegistry.ELEMENT_WOOD));
            }
            //池2：材料、货物、通用
            if (r2 < 0.3){
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_CLOTH_2));
            }else if (r2 < 0.5){
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_ZOMBIECORE_2));
            }else if (r2 < 0.7){
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_GOODS_2));
            }else  if (r2 < 0.9){
                drops.add(ItemDataManager.getItem(ItemRegistry.MONEY0));
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_CLOTH_2));
            }else {
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_GOODS_2));
                drops.add(ItemDataManager.getItem(ItemRegistry.EAST_ZOMBIECORE_2));
            }
            //池3：核心
            if (r3 < 0.1){
                drops.add(ItemDataManager.getItem(ItemRegistry.ARMORCORE2));
            }else  if (r3 < 0.2){
                drops.add(ItemDataManager.getItem(ItemRegistry.ELEMENT_WOOD));
            }else  if (r3 < 0.21){
                drops.add(ItemDataManager.getItem(ItemRegistry.MONEY2));
            }
        }
    };

    private final String id;
    private static final Map<String, MonsterDrop> monsters = new HashMap<>();
    MonsterDrop(String id){
        this.id = id;
    }
    static {
        for (MonsterDrop monsterDrop : MonsterDrop.values()) {
            monsters.put(monsterDrop.id, monsterDrop);
        }
    }
    public static MonsterDrop getById(String id){
        return monsters.get(id);
    }

    public String getId() {
        return id;
    }

    public abstract void handle(List<ItemStack> drops);
}
