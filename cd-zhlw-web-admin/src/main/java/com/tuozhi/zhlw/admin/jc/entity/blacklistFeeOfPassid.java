package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class blacklistFeeOfPassid implements Serializable {
    private String passid;
    private Double realFee;
    private Double oweFee;

}
