package Ink_server.PlayerJoinLand;

import Ink_server.InkSound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static Ink_server.PlayerJoinLand.ChooseJob.jobChoose;
import static Ink_server.PlayerJoinLand.ChooseJob.jobChooser;
import static Ink_server.PlayerJoinLand.ChooseRace.raceChoose;
import static Ink_server.PlayerJoinLand.ChooseRace.raceChooser;
import static Ink_server.PlayerJoinLand.FinalConfirm.endCheck;
import static Ink_server.PlayerJoinLand.FinalConfirm.ends;
import static Ink_server.PlayerJoinLand.ReLife.raceRlifer;
import static Ink_server.PlayerJoinLand.ReLife.relifer;
import static Ink_server.PlayerJoinLand.StoryEnd.endStory;
import static Ink_server.PlayerJoinLand.StoryEnd.storys;

public class JoinEventsListener implements Listener {
    private static final InkSound plugin = InkSound.getInstance();
    private JoinEventsListener(){}

    public static void init(){
        plugin.getServer().getPluginManager().registerEvents(new JoinEventsListener(), plugin);
    }

    private static int checkLoc(Location loc){
        if (raceChooser.containsKey(loc)) return 0;
        else if (jobChooser.containsKey(loc)) return 1;
        else if (storys.contains(loc)) return 2;
        else if (ends.containsKey(loc)) return 3;
        else if (raceRlifer.containsKey(loc)) return 4;
        else return 5;
    }

    @EventHandler
    public void onSelect(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block == null) return;
        Location loc = block.getLocation();
        switch (checkLoc(loc)){
            case 0 -> raceChoose(event,player,block,loc);
            case 1 -> jobChoose(event,player,block,loc);
            case 2 -> endStory(event,player,block);
            case 3 -> endCheck(event,player,block,loc);
            case 4 -> relifer(event,player,block,loc);

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()){
            plugin.getServer().getScheduler().runTask(plugin, ()-> player.teleport(FinalConfirm.location));
            player.sendMessage(Component.text("""
                    
                    =========== InkSound 盘灵古域 韵起墨落 ===========
                          ________     ____     __     __    ___
                         |___  ___|   |    \\   |  |   |  |  /  /
                            |  |      |     \\  |  |   |  |_/  /
                            |  |      |  |\\  \\ |  |   |      /
                            |  |      |  | \\  \\|  |   |   _  \\
                          __|  |__    |  |  \\     |   |  | \\  \\
                         |________|   |__|   \\____|   |__|  \\__\\
                       ________   _____   __    __  __   __  _____
                      /  ______| /  _  \\ |  |  |  ||  \\ |  ||  _  \\
                      \\  \\____  |  | |  ||  |  |  ||   \\|  || | \\  \\
                       \\____  \\ |  | |  ||  |  |  ||       || | |  |
                       _____\\  \\|  |_|  ||  \\__/  ||  |\\   || |_/  /
                      |________/ \\_____/  \\______/ |__| \\__||_____/
                    ============ 作者 ink_phrase PAN-LING ============
                    """).color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC,false));
        }
    }
}
