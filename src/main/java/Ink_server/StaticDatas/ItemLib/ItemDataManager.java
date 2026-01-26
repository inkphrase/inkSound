package Ink_server.StaticDatas.ItemLib;

import Ink_server.StaticDatas.ItemKeys;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemDataManager {
    private static final Map<String, ItemData> itemLib = new ConcurrentHashMap<>();
    private static final Map<String, ItemStack> itemMap = new ConcurrentHashMap<>();

    /*
    public static void itemsLoad(File itemFile) {
        itemLib.clear();

        try (FileReader reader = new FileReader(itemFile)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            if(root.getAsJsonObject("materials") != null){loader(root, "materials");}
            if(root.getAsJsonObject("others") != null){loader(root, "others");}

        }  catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加载物品JSON文件失败: " + e.getMessage());
        }
    }

    public static void loader(JsonObject root ,String kind){
        JsonObject object = root.getAsJsonObject(kind);
        for (String id : object.keySet()) {
            JsonObject matJson = object.getAsJsonObject(id);
            if (matJson == null) continue;

            // 获取基本信息
            String material = matJson.has("material") ?
                    matJson.get("material").getAsString() : "STONE";
            String name = matJson.has("name") ?
                    matJson.get("name").getAsString() : id;
            String model = matJson.has("model") ?
                    matJson.get("model").getAsString() : kind + "/" + id;

            Material mat = Material.valueOf(material.toUpperCase());
            Component displayName = Component.text(name);

            // 获取lore列表
            List<String> lore = new ArrayList<>();
            if (matJson.has("lore")) {
                JsonArray loreArray = matJson.getAsJsonArray("lore");
                for (int i = 0; i < loreArray.size(); i++) {
                    lore.add(loreArray.get(i).getAsString());
                }
            }

            List<Component> lores = new ArrayList<>();
            for (String line : lore) {
                lores.add(Component.text(line));
            }

            // 创建WeaponData对象
            ItemData itemDatas = new ItemData(id, mat, displayName, model, lores);

            itemLib.put(id, itemDatas);
        }
    }
     */

    public static ItemStack buidItem(String id){
        ItemData itemData = getItemLib().get(id);
        if (itemData == null) return null;

        ItemStack item = new ItemStack(itemData.material());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        meta.displayName(itemData.name());
        if (itemData.model() != null){
            NamespacedKey modelKey = new NamespacedKey("inksound", itemData.model());
            meta.setItemModel(modelKey);
        }
        List<Component> lore = new ArrayList<>(itemData.lore());
        meta.lore(lore);

        var pdc = meta.getPersistentDataContainer();
        // 设置必要的pdc：id
        pdc.set(ItemKeys.ID, PersistentDataType.STRING, id);
        meta.addItemFlags(ItemFlag.values());

        item.setItemMeta(meta);
        return item;
    }

    public static void LoadMap(){
        for (String itemId : itemLib.keySet()){
            itemMap.put(itemId, buidItem(itemId));
        }
    }


    public static Map<String, ItemData> getItemLib(){
        return Collections.unmodifiableMap(itemLib);
    }

    public static ItemStack getItem(String id){
        ItemStack cached = itemMap.get(id);
        return cached != null ? cached.clone() : null;
    }

    public static void addItem(ItemData itemData){
        itemLib.put(itemData.id(), itemData);
    }
}