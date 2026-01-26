package Ink_server.LandTpSystem;

import Ink_server.InkSound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class farmProtector implements Listener {

    public static void init(){
        InkSound plugin = InkSound.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new farmProtector(), plugin);
    }

    private farmProtector(){}

    private static final PotionEffect slowPotion = new PotionEffect(
            PotionEffectType.SLOWNESS,
            20,9,false,true
    );
    @EventHandler
    public static void farmProtect(EntityChangeBlockEvent event){
        Block block = event.getBlock();
        if (block.getType() == Material.FARMLAND && event.getTo() == Material.DIRT){
            event.setCancelled(true);
            if (event.getEntity() instanceof Player player){
                player.sendMessage(Component.text("请不要破坏农作物哦！").color(NamedTextColor.RED));
                player.addPotionEffect(slowPotion);
            }
        }
    }
}
