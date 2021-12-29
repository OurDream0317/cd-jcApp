package com.tuozhi.zhlw.admin.jc.entity;

import com.tuozhi.zhlw.common.utils.SHAUtil;
import lombok.Data;

/**
 * @author linqi
 * @create 2020/01/28 10:49
 **/
@Data
public class GreyInfo {

    private Long sid;
    private String id;
    private String vehicleId;//车牌
    private String currGantryId;//当前门架编号
    private String currGantryName;//当前门架编号
    private String currTime;//当前时间
    private String exTime;//离开时间
    private String exPos;//离开路网位置
    private String exPosName;//离开路网位置
    private String status;//1:在网内 2:本省出 3:省界出
    private String currSectionId;//
    private String currSectionName;// 
    private String color; //颜色汉字
    private Integer colorType; //颜色汉字
    private String newStatus;
    private String picId;
    private String picUrl;//图片路径
    private Integer carType;
    private String axleType;//轴型
    private String carTypeText; 
    private String discription;
    private String ltype;
    private String ltypeText;

    public String getVoKey() throws Exception {
        String key = vehicleId + currGantryId + currTime;
        return SHAUtil.shaEncode(key);
    }
}
