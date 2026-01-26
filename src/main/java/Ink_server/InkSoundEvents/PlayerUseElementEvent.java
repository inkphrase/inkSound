package Ink_server.InkSoundEvents;

import Ink_server.EntityTempData.EntityData;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.StaticDatas.ItemKeys;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfo;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerUseElementEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean cancelled = false; // 记录取消状态

    private final Player player;
    private final String element;

    public PlayerUseElementEvent(Player player, String element) {
        this.player = player;
        this.element = element;
    }

    // 必须实现此方法
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    //必须实现的方法：获取当前的取消状态
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    //必须实现的方法：设置取消状态
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public String getElement() {
        return element;
    }
}

