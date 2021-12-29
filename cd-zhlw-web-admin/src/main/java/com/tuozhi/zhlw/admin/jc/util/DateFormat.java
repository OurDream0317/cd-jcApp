package com.tuozhi.zhlw.admin.jc.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateFormat {

    private static DateFormat format;

    /**
     * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static long getDistDates(String start, String end)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        return getDistDates(startDate, endDate);
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        // if(totalDate == 0) {
        // totalDate = 1;
        // }

        return totalDate;
    }

    public static String getTime(String format) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        return new SimpleDateFormat(format).format(time);
    }

    public static Timestamp string2Timestamp(String s) {
        if (!s.endsWith(" 00:00:00"))
            s += " 00:00:00";
        Timestamp ts = Timestamp.valueOf(s);
        return ts;
    }

    public static Timestamp string3Timestamp(String s) {
        if (s.indexOf(":") == -1)
            s += "00:00:00";
        Timestamp ts = Timestamp.valueOf(s);
        return ts;
    }

    public static String Date2Format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(date);
    }

    public static long long2day(long l) {
        return l / 1000 / 3600 / 24;
    }

    public static String Timestamp2StringFormat(Timestamp time) {
        return Timestamp2StringFormat(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String Timestamp2StringFormat(Timestamp time, String format) {
        return time == null ? null : new SimpleDateFormat(format).format(time);
    }

    public static String Date2StringFormat(Date date) {
        return Date2StringFormat(date, "yyyy-MM-dd");
    }

    public static String Date3StringFormat(Date date) {
        return Date2StringFormat(date, "yyyyMM");
    }

    public static String Date2StringFormat(Date time, String format) {
        return time == null ? null : new SimpleDateFormat(format).format(time);
    }

    public String getDate(String style) {
        return null;
    }

    public static String getFormatDate(Date date) {
        if (date == null) {
            return "--/--/--";
        }
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return cz.format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return cz.format(date);
    }

    public static String getFormatDate2(Date date) {
        SimpleDateFormat cz = new SimpleDateFormat("yyyyMMddHHmmss");
        return cz.format(date);
    }

    public static Date StringToDate(String str) throws ParseException {
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd");
        return cz.parse(str);
    }

    public static Date StringToDate3(String str) throws ParseException {
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return cz.parse(str);
    }

    /**
     * flag = 0 取当天开始时间 flag = 其它时取当天结束时间
     *
     * @param str
     * @param flag
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String str, int flag) throws ParseException {
        if (StringUtils.isBlank(str))
            return null;
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cz.parse(str);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (flag == 0) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            return c.getTime();
        }
        return c.getTime();
    }

    /**
     * 取时间的当天开始还是结束时间 flag = 0 取当天开始时间 flag = 其它时取当天结束时间
     *
     * @param str
     * @param flag
     * @return
     * @throws ParseException
     */
    public static Date dateToPart(Date date, int flag) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (flag == 0) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            return c.getTime();
        }
        return c.getTime();
    }

    public static Date StringToDate2(String str) throws ParseException {
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM");
        return cz.parse(str);
    }

    public static String StringToString(String str) throws ParseException {
        SimpleDateFormat cz1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat cz2 = new SimpleDateFormat("dd日 E");
        Date date = cz1.parse(str);
        return cz2.format(date);
    }

    public static String getFormatShortDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd");
        return cz.format(date);
    }

    public static String getFormatShortDateAddDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 30);
        date = c.getTime();
        return sdf.format(date);
    }

    public static String getFormatShortDate(Date date, String html) {
        if (date == null) {
            return html;
        }
        SimpleDateFormat cz = new SimpleDateFormat("yyyy-MM-dd");
        return cz.format(date);
    }

    public static String getFormatYear(Date date) {
        SimpleDateFormat cz = new SimpleDateFormat("yyyy");
        return cz.format(date);
    }

    public static String getFormatMonth(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat cz = new SimpleDateFormat("MM");
        return cz.format(date);
    }

    public static String frontZeroFill(int sourceStr, int formatLength) {
        return String.format("%0" + formatLength + "d", sourceStr);
    }

    public static String getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }

        return age + "";
    }

    /**
     * 计算年龄
     */
    public static String getAge(String birthDay) {
        java.text.DateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        if (StringUtils.isBlank(birthDay))
            return "";
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        try {
            cal.setTime(format.parse((birthDay.trim())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {// 当前年
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {// monthNow>monthBirth
                age--;
            }
        }
        return age + "";
    }

    public static boolean DateCompare(Date date1, Date date2)
            throws ParseException {
        if (date1.after(date2)) {
            return false;
        } else {
            return true;
        }
    }


    public static List<Date> dateSplit(Date startDate, Date endDate) {
//		if (!startDate.before(endDate))
//			throw new Exception("开始时间应该在结束时间之后");
        Long spi = endDate.getTime() - startDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        return dateList;
    }

    /**
     * 往前或往后几天
     *
     * @param number
     * @return
     */
    public static String aroundDate(int number) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, number);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

}
