package Ink_server.StaticDatas.PlayerInformance;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInfo {
    private boolean inGame = false;
    private boolean inLand = false;
    private boolean inHouse = false;
    private double job = -1.0;
    private double race = -1.0;
    private final Set<String> completedQuests = new HashSet<>();
    private final Map<String, Boolean> raceBless = new ConcurrentHashMap<>();
    private double money;
    private double elements;
    private int rebornPlace = -1;
    private int houseId = -1;
    //天赋能力
    private boolean talent = false;

    //击杀数
    private int killNum = 0;

    private final Map<String, Integer> restCooldownMap = new ConcurrentHashMap<>();

    // --- Boolean 类型通常使用 'is' 而非 'get' ---
    public boolean isInGame() {return inGame;}

    public void setInGame(boolean inGame) {this.inGame = inGame;}

    public void setInLand(boolean inLand) {this.inLand = inLand;}

    public void setInHouse(boolean inHouse) {this.inHouse = inHouse;}

    // --- 基础字段的标准 Getter 和 Setter ---
    public double getJob() {return job;}

    public void setJob(double job) {this.job = job;}

    public double getRace() {return race;}

    public void setRace(double race) {this.race = race;}

    public double getMoney() {return money;}

    public void setMoney(double money) {this.money = money;}

    public double getElements() {return elements;}

    public void setElements(double elements) {this.elements = elements;}

    public Set<String> getCompletedQuests() {return completedQuests;}

    public void addCompletedQuest(String questId) {this.completedQuests.add(questId);}

    public Map<String, Boolean> getRaceBless() {return raceBless;}

    public void setBlessing(String blessingName, boolean active) {this.raceBless.put(blessingName, active);}

    public int getRebornPlace(){return rebornPlace;}

    public void setRebornPlace(int num){this.rebornPlace = num;}

    public int getHouseId() {return houseId;}

    public void setHouseId(int id){houseId = id;}

    public boolean isInLand() {
        return inLand;
    }

    public boolean isInHouse() {
        return inHouse;
    }

    public Map<String, Integer> getRestCooldownMap() {
        return restCooldownMap;
    }

    public void  setRestCooldownMap(String id, Integer cooldown) {
        this.restCooldownMap.put(id, cooldown);
    }

    public void clearRestCooldownMap(){
        restCooldownMap.clear();
    }

    public int getKillNum() {
        return killNum;
    }

    public void addKillNum(){
        killNum++;
    }

    public boolean isTalented() {
        return talent;
    }

    public void setTalented() {
        talent = true;
    }
}
