package com.tuozhi.zhlw.common.base.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getNowDate() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }

    public static Date stringToDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date stringToDate(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /*
    public static Date jsonNodeToDate(JsonNode jsonNode) throws ParseException {
        if(jsonNode==null){
            return new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(jsonNode.asText());
        } catch (ParseException e) {
            return sdf.parse(jsonNode.asText() + " 00:00:00");
        }
    }
*/
    public static Date stringToDateBegin(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(strDate + " 00:00:00");
        } catch (Exception e) {
            return null;
        }
    }

    public static Date stringToDateEnd(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(strDate + " 23:59:59");
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateBegin(String strDate) {
        return strDate + " 00:00:00";
    }

    public static String dateEnd(String strDate) {
        return strDate + " 23:59:59";
    }

    public static String timeFormat(Timestamp time) {
        return timeFormat(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String timeFormat(Timestamp time, String format) {
        return time == null ? null : new SimpleDateFormat(format).format(time);
    }

    public static String timeFormat(Date date) {
        return date == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
