package net.guerlab.spring.cloud.commons.util;

import java.time.LocalDate;

/**
 * 时间工具类
 * 
 * @author guer
 *
 */
public class TimeUtil {

    private TimeUtil() {

    }

    /**
     * 获取当前日的字符串int值
     * 
     * @return 当前日的字符串int值
     */
    public static int getNowDayInt() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int day = now.getDayOfMonth();
        return year * 10000 + month * 100 + day;
    }

    /**
     * 获取当前日的字符串
     * 
     * @return 当前日的字符串
     */
    public static String getNowDayString() {
        return Integer.toString(getNowDayInt());
    }

    /**
     * 获取当前月的字符串int值
     * 
     * @return 当前月的字符串int值
     */
    public static int getNowMonthInt() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        return year * 100 + month;
    }

    /**
     * 获取当前月的字符串
     * 
     * @return 当前月的字符串
     */
    public static String getNowMonthString() {
        return Integer.toString(getNowMonthInt());
    }
}
