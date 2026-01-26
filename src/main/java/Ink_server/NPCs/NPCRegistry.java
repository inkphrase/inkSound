package Ink_server.NPCs;

import Ink_server.InkSound;
import Ink_server.NPCs.East.EastForgecoreSeller;
import Ink_server.NPCs.Middle.MiddleElementRefineder;
import Ink_server.NPCs.Middle.MiddleHouseSeller;
import Ink_server.StaticDatas.NPCID;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.Map;


public enum NPCRegistry {
    SHI_AN(NPCID.SHI_AN) {
        @Override
        public void handle(Player player, Villager villager) {
            MiddleHouseSeller.handle(player);
        }
    },

    EAST_FORGECORE_SELLER(NPCID.EAST_FORGECORE_SELLER) {
        @Override
        public void handle(Player player, Villager villager) {
            EastForgecoreSeller.handle(player, villager.customName());
        }
    },

    MIDDLE_ELEMENT_REFINEDER(NPCID.MIDDLE_ELEMENT_REFINEDER) {
        @Override
        public void handle(Player player, Villager villager) {
            MiddleElementRefineder.handle(player, villager.customName());
        }
    };

    private final String id;
    private static final Map<String, NPCRegistry> ID_MAP = new HashMap<>();

    static {
        for (NPCRegistry npc : values()) ID_MAP.put(npc.id, npc);
    }

    public static void init() {
        InkSound.getInstance().getLogger().info("NPC交互行为已注册！");
    }

    NPCRegistry(String id) { this.id = id; }

    public abstract void handle(Player player, Villager villager);

    public static NPCRegistry getById(String id) {
        return id == null ? null : ID_MAP.get(id);
    }
}
