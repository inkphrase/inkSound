package Ink_server;

import Ink_server.Forge.ForgeTable;
import Ink_server.Forge.Recipe;
import Ink_server.InkSoundEvents.PlayerUseElementEventCaller;
import Ink_server.InkSoundFight.Fight;
import Ink_server.InkSoundFight.SkillEffectListener;
import Ink_server.LandTpSystem.OverLandTp;
import Ink_server.LandTpSystem.RaceTp;
import Ink_server.LandTpSystem.Reborn;
import Ink_server.LandTpSystem.farmProtector;
import Ink_server.House.BuyHouse;
import Ink_server.House.EnderChestMenu;
import Ink_server.House.GetHouse;
import Ink_server.House.StorageManager;
import Ink_server.NPCs.NPCHandler;
import Ink_server.NPCs.NPCRegistry;
import Ink_server.NPCs.VillagerCreator;
import Ink_server.NPCs.VillagerTradeManager;
import Ink_server.PlayerJoinLand.*;
import Ink_server.StaticDatas.*;
import Ink_server.InkSoundFight.TickTimer;
import Ink_server.StaticDatas.ItemLib.ItemDataManager;
import Ink_server.StaticDatas.ItemLib.ItemRegistry;
import Ink_server.StaticDatas.MinecraftDisabler.AdvancementDisabler;
import Ink_server.StaticDatas.MonsterLib.MonsterDeath;
import Ink_server.StaticDatas.MonsterLib.MonsterManager;
import Ink_server.StaticDatas.MonsterLib.MonsterRegistry;
import Ink_server.StaticDatas.MonsterLib.MonsterSpawner;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoListener;
import Ink_server.StaticDatas.PlayerInformance.PlayerInfoManager;
import Ink_server.EntityTempData.EntityDataManager;
import Ink_server.StaticDatas.WeaponLib.WeaponManager;
import Ink_server.StaticDatas.MinecraftDisabler.RecipeDisabler;
import Ink_server.StaticDatas.WeaponLib.WeaponRegistry;
import Ink_server.TestCommands.*;
import org.bukkit.plugin.java.JavaPlugin;
import Ink_server.EnableCheck.EnableEventsListener;

import java.util.Objects;


public final class InkSound extends JavaPlugin {
    private static InkSound instance;
    private RecipeDisabler recipeDisabler;
    private AdvancementDisabler advancementDisabler;
    private GetHouse getHouse;
    private VillagerTradeManager villagerTradeManager;
    private TickTimer tickTimer;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        //注册实体数据管理器
        EntityDataManager.init();

        //注册检测激活的监听器
        EnableEventsListener.init();

        //注册属性调试指令
        PlayerDataCommandExecutor playerDataCommandExecutor = new PlayerDataCommandExecutor();
        Objects.requireNonNull(getCommand("pldata")).setExecutor(playerDataCommandExecutor);

        //注册获取武器指令
        GivePanlingItem givePanlingItem = new GivePanlingItem();
        Objects.requireNonNull(getCommand("plgive")).setExecutor(givePanlingItem);
        Objects.requireNonNull(getCommand("plgive")).setTabCompleter(givePanlingItem);

        //注册生成怪物指令
        SpawnPanlingMonster spawnPanlingMonster = new SpawnPanlingMonster();
        Objects.requireNonNull(getCommand("plspawn")).setExecutor(spawnPanlingMonster);
        Objects.requireNonNull(getCommand("plspawn")).setTabCompleter(spawnPanlingMonster);

        //注册武器、物品、怪物库
        loadLibData();

        //重写怪物掉落逻辑
        MonsterDeath.init();

        //注册调试玩家信息指令
        PlayerInfoCommand playerInfoCommand = new PlayerInfoCommand();
        Objects.requireNonNull(getCommand("plinfo")).setExecutor(playerInfoCommand);
        Objects.requireNonNull(getCommand("plinfo")).setTabCompleter(playerInfoCommand);

