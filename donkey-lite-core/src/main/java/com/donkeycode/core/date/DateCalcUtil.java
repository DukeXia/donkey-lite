package com.donkeycode.core.date;

import org.apache.commons.lang3.StringUtils;

import com.donkeycode.core.utils.StringSuperUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yanjun.xue
 */
public class DateCalcUtil {

    private DateCalcUtil() {
    }

    private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean is8Date(String date) {
        if (StringSuperUtils.isEmpty(date)) {
            return false;
        }
        String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern pattern = Pattern.compile(eL);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static String getSysDate(String format) {
        if (StringSuperUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static int getSysYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static Date getDateByString(String date, String format) {
        if (StringSuperUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        if (StringSuperUtils.isNotEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("转换为日期类型错误：DATE：" + date + "  FORMAT:" + format);
            }
        } else {
            return null;
        }
    }

    public static String getFormatDate(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } else {
            return null;
        }
    }

    public static Date getDayStartOfDate(Date date) {
        String formatDate = getFormatDate(date, "yyyy-MM-dd");
        return StringSuperUtils.isEmpty(formatDate) ? null : getDateByString(formatDate + " 00:00:00", "");
    }

    public static Date getDayEndOfDate(Date date) {
        String formatDate = getFormatDate(date, "yyyy-MM-dd");
        return StringSuperUtils.isEmpty(formatDate) ? null : getDateByString(formatDate + " 23:59:59", "");
    }

    /**
     * 一月的最后一天
     *
     * @param date 日期
     * @return 当月最后一天
     */
    public static Date getDayEndOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Date dayStartOfMonth = getDayStartOfMonth(date);
        Calendar cal = Calendar.getInstance();

        cal.setTime(dayStartOfMonth);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return getDayEndOfDate(cal.getTime());
    }

    /**
     * 一月的第一天
     *
     * @param date 日期
     * @return 当月第一天
     */
    public static Date getDayStartOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return getDayStartOfDate(cal.getTime());
    }

    public static Date getDateByLongDate(Long millis) {
        if (millis == null) {
            return new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getTime();

    }

    /**
     * 日期加减运算
     *
     * @param date 日期
     * @param day  加天数（减传负数)
     * @return 返回运算后日期
     */
    public static Date daysOperation(Date date, int day) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // 加天
            cal.add(Calendar.DATE, day);
            return cal.getTime();
        } else {
            return null;
        }
    }

    public static String secToTime(int time) {
        String timeStr = null;

        int hour = 0;
        int minute = 0;
        int second = 0;

        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
