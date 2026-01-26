package Ink_server.StaticDatas;

import Ink_server.InkSound;

import java.util.HashMap;
import java.util.Map;

public class NPCID {

    public static void init(){
        InkSound.getInstance().getLogger().info("NPC信息已注册！");
    }

    public static final Map<String, String> NPCMap = new HashMap<>();
    public static final String SHI_AN = "middle.HouseSeller";
    public static final String EAST_FORGECORE_SELLER = "east.ForgecoreSeller";
    public static final String HOUSE_BUTLER = "house.Butler";
    public static final String MIDDLE_ELEMENT_REFINEDER = "middle.ElementRefineder";
    //皇城 npc
    static {
        NPCMap.put(SHI_AN, "石安");
        NPCMap.put(MIDDLE_ELEMENT_REFINEDER, "元素精炼师");
    }
    //东方 npc
    static {
        NPCMap.put(EAST_FORGECORE_SELLER, "龙须镇铁匠");
    }

    //非大陆 npc
    static {
        NPCMap.put(HOUSE_BUTLER, "府邸管家");
    }
}