        //注册pdc清除指令
        //PdcClear pdcClear = new PdcClear();
        //Objects.requireNonNull(getCommand("clearpdc")).setExecutor(pdcClear);
        //itemdata
        ItemDataCommand itemDataCommand = new ItemDataCommand();
        Objects.requireNonNull(getCommand("itemdata")).setExecutor(itemDataCommand);
        GetPdcCommand getPdcCommand = new GetPdcCommand();
        Objects.requireNonNull(getCommand("pdcget")).setExecutor(getPdcCommand);
        Objects.requireNonNull(getCommand("pdcget")).setTabCompleter(getPdcCommand);

        //注册玩家信息监听器
        PlayerInfoListener.init();

        //注册选择种族、职业监听器
        JoinEventsListener.init();

        //注册复活与传送监听器
        Reborn.init();
        RaceTp.init();
        OverLandTp.init();

        //注册锻造台监听器
        ForgeTable.init();

        //注册锻造配方
        Recipe.init();

        //注册田地保护
        farmProtector.init();

        //注册仓库
        StorageManager.init();

        //注册末影箱菜单
        EnderChestMenu.init();

        //注册打开仓库指令
        OpenStorage openStorage = new OpenStorage();
        Objects.requireNonNull(getCommand("plopen")).setExecutor(openStorage);
        Objects.requireNonNull(getCommand("plopen")).setTabCompleter(openStorage);

        //注册元素事件监听器
        PlayerUseElementEventCaller.init();

        // ========== 添加配方禁用功能 ==========
        getLogger().info("========原版禁用========");
        recipeDisabler = new RecipeDisabler();
        recipeDisabler.enable();

        //成就禁用
        advancementDisabler = new AdvancementDisabler(this);
        advancementDisabler.enable();

        getLogger().info("=======================");

        //初始化府邸数据
        getHouse = new GetHouse();
        getHouse.load();
        getLogger().info("府邸数据已加载！");

        //注册府邸购买菜单
        BuyHouse.init();

        //注册 NPC 生成器
        VillagerCreator.init();
        Functions.setTeamCollision(Factions.INGAME, false);

        //注册 NPC 交易项管理器
        villagerTradeManager = new VillagerTradeManager();
        villagerTradeManager.load();
        NPCHandler.init();

        //注册静态类
        NPCRegistry.init();
        Factions.init();
        Functions.init();
        ItemKeys.init();
        NPCID.init();
        PlayerKeys.init();

        //注册伤害系统
        Fight.init();

        //注册技能触发
        SkillEffectListener.init();

        //注册自定义刷怪笼
        MonsterSpawner.init();

        //开启定时器任务 一定放在最后！！！
        tickTimer = new TickTimer();

        //发送插件独特文本
        getLogger().info("""
                
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
                """);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        tickTimer.disable();
        EntityDataManager.UnloacAll();
        PlayerInfoManager.saveAll();
        // ========== 禁用配方功能（可选）==========
        if (recipeDisabler != null) {
            recipeDisabler.disable();
        }
        //禁用成就功能
        if (advancementDisabler != null){
            advancementDisabler.disable();
        }
        instance = null;
        //保存玩家仓库
        StorageManager.onDisable();
        //保存府邸信息
        getHouse.save();
        //保存交易项
        villagerTradeManager.save();
    }

    public void loadLibData() {
        WeaponRegistry.init();
        ItemRegistry.init();
        ItemDataManager.LoadMap();
        MonsterRegistry.init();
        getLogger().info("武器数据加载完成，共 " + WeaponManager.getWeaponLib().size() + " 件武器");
        getLogger().info("物品数据加载完成，共 " + ItemDataManager.getItemLib().size() + " 件物品");
        getLogger().info("怪物数据加载完成，共 " + MonsterManager.getMonsterLib().size() + " 只怪物");
    }

    public static InkSound getInstance() {return instance;}
}
