package Ink_server.StaticDatas.ItemLib;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public record ItemData(String id, Material material, Component name, String model, List<Component> lore) {
}
