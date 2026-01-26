package Ink_server.NPCs.Middle;

import Ink_server.House.BuyHouse;
import Ink_server.StaticDatas.NPCID;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MiddleHouseSeller {
    private static final String id = NPCID.SHI_AN;

    public static void handle(Player player) {
        if (player.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME){

        }else openTrade(player);
    }

    public static void openTrade(Player player) {
        BuyHouse.openBuyMenu(player);
    }
}
