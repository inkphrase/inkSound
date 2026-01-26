package Ink_server.EntityTempData;

import org.bukkit.Bukkit;

public class BuffEffect {
    private final String  id;
    private final double amount;
    private final int expiry;
    private final BuffType buffType;

    public BuffEffect(String id, double amount, BuffType buffType, int expiry) {
        this.id   = id;
        this.amount = amount;
        if (expiry == -1){
            this.expiry = -1;
        }else {
            this.expiry = expiry + Bukkit.getCurrentTick();
        }
        this.buffType = buffType;
    }

    public enum BuffType {
        ATTACK_BASE,
        ARMOR_BASE,
        HEALTH_BASE,
        ATTACK_PERS,
        ARMOR_PERS,
        HEALTH_PERS,
        ATTACK_FINAL,
        ARMOR_FINAL,
        HEALTH_FINAL,
        COOLS,
        SPEED
    }

    public boolean isExpired() {
        return expiry != -1 && Bukkit.getCurrentTick() > expiry;
    }


    public double getAmount() {
        return amount;
    }


    public BuffType getBuffType() {
        return buffType;
    }

    public String getId() {
        return id;
    }

}
