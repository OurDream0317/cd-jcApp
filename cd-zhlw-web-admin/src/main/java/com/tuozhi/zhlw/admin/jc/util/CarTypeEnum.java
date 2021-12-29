package com.tuozhi.zhlw.admin.jc.util;

/**
 * 车型枚举类
 */
public enum CarTypeEnum {
    oneCar("一型客车", 1),
    twoCar("二型客车", 2),
    threeCar("三型客车", 3),
    fourCar("四型客车", 4),
    oneTruck("一型货车", 11),
    twoTruck("二型货车", 12),
    threeTruck("三型货车", 13),
    fourTruck("四型货车", 14),
    fiveTruck("五型货车", 15),
    sixTruck("六型货车", 16),
    oneWork("一型专项作业车", 21),
    twoWork("二型专项作业车", 22),
    threeWork("三型专项作业车", 23),
    fourWork("四型专项作业车", 24),
    fiveWork("五型专项作业车", 25),
    sixWork("六型专项作业车", 26);

    //车型名称
    private String typeName;

    //车型代码
    private Integer typeCode;

    CarTypeEnum(String typeName, Integer typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getTypeName() {
		return typeName;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	//获取车型名称
    public static String matchCarType(Integer typeCode) {
        String typeName = "";
        if (typeCode != null) {
            CarTypeEnum[] values = CarTypeEnum.values();
            for (CarTypeEnum value : values) {
                if (typeCode.intValue() == value.getTypeCode().intValue()) {
                	typeName = value.getTypeName();
                	break;
                }
            }
        }
        return typeName;
    }
}
