package Ink_server.EconomySystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Ink_server.InkSound;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class GlobalData {
    private static long lastDeliveryUpdatePeriod = 0;
    private static long lastAdventureUpdatePeriod = 0;

    private static final InkSound plugin = InkSound.getInstance();

    // ===== 文件路径 =====
    private static final File DATA_FILE = new File(
            InkSound.getInstance().getDataFolder(),
            "global_data.json"
    );

    // ===== 内部数据类（用于 JSON 序列化）=====
    private static class DataWrapper {
        long lastDeliveryUpdatePeriod;
        long lastAdventureUpdatePeriod;
    }

    // ===== 加载（服务器启动时调用）=====
    public static void load() {
        if (!DATA_FILE.exists()) {
            save(); // 创建默认文件
            return;
        }

        try (Reader reader = new FileReader(DATA_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            DataWrapper data = gson.fromJson(reader, DataWrapper.class);

            if (data != null) {
                //json读取到包装类后，赋值给静态变量
                lastDeliveryUpdatePeriod = data.lastDeliveryUpdatePeriod;
                lastAdventureUpdatePeriod = data.lastAdventureUpdatePeriod;
            }
        } catch (Exception e) {
            plugin.getLogger().severe("加载 global_data.json 失败: " + e.getMessage());
        }
    }

    // ===== 保存（服务器关闭时调用）=====
    public static void save() {
        try {
            DATA_FILE.getParentFile().mkdirs();

            //从静态字段复制到包装类
            DataWrapper data = new DataWrapper();
            data.lastDeliveryUpdatePeriod = lastDeliveryUpdatePeriod;
            data.lastAdventureUpdatePeriod = lastAdventureUpdatePeriod;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(data);

            try (Writer writer = new FileWriter(DATA_FILE)) {
                writer.write(json);
            }
        } catch (Exception e) {
            plugin.getLogger().severe("保存 global_data.json 失败: " + e.getMessage());
        }
    }

    public static long getLastDeliveryUpdatePeriod() {
        return lastDeliveryUpdatePeriod;
    }

    public static void  setLastDeliveryUpdatePeriod(long lastDeliveryUpdatePeriod) {
        GlobalData.lastDeliveryUpdatePeriod = lastDeliveryUpdatePeriod;
    }

    public static long getLastAdventureUpdatePeriod() {
        return lastAdventureUpdatePeriod;
    }

    public static void  setLastAdventureUpdatePeriod(long lastAdventureUpdatePeriod) {
        GlobalData.lastAdventureUpdatePeriod = lastAdventureUpdatePeriod;
    }

    //时间映射
    private static final String[] hours = {"子时", "丑时", "寅时", "卯时", "辰时", "巳时", "午时", "未时", "申时", "酉时", "戌时", "亥时"};
    private static final String[] numbers = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] tens = {"初" ,"十", "廿", "卅"};
    private static final String[] months = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"};
    private static final String[] TIANGANs = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static final String[] DIZHIs = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    public static String getHour(){
        long now = System.currentTimeMillis();
        long dayMills = (now + 32400000L) % 86400000L;
        long SHICHENCount = dayMills / 7200000L;
        return hours[(int) SHICHENCount];
    }

    public static String getDay(){
        LocalDate day = LocalDate.now(ZoneId.of("Asia/Shanghai"));
        int year = day.getYear();
        int month = day.getMonthValue();
        int dayOfMonth = day.getDayOfMonth();
        String TIANGGAN = TIANGANs[(year - 1984) % 10];
        String DIZHI = DIZHIs[(year - 1984) % 12];
        String yearOut = TIANGGAN + DIZHI + "年";
        String monthOut = months[month-1] + "月";
        int dayCheck0 = dayOfMonth % 10;
        int dayCheck1 = dayOfMonth / 10;
        String dayOut;
        if (dayCheck0 == 0){
            if (dayCheck1 == 1){
                dayOut = "初十";
            }else dayOut = numbers[dayCheck1-1] + "十";
        }else {
            dayOut = tens[dayCheck1] +numbers[dayCheck0-1];
        }
        return yearOut + monthOut + dayOut;
    }

}
