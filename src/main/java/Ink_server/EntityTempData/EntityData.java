package Ink_server.EntityTempData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityData {
    //这里记录了真实数据
    private double
            //玩家实际战斗数据
            attackData, armorData, healthData,
            cools, speed,
            armorBreak, envDefence, multiple, resistance,
            recycle, pierce = 0.0;

    public double
            //玩家装备数据
            attackBaseSlot, armorBaseSlot, healthBaseSlot,
            attackPersSlot, armorPersSlot, healthPersSlot,
            attackFinalSlot, armorFinalSlot, healthFinalSlot,
            coolsSlot, speedSlot,
            armorBreakSlot, envDefenceSlot, multipleSlot, resistanceSlot, recycleSlot, pierceSlot,
            //玩家buff数据
            attackBaseBuff, armorBaseBuff, healthBaseBuff,
            attackPersBuff, armorPersBuff, healthPersBuff,
            attackFinalBuff, armorFinalBuff, healthFinalBuff,
            coolsBuff, speedBuff, armorBreakBuff,
            //玩家永久属性数据（需每次上线赋值）
            attackBuffPerm, armorBuffPerm, healthBuffPerm, coolsBuffPerm, speedBuffPerm, armorBreakPerm
            = 0.0;

    //这里记录了cd、buff、dot
    public Map<String, Integer> cooldownMap = new ConcurrentHashMap<>();
    public Map<String, BuffEffect> buffEffectMap = new ConcurrentHashMap<>();
    public Map<String, DotEffect> dotEffectMap = new ConcurrentHashMap<>();
    public Map<String, Skills> skillsMap = new ConcurrentHashMap<>();

    //这里记录了一些天赋、技能等所需的标记等变量，初始化时为0或空，使用技能时赋值

    //炼丹师天赋阴阳鱼，最高九层，阴鱼每层获得2%进攻属性，阳鱼每层获得1%额外速度力，阴阳鱼满层时额外获得一层的buff
    //此后炼丹师可消耗阴阳鱼各一层，炼化精炼元素释放奥义阵法，所有奥义阵法共用一个cd
    public int YIN;
    public int YANG;

    // 清空buff加成数据
    public void clearBuff(){
            attackBaseBuff  = 0.0;
            armorBaseBuff = 0.0;
            healthBaseBuff = 0.0;
            attackPersBuff = 0.0;
            armorPersBuff = 0.0;
            healthPersBuff = 0.0;
            attackFinalBuff =  0.0;
            armorFinalBuff =  0.0;
            healthFinalBuff =  0.0;
            coolsBuff = 0.0;
            speedBuff = 0.0;
            armorBreakBuff = 0.0;
    }

    public Map<String, BuffEffect> getBuffEffectMap() {
        return buffEffectMap;
    }

    public Map<String, DotEffect> getDotEffectMap() {
        return dotEffectMap;
    }

    public Map<String, Skills> getSkillsMap() {return  skillsMap;}

    public void setBuffEffect(String id, BuffEffect buffEffect) {
        buffEffectMap.put(id, buffEffect);
    }

    public void setDotEffect(String id, DotEffect dotEffect) {
        dotEffectMap.put(id, dotEffect);
    }

    public void setSkillsMap(String id, Skills skills) {
        skillsMap.put(id, skills);
    }

    //最终战斗数据的getter
    public double getAttackData() {
        return attackData;
    }
    public double getArmorData() {
        return armorData;
    }
    public double getHealthData() {
        return healthData;
    }
    public double getCools() {
        return cools;
    }
    public double getSpeed() {
        return speed;
    }
    public double getArmorBreak() {
        return armorBreak;
    }
    public double getEnvDefence() {return envDefence;}
    public double getMultiple() {return multiple;}
    public double getResistance() {return resistance;}
    public double getRecycle() {
        return recycle;
    }
    
    //计算函数
    public void calculateData(){
        // 计算攻击力
        attackData =
                (attackBaseSlot + attackBaseBuff) *
                        (attackPersSlot / 100 + attackPersBuff / 100 + 1) *
                        (attackFinalSlot / 100 + attackFinalBuff / 100 + (double) (YIN == 9 ? 10 : YIN) / 50 + 1);
        // 计算护甲
        armorData =
                (armorBaseSlot + armorBaseBuff) *
                        (armorPersSlot / 100 + armorPersBuff / 100 + 1) *
                        (armorFinalSlot / 100 + armorFinalBuff / 100 + 1);
        // 计算生命值
        healthData =
                (healthBaseSlot + healthBaseBuff + 20) *
                        (healthPersSlot / 100 + healthPersBuff / 100 + 1) *
                        (healthFinalSlot / 100 + healthFinalBuff / 100 + 1);
        // 计算冷却
        cools =
                coolsSlot + coolsBuff;
        //计算加速
        speed =
                speedSlot + speedBuff;
        //计算穿甲
        armorBreak =
                armorBreakSlot + armorBreakBuff;
        //计算环境减免
        envDefence = envDefenceSlot;
        //计算额外倍率
        multiple = multipleSlot;
        //击退抗性
        resistance = resistanceSlot;
        //不消耗率
        recycle = recycleSlot;
        //穿透
        pierce = pierceSlot;
    }

    public double skillDamage;

    public double getPierce() {
        return pierce;
    }

    //这里记录怪物的一些属性
    public int envDotTick;
}
