package com.donkeycode.core.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间Utils
 *
 * @author lichenxiao@ksjgs.com
 * <p>
 * version 1.0
 * <p>
 * 2016年4月14日
 */
public class DateFormatUtil extends org.apache.commons.lang3.time.DateUtils {

    static Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");


    public static final String FORMAT_DEFAULT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String format_yyyyMMdd = "yyyyMMdd";
    public static final String format_yyyyMM = "yyyyMM";
    public static final String format_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String format_yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static final String[] WEEK_STR = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将相应格式的时间字符串转成DATE
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date, String formatType) {
        SimpleDateFormat f = new SimpleDateFormat(formatType);
        Date innerDate;
        try {
            innerDate = f.parse(date);
        } catch (ParseException e) {
            innerDate = new Date();
            e.printStackTrace();
        }
        return innerDate;
    }

    /**
     * 获取相应格式的当前时间
     *
     * @param formatType
     * @return
     */
    public static String getCurrentFormatDate(String formatType) {
        Locale locale = Locale.CHINESE;
        SimpleDateFormat dateStyle = new SimpleDateFormat(formatType, locale);
        return dateStyle.format(new Date());
    }

    /**
     * 把日期时间格式化为指定格式，如：yyyy-MM-dd HH:mm
     *
     * @param dt         java.util.Date
     * @param formatType : 指定日期转化格式字符串模板,例如:yyyy-MM-dd
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(Date dt, String formatType) {
        String newDate = "";
        if (dt != null) {
            Locale locale = Locale.CHINESE;
            SimpleDateFormat dateStyle = new SimpleDateFormat(formatType, locale);
            newDate = dateStyle.format(dt);
        }
        return newDate;
    }

    /**
     * 将时间格式字符串转换为时间对象
     *
     * @param strDate
     * @return
     */
    public static Date format(String strDate, String aFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 获取某一天是星期几
     *
     * @param date
     * @return
     * @author 张广彬
     * @date 2013-5-10
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取某一天的前一天
     *
     * @param date
     * @return
     * @author 张广彬
     * @date 2013-6-7
     */
    public static Date getYesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param time   时间戳(长整形字符串)
     * @param format 格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(long time, String format) {
        Date d = new Date();
        d.setTime(time);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    /**
     * 返回一个指定数字后的时间
     *
     * @param x 指定几分钟
     * @return
     */
    public static String getTimeMinuteAdd(Date date, int x) {
        long newDate = date.getTime() + (x * 60 * 1000);
        return getDate(newDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 当前时间+interval 分钟 后的时间
     *
     * @param interval
     * @return
     * @author 王振江
     * @date 2013-6-6
     */
    public static Date addDate(final int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, interval);
        Date date = c.getTime();
        return date;

    }

    /**
     * @param @param  interval
     * @param @return 设定文件<br>
     * @return Date 返回类型<br>
     * @Title: addDay<br>
     * @Description: 将某天增加一天<br>
     * @throws<br>
     * @author ShiXueQiang<br>
     * @date 2015年9月26日 上午11:35:01<br>
     */
    public static Date addDay(Date dt, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, interval);
        Date date = c.getTime();
        return date;

    }

    public static String getFutureDay(String appDate, String format, int days) {
        String future = "";
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {

        }

        return future;
    }

    /**
     * 字符型时间变成时间类型
     *
     * @param date   字符型时间 例如: "2008-08-08"
     * @param format 格式化形式 例如: "yyyy-MM-dd"
     * @return 出现异常时返回null
     */
    public static Date getFormatDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 得到今天的星期
     *
     * @return 今天的星期
     */
    public static String getWeeks(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param date   时间戳(长整形字符串)
     * @param format 格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(Date date, String format) {
        String formatDate = "";
        if (date != null) {
            DateFormat df = new SimpleDateFormat(format);
            formatDate = df.format(date);
        }
        return formatDate;
    }

    /**
     * 日期转换(竞彩专用)
     *
     * @param agalistNo
     */
    public static String getDate(String agalistNo) {
        String resDate = "";
        int iss = Integer.valueOf(agalistNo.substring(8, 9));
        switch (iss) {
            case 1:
                resDate = "周一" + " " + agalistNo.substring(9, 12);
                break;
            case 2:
                resDate = "周二" + " " + agalistNo.substring(9, 12);
                break;
            case 3:
                resDate = "周三" + " " + agalistNo.substring(9, 12);
                break;
            case 4:
                resDate = "周四" + " " + agalistNo.substring(9, 12);
                break;
            case 5:
                resDate = "周五" + " " + agalistNo.substring(9, 12);
                break;
            case 6:
                resDate = "周六" + " " + agalistNo.substring(9, 12);
                break;
            case 7:
                resDate = "周日" + " " + agalistNo.substring(9, 12);
                break;
            default:
                break;
        }

        return resDate;
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday
     * @return
     * @author zhangwenjie
     * @date 2016年4月29日
     */
    public static Integer getAge(Date birthday) {
        int age = -1;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (birthday != null) {
            now.setTime(new Date());
            born.setTime(birthday);
            if (born.after(now)) {
                return age;
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
            if (age == 0) {
                age = 1;
            }
        }
        return age;
    }

}
