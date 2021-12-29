package com.tuozhi.zhlw.admin.jc.util;
/**
 *
 *  车种枚举类
 * @author wangweixiang
 * @date 2020/4/9 14:43
**/
public enum CarClassEnum {
    one("普通",0),
    two("军警",8),
    three("紧急",10),
    four("车队",14),
    five("绿通车",21),
    six("联合收割机",22),
    seven("抢险救灾",23),
    eight("集装箱",24),
    nine("大件运输",25),
    ten("应急车",26),
    eleven("货车列车或半挂汽车列车",27),
    twelve("默认值",999);
    //车种名称
    private String typeName;
    //车种代码
    private Integer typeCode;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    CarClassEnum(String typeName, Integer typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }
    //获取车种
    public static String matchClass(Integer typeCode) {
        String typeName = "";
        if (typeCode != null) {
            CarClassEnum[] values = CarClassEnum.values();
            for (CarClassEnum value : values) {
                if (typeCode.intValue() == value.getTypeCode().intValue()) {
                    typeName = value.getTypeName();
                }
            }
        }
        return typeName;
    }
}
