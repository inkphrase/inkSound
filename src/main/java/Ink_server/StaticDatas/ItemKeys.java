package Ink_server.StaticDatas;

import Ink_server.InkSound;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.units.qual.N;

public class ItemKeys {
    public static final String namespace = PlayerKeys.namespace;
    public static final NamespacedKey ID = new NamespacedKey(namespace, "id");
    public static final NamespacedKey ENABLE = new NamespacedKey(namespace, "enable");
    public static final NamespacedKey ENCHANT = new NamespacedKey(namespace, "enchant");
    public static final NamespacedKey ATTACKSPEED = new NamespacedKey(namespace, "attackspeed");

    //实体的pdc
    public static final NamespacedKey ATTACK = new NamespacedKey(namespace, "attack");
    public static final NamespacedKey ARMORBREAK = new NamespacedKey(namespace, "armorbreak");
    public static final NamespacedKey MULTIPLE = new NamespacedKey(namespace, "multiple");
    public static final NamespacedKey FORCE = new NamespacedKey(namespace, "force");
    public static final NamespacedKey SKILLS = new  NamespacedKey(namespace, "skills");
    public static final NamespacedKey WEAPON_SKILL = new  NamespacedKey(namespace, "weapon_skill");

    public static void init(){
        InkSound.getInstance().getLogger().info("物品/实体PDC键已注册！");
    }
}
