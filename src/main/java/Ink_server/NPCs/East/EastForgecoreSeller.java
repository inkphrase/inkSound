package Ink_server.NPCs.East;

import Ink_server.NPCs.VillagerTradeManager;
import Ink_server.StaticDatas.NPCID;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

/**
 * 龙须镇铁匠
 */
public class EastForgecoreSeller {
    private static final String id = NPCID.EAST_FORGECORE_SELLER;

    public static void handle(Player player, Component title) {
        if (player.getInventory().getItemInMainHand().getType() == Material.GLOW_ITEM_FRAME){

        }else openTrade(player, title);
    }

    public static void openTrade(Player player, Component title){
        List<MerchantRecipe> recipes = VillagerTradeManager.getMerchantRecipes(id);
        Merchant merchant = Bukkit.createMerchant(title);
        merchant.setRecipes(recipes);
        player.openMerchant(merchant,true);
    }
}
