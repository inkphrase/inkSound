package Ink_server.InkSoundFight.Elements;


import Ink_server.EntityTempData.EntityData;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过枚举类判断元素并设置效果
 */
public enum ElementsRegistry {
    ELEMENT_METAL(ItemRegistry.ELEMENT_METAL){
        @Override
        public void handle(Player player, EntityData playerData){
            Metal.metalEffect(player, playerData);
        }
    };

    private final String elementId;
    public static final Map<String, ElementsRegistry> ID_MAP = new HashMap<>();
    static {
        for (ElementsRegistry element : values()) {
            ID_MAP.put(element.elementId, element);
        }
    }

    public static void init(){
        InkSound.getInstance().getLogger().info("元素阵法已注册");
    }


    ElementsRegistry(String id) { this.elementId = id; }

    public static ElementsRegistry getById(String id) { return ID_MAP.get(id); }

    public abstract void handle(Player player, EntityData playerData);
}
