package Ink_server.StaticDatas.MonsterLib;

import Ink_server.InkSound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.EntityType;

public class MonsterRegistry {
    public static void init(){
        InkSound.getInstance().getLogger().info("怪物库已注册！");
    }

    //region 注册怪物id
    public static final String EAST_ZOMBIE_2 = "east.zombie.2";
    public static final String EAST_SKELETON_2 = "east.skeleton.2";
    public static final String EAST_GHOSTBONE_2 = "east.ghostbone.2";

    static {
        MonsterData eastZombie2 = new MonsterData(EAST_ZOMBIE_2, EntityType.ZOMBIE,
                Component.text("魔化妖尸").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        eastZombie2.setAttribute(MonsterData.ATTACKBASE, 5);
        eastZombie2.setAttribute(MonsterData.ARMORBASE, 5);
        eastZombie2.setAttribute(MonsterData.HEALTHBASE, 0);
        MonsterManager.addMonster(eastZombie2);

        MonsterData eastSkeleton2 = new MonsterData(EAST_SKELETON_2, EntityType.SKELETON,
                Component.text("魔化枯骨").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC,false));
        eastSkeleton2.setAttribute(MonsterData.ATTACKBASE, 5);
        eastSkeleton2.setAttribute(MonsterData.ARMORBASE, 2);
        eastSkeleton2.setAttribute(MonsterData.HEALTHBASE, -5);
        MonsterManager.addMonster(eastSkeleton2);

        MonsterData eastGhostbone2 = new MonsterData(EAST_GHOSTBONE_2, EntityType.STRAY,
                Component.text("神速的 幽影魔骨").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false));
        eastGhostbone2.setAttribute(MonsterData.ATTACKBASE, 10);
        eastGhostbone2.setAttribute(MonsterData.ARMORBASE, 10);
        eastGhostbone2.setAttribute(MonsterData.HEALTHBASE, 20);
        eastGhostbone2.setAttribute(MonsterData.SPEED, 300);
        MonsterManager.addMonster(eastGhostbone2);
    }
}
