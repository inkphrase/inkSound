package Ink_server.EntityTempData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DotEffect {
    private final String  id;
    private final double amount;
    private final DotType dotType;
    private final int tickInterval;
    private final int expiry;
    private final LivingEntity effector;

    public int counter = 0;

    public DotEffect(String id, double amount, DotType dotType, int tickInterval, int expiry, LivingEntity effector) {
        this.id = id;
        this.amount = amount;
        this.dotType = dotType;
        this.tickInterval = tickInterval;
        if (expiry == -1){
            this.expiry = -1;
        }else {
            this.expiry = expiry + Bukkit.getCurrentTick();
        }
        this.effector = effector;
    }

    public enum DotType {
        DAMAGE,
        HEAL
    }

    public boolean shouldEffect(){
        counter++;
        if (tickInterval == -1 || counter >= tickInterval){
            counter = 0;
            return true;
        }else {
            return false;
        }
    }

    public boolean isExpired(){
        return expiry != -1 && Bukkit.getCurrentTick() > expiry;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public DotType getDotType() {
        return dotType;
    }

    public LivingEntity getEffector() {
        return effector;
    }
}
