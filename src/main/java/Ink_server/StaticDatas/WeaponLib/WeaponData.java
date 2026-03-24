package Ink_server.StaticDatas.WeaponLib;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.*;

public class WeaponData {
    //设置所需物品数据
    private final String id;
    private final Material material;
    private final Component name;
    private final String model;
    private final List<Component> lore;
    private final Map<String, Double> attributes = new HashMap<>();
    private final Set<String> armorSkills = new HashSet<>();
    private final Set<String> seriesSkills = new HashSet<>();
    //初始化物品属性名常量
    //攻击生命护甲
    public static final String ATTACKBASE = "attackbase";
    public static final String ATTACKPERS = "attackpers";
    public static final String HEALTHBASE = "healthbase";
    public static final String ARMORBASE = "armorbase";
    public static final String ARMORPPERS = "armorppers";
    public static final String HEALTHPERS = "healthpers";
    public static final String ATTACKFINAL = "attackfinal";
    public static final String ARMORFINAL = "armorfinal";
    public static final String HEALTHFINAL = "healthfinal";
    //冷却攻速速度
    public static final String COOLS = "cools";
    public static final String ATTACKSPEED = "attackspeed";
    public static final String SPEED = "speed";
    //破甲环境抵抗额外倍率击退抗性
    public static final String ARMORBREAK = "armorbreak";
    public static final String ENVDEFENCE = "envdefence";
    public static final String MULTIPLE = "multiple";
    public static final String RESISTANCE = "resistance";
    public static final String RECYCLE = "recycle";
    //激活位职业等级附灵
    public static final String ENABLESLOT = "enableslot";
    public static final String LEVELLIMIT = "levellimit";
    public static final String JOBLIMIT = "joblimit";
    public static final String ENCHANTLEVEL = "enchantlevel";

    //初始化技能属性
    public static final String ARMORSKILL = "armorskill";
    public static final String SEIRESSKILL = "seiresskill";

    //仅弓箭手，穿透数目
    public static final String PIERCE = "pierce";

    public WeaponData(String id, Material material, Component name, String model, List<Component> lore){
        this.id = id; this.material = material; this.name = name; this.model = model; this.lore = lore;
    }
    //导入物品属性
    public void setAttribute(String key, double num){
        attributes.put(key,num);
    }
    public void setArmorSkills(String id){armorSkills.add(id);}
    public void setSeriesSkills(String id){seriesSkills.add(id);}
    //获取物品数据
    public String getId() {return id;}
    public Material getMaterial() {return material;}
    public Component getName() {return name;}
    public String getModel() {return model;}
    public List<Component> getLore() {return lore;}
    public Map<String, Double> getAttributes() {return Collections.unmodifiableMap(attributes);}
    public Set<String> getArmorSkills() {return Collections.unmodifiableSet(armorSkills);}
    public Set<String> getSeriesSkills() {return Collections.unmodifiableSet(seriesSkills);}
}
