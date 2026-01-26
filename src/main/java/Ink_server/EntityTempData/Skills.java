package Ink_server.EntityTempData;

import org.bukkit.Bukkit;

public class Skills {
    private final String  id;
    private final int expiry;
    private final int tickInterval;
    private final SkillType skillType;
    private int tickCounter = 0;
    private int numCounter = 0;

    public enum SkillType {
        ATTACK,
        SHOOT,
        FORMATION,
        DEFENCE,
        PASSIVE
    }

    public Skills(String id, SkillType skillType, int tickInterval, int expiry) {
        this.id = id;
        this.skillType = skillType;
        this.tickInterval = tickInterval;
        if (expiry == -1){
            this.expiry = -1;
        }else {
            this.expiry = expiry + Bukkit.getCurrentTick();
        }
    }

    public boolean shouldEffect(){
        tickCounter++;
        if (tickInterval == -1 || tickCounter >= tickInterval){
            tickCounter = 0;
            return true;
        }else {
            return false;
        }
    }

    public boolean isExpired(){
        return  expiry != -1 && Bukkit.getCurrentTick() > expiry;
    }

    public String getId() {
        return id;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public int getNumCounter() {
        return numCounter;
    }

    public void addNumCounter(){
        numCounter++;
    }
}
