package com.tuozhi.zhlw.admin.jc.util;
/**
 *
 *
 * 通行介质类型枚举类
 * @author wangweixiang
 * @date 2020/4/9 14:57
**/
public enum MediatypeEnum {
    one("OBU",1),
    two("CPC卡",2),
    three("纸券",3),
    four("无通行介质",9);
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

    MediatypeEnum(String typeName, Integer typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }
    //获取车种
    public static String matchMediatype(Integer typeCode) {
        String typeName = "";
        if (typeCode != null) {
            MediatypeEnum[] values = MediatypeEnum.values();
            for (MediatypeEnum value : values) {
                if (typeCode.intValue() == value.getTypeCode().intValue()) {
                    typeName = value.getTypeName();
                }
            }
        }
        return typeName;
    }
}
