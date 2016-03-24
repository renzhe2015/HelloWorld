package com.cifpay.cifpaylib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期操作 工具
 *
 * @author kun
 */
public class DateUtils {
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YY_MM_DD_HH_MM_SS = "yy-MM-dd HH:mm:ss";
    public static final String HH_MM_SS_SSS = "HH:mm:ss.sss";

    /**
     * 获得 几天后的时间
     */
    public static Date getAfterDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();

    }

    /**
     * 返回 指定 某一年 的 某个月含有多少天
     *
     * @param year
     * @param month
     * @return
     */

    public static int getMonthDay(int year, int month) {
        return getMonthDayByYear(year)[--month];
    }

    /**
     * 通过 年份 返回 每个月 有几天
     *
     * @param year
     * @return
     */
    public static int[] getMonthDayByYear(int year) {

        int[] monthDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            monthDay[1]++;
        return monthDay;
    }

    /**
     * 判断 2个日期 相差的时间
     *
     * @param startTime
     * @param endTime
     * @param format    日期的格式 比如 yyyy-MM-dd HH:mm:ss date[0] = day; date[1] = hour;
     *                  date[2] = min; date[3] = sec;
     * @return 返回 一个 数组 每个 元素 依次 表示 相差的天数 小时 分钟 秒
     */
    public static long[] dataBetween(String startTime, String endTime, String format) {

        // 按照传入的格式生成一个simpledateformate对象

        SimpleDateFormat sd = new SimpleDateFormat(format);

        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数

        long nh = 1000 * 60 * 60;// 一小时的毫秒数

        long nm = 1000 * 60;// 一分钟的毫秒数

        long ns = 1000;// 一秒钟的毫秒数

        long diff;

        long[] date = new long[4];

        try {

            // 获得两个时间的毫秒时间差异

            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();

            long day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒

            date[0] = day;
            date[1] = hour;
            date[2] = min;
            date[3] = sec;

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;

    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);

            date = dateFormat.parse(dateStr);

        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, YYYY_MM_DD);
    }


    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return formatDate(date, HH_MM_SS);
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return formatDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     * <p/>
     * 返回的 是long 的毫秒
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static long dateBetween(Date date, Date date1) {
        return ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        Date date = parseDate(strdate);
        return formatDate(addDate(null, 0), YYYY_MM) + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        Date date = parseDate(getMonthBegin(strdate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 得到当前日期是星期几。
     *
     * @return 当为周日时，返回0，当为周一至周六时，则返回对应的1-6。
     */
    public static final int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取当前时间对应 的UTC
     *
     * @return
     */
    public static long getCurrentUTC() {

        Calendar calendar = Calendar.getInstance();
        // 2、取得时间偏移量：
        // int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        // int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        // calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset +
        // dstOffset));

        return calendar.getTimeInMillis();
    }

    /**
     * 将UTC时间转化为时间格式字符串
     *
     * @param utcTime
     * @return <strong>yyyy-MM-dd HH:mm:ss<strong>
     */
    public static String UTC2DateTime(long utcTime) {
        return UTC2DateTime(utcTime, TimeZone.getDefault(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 将UTC时间转换成指定格式的时间字符串
     *
     * @param utcTime  UTC时间
     * @param formater 格式化样式
     * @return <strong>as yyyy/MM/dd HH:mm:ss<strong>
     */
    public static String UTC2DateTime(long utcTime, String formater) {
        return UTC2DateTime(utcTime, TimeZone.getDefault(), formater);
    }

    /**
     * 将UTC时间转化成指定时区的时间字符串格式
     *
     * @param utcTime  UTC時間
     * @param timeZone 時區
     * @param formater 格式化字符串樣式
     * @return
     */
    public static String UTC2DateTime(long utcTime, TimeZone timeZone, String formater) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formater, Locale.getDefault());
            Date date = new Date(utcTime);
            format.setTimeZone(timeZone);
            return format.format(date);
        } catch (Exception e) {
            return utcTime + "";
        }
    }


    /**
     * 根据出生年月日字符串，计算年龄
     *
     * @param birthday YYYY-MM-dd
     * @return int 年龄
     */
    public static int getAge(String birthday) {
        int age = 0;
        try {
            String[] date = birthday.split("-");
            if (date != null && date.length >= 2) {
                String year = date[0];
                String month = date[1];
                String day = date[2];
                age = getAge(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            }
            return age;
        } catch (Exception e) {
            age = 0;
        }

        return age;
    }

    /**
     * 根据年月计算年龄
     */
    public static int getAge(int year, int month, int day) {
        int age = 1;
        Date date = new Date();
        int nowYear = getYear(date);
        int nowMonth = getMonth(date);
        int nowDay = getDay(date);
        if (year > nowYear || (year == nowYear && month >= nowMonth)) {
            return age;
        }
        age = nowYear - year;

        if (nowMonth <= month) {
            if (nowMonth == month) {
                if (day < nowDay) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age == 0 ? 1 : age;
    }

    /**
     * @param @param  day1
     * @param @param  day2
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isSameDay
     * @author wangkun
     * @Description: TODO 比较两个日期是在同一天
     */
    public static boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        return ds1.equals(ds2);
    }

    /**
     * 获取本周周一的日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String getNowWeekBegin(Date date, String format) {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();


        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String preMonday = sdf.format(monday);

        return preMonday;
    }

    /**
     * 获取本周日日期
     *
     * @return
     */
    public static String getNowWeekEnd(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String day = sdf.format(cal.getTime());
        return day;
    }

    /**
     * 获取本月一号的日期
     *
     * @param format
     * @return
     */
    public static String getNowMonthBegin(Date date, String format) {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String first = sdf.format(c.getTime());
        return first;
    }

    /**
     * 获取本月一号的日期
     *
     * @param format
     * @return
     */
    public static String getNowMonthLast(Date date, String format) {
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String last = sdf.format(c.getTime());
        return last;
    }

    /**
     * 根据不同的时间格式获取相关结果
     *
     * @param time
     * @param inFormat
     * @param outFormat
     * @return
     */
    public static String getTime(String time, String inFormat, String outFormat) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(inFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(outFormat);
        String outDate = "";
        try {
            Date date = sdf1.parse(time);
            outDate = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }

    /**
     * 根据 "月" "日" 判断星座
     */
    public static String getConstellation(int m, int d) {
        final String[] constellationArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        final int[] constellationEdgeDay = {20, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21};
        int month = m;
        int day = d;

        if (month >= 1) {
            if (day <= constellationEdgeDay[month - 1]) {
                month = month - 1;
            }
            if (month >= 0) {
                return constellationArr[month];
            }

        }
        return constellationArr[0];
    }


    public static SimpleDateFormat getDataFormat(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format;
    }


    /**
     * 一小时以内显示为：1-60分钟前
     * 一小时以外当天以内显示为：1-24小时前
     * 昨天显示为： 昨天
     * 昨天以前显示为：月+日 例如：8-16 、7-2
     * 跨年显示为： 年+月+日 例如：14-12-23
     */

    public static String getData(long timeMillis) {
        Calendar currentTime = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(timeMillis);
        /**一年以前*/
        if (currentTime.get(Calendar.YEAR) - time.get(Calendar.YEAR) >= 1) {
            return UTC2DateTime(timeMillis, "yyyy-MM-dd");
        } else {
            /**1天以前*/
            if (currentTime.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR) > 1) {
                return time.get(Calendar.MONTH) + "-" + time.get(Calendar.DAY_OF_MONTH);
                /**昨天*/
            } else if (currentTime.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR) == 1) {

                return "昨天";
                /**当天*/
            } else {
                /**1小时前*/
                if (currentTime.get(Calendar.HOUR_OF_DAY) - time.get(Calendar.HOUR_OF_DAY) > 0) {
                    int min = (int) ((System.currentTimeMillis() - timeMillis) / (1000 * 60));
                    if (min < 60) {
                        return (min > 0 ? min : 1) + "分钟前";
                    } else {
                        return currentTime.get(Calendar.HOUR_OF_DAY) - time.get(Calendar.HOUR_OF_DAY) + "小时前";
                    }
                    /**1小时内*/
                } else {
                    int d = currentTime.get(Calendar.MINUTE) - time.get(Calendar.MINUTE);
                    return (d > 0 ? d : 1) + "分钟前";
                }
            }
        }
    }


}
