package Ink_server.StaticDatas;

import Ink_server.InkSound;
public class Factions {

    public static void init(){
        InkSound.getInstance().getLogger().info("队伍名列表已注册！");
    }

    public static final String INGAME = "InGame";
    public static final String NPCS = "plNPC";
    public static final String MONSTERS = "plMonster";

}
