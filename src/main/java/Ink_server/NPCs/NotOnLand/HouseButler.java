package Ink_server.NPCs.NotOnLand;

import Ink_server.StaticDatas.NPCID;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HouseButler {
    private  static String id = NPCID.HOUSE_BUTLER;

    public static void handle(Player player) {
        if (player.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME){

        }else{

        }
    }
}
