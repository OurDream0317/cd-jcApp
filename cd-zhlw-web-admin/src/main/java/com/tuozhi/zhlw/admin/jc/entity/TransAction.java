package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransAction implements Serializable {

    private String id; //交易编号1
    private Long fee; //交易金额1
    private Long discountfee;  //优惠金额1
    private String entolllaneid;  //入口车道编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entime;  //入口交易时间1
    private String etccardid;  //ETC卡编号
    private Integer mediatype;  //通行介质类型
    private String vehicleid;  //车辆车牌号码和颜色
    private String vehicletype;  //车辆类型
    private String specialtype;  //特殊类型1
    private Integer vehicleclass;  //车种1
    private String rateversion;  //计算费率版本号1
    private Integer obusign;  //OBU单/双片标识1
    private String obuid;
    private Integer etccardtype;  //ETC卡类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date transtime;  //门架交易时间1
    private String tollintervalid; //收费单元编号1
    private String tac;  //TAC码1
    private Long transfee;  //卡面扣费金额
    private Integer signstatus;  //二类交易：ETC门架处交易失败标记状态    2-写标签非设备失败    3-写标签设备失败    4-写ETC卡非设备失败    5-写ETC卡设备失败    若存在多项，用“|”隔开
    private String terminaltransno;  //PSAM卡脱机交易序列号1
    private Integer cdTransactiontype;  //交易类型标识1
    private String terminalno;  //终端机编号1
    private Integer servicetype; //交易的服务类型1
    private Integer algorithmidentifer; //算法标识1
    private String enpassrecordid;  //车牌入口通行记录编号
    private String forwardetcrecordid;  //车辆在上游ETC门架通行记录编号
    private String backwardetcrecordid;  //车辆在下游ETC门架通行记录编号
    private String tollgantryid;  //门架编号1
    private Integer manualsign;  //二类交易：车牌识别数据经校核标识:1-自动完成匹配    2-校核后匹配
    private String vehiclesignid;
    private String vehiclesignids;
    private String passid;//通行ID
    private String issuerid;//发行方
    private String tollprovinceid;
    private Long payfee;
    private Integer enweight;
    private Integer enaxlecount;
    private String lastgantryhex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastgantrytime;
    private Integer vehiclelength;
    private Integer vehiclewidth;
    private Integer vehiclehight;
    private Integer vehicleweightlimits;
    private String tollintervalpayfee;
    private String tollintervaldiscountfee;
    private String tollintervalfee;
    private Integer platecolor;
    private String feeinfo1;
    private String feeinfo2;
    private String feeinfo3;
    private String feelogmsg;
    private Integer enlanetype;
    private String entollstationhex;
    private String cdMtcfeerequestid="0";
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdMtcfeerequesttime; //1
    private String cpunetid;
    private Integer vehicleusertype;
    private Integer obuversion;
    private Integer electricalpercentage;
    private String obumac;
    private String obuissueid;//OBU/CPC 发行方标识
    private String obusn;
    private String cpuissueid;//CPU 卡片发行方标识
    private Integer cpuversion;
    private String sectionid;
    private String entollstationname;
    private String tollintervalname;
    private String identifyvehicleid;
    private Integer identifyvehicletype;
    private String transtype; //交易类型标识
    private String description;
    private String keyversion;//消费密钥版本号
    private Date cleandate; //如果是ETC交易，则trunc(sysdate,'dd')+1
    private Integer cleanstatus =0;
    private String sectionname;//收费路段名称
    private String direction;
    private Long reqMessageid;
    private String link;
    private Integer status;
    private String extolllaneid;//出口车道编号
    private String enumName1;//车种
    private String enumName;//计费车型
    private String enumName2;//门架识别的车型
    private Long rownum;
}
