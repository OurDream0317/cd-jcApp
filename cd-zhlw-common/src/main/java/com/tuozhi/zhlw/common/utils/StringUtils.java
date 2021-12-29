package com.tuozhi.zhlw.common.utils;

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNullOrEmpty(String str) {
        return isEmpty(str);
    }

    /**
     * String数组转Integer数组
     *
     * @param strArray
     * @return
     * @version 1.0
     */
    public static Integer[] parseInteger(String[] strArray) {
        if (strArray == null) {
            return new Integer[0];
        }
        if (strArray.length == 1 && isEmpty(strArray[0])) {
            return new Integer[0];
        }
        Integer[] intArray = new Integer[strArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.valueOf(strArray[i]);
        }
        return intArray;
    }

    public static Long[] parseLong(String[] strArray) {
        if (strArray == null) {
            return new Long[0];
        }
        if (strArray.length == 1 && isEmpty(strArray[0])) {
            return new Long[0];
        }
        Long[] intArray = new Long[strArray.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Long.valueOf(strArray[i]);
        }
        return intArray;
    }

    public static String splitFirst(String value) {
        return splitFirst(value, "\\|");
    }

    public static String splitFirst(String value, String regex) {
        if (value == null) {
            return "";
        }
        return value.split(regex)[0];
    }

    public static String specialCharTrim(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(" ", "");
    }

}
