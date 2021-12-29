package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

//该类是出口通行数据对门架交易数据大车小标后的数据对象
@Data
public class JCNewDataOfPassId {
    private Double realFee;  //应收金额
    private Double oweFee;   //欠费金额
    private Integer newVehicleType; //大车小标前的车型
    private String oldVehicleType;  //大车小标后的车型
    private String tollgantryid;  //门架编号
    private String passId;  //通行ID
}
