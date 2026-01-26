package Ink_server.InkSoundFight.Elements;

import Ink_server.EntityTempData.EntityData;
import Ink_server.InkSound;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public enum UltimateElementsRegistry {
    REFINED_METAL(ItemRegistry.REFINED_METAL){
    };

    private final String elementId;
    public static final Map<String, UltimateElementsRegistry> ID_MAP = new HashMap<>();
    static {
        for (UltimateElementsRegistry element : values()) {
            ID_MAP.put(element.elementId, element);
        }
    }

    public static void init(){
        InkSound.getInstance().getLogger().info("奥义阵法已注册");
    }


    UltimateElementsRegistry(String id) { this.elementId = id; }

    public static UltimateElementsRegistry getById(String id) { return ID_MAP.get(id); }

}
