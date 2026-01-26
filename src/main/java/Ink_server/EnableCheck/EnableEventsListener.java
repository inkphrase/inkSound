package Ink_server.EnableCheck;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import Ink_server.InkSound;
import static Ink_server.EnableCheck.ItemCheck.allCheck;
import static Ink_server.EnableCheck.ItemCheck.theSlotCheck;
import static org.bukkit.Bukkit.getScheduler;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EnableEventsListener implements Listener {
    private static final InkSound plugin = InkSound.getInstance();

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new EnableEventsListener(), plugin);
    }

    private EnableEventsListener(){
    }
    private static final Map<UUID, Long> silencedTime = new ConcurrentHashMap<>();

    @EventHandler
    public void onSlotChange(PlayerInventorySlotChangeEvent event){
        Player player = event.getPlayer();
        if (isSilenced(player)) return;
        ItemStack oldone = event.getOldItemStack();
        ItemStack newone = event.getNewItemStack();
        if (oldone.equals(newone)) return;
        int i = event.getSlot();
        getScheduler().runTask(plugin, ()-> {
            if (isSilenced(player)) return;
            theSlotCheck(player, i, oldone, newone);
        });
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if (event.getInventory().getType() == InventoryType.CRAFTING) return;
        UUID uid = event.getPlayer().getUniqueId();
        setSilencedTime(uid, 1L);
    }
    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if (event.getInventory().getType() == InventoryType.CRAFTING) return;
        UUID uid = event.getPlayer().getUniqueId();
        setSilencedTime(uid, 1L);
    }
    @EventHandler
    public void onGetIn(PlayerJoinEvent event){
        Player player = event.getPlayer();
        getScheduler().runTask(plugin, ()-> allCheck(player));
        setSilencedTime(player.getUniqueId(), 20L);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        silencedTime.remove(event.getPlayer().getUniqueId());
    }
    @EventHandler
    public void onReborn(PlayerRespawnEvent event){
        Bukkit.getScheduler().runTask(plugin, ()-> allCheck(event.getPlayer()));
    }

    public void setSilencedTime(UUID uid, Long time){
        long removeTime = Bukkit.getServer().getCurrentTick() + time;
        silencedTime.put(uid, removeTime);
    }
    public boolean isSilenced(Player player){
        Long removeTime = silencedTime.get(player.getUniqueId());
        if (removeTime == null) return false;
        if (Bukkit.getServer().getCurrentTick() < removeTime) {return true;}
        else {
            silencedTime.remove(player.getUniqueId(), removeTime);
            return  false;
        }
    }
}