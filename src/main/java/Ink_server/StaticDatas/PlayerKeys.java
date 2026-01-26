package Ink_server.StaticDatas;

import Ink_server.InkSound;
import org.bukkit.NamespacedKey;

public class PlayerKeys {
    public static final String namespace = "inksound";
    public static final NamespacedKey PLAYERINFO = new NamespacedKey(namespace, "playerinfo");

    public static void init(){
        InkSound.getInstance().getLogger().info("玩家PDC键已注册！");
    }
}
