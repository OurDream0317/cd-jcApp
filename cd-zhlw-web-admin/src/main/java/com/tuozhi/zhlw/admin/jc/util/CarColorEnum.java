package com.tuozhi.zhlw.admin.jc.util;

/**
 * 车牌颜色枚举类
 */
public enum CarColorEnum {

    blue("蓝", 0),
    yellow("黄", 1),
    black("黑", 2),
    white("白", 3),
    change_green("渐变绿", 4),
    yellowAndGreen("黄绿双拼", 5),
    blueAndWhite("蓝白渐变", 6),
    tempPlate("临时牌照", 7),
    undecided("未确定", 9),
    green("绿", 11),
    red("红", 12);

    //车牌颜色
    private String plateColor;

    //颜色代码
    private Integer colorCode;

    CarColorEnum(String color, Integer code) {
        this.plateColor = color;
        this.colorCode = code;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public Integer getColorCode() {
        return colorCode;
    }

    //获取颜色
    public static String matchColor(Integer colorNum) {
        String color = "";
        if (colorNum != null) {
            CarColorEnum[] values = CarColorEnum.values();
            for (CarColorEnum value : values) {
                if (colorNum.intValue() == value.getColorCode().intValue()) {
                    color = value.getPlateColor();
                }
            }
        }
        return color;
    }
}
