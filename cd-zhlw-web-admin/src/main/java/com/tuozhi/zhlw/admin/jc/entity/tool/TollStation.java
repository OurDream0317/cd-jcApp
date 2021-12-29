package com.tuozhi.zhlw.admin.jc.entity.tool;

import lombok.Data;

import java.util.Date;

/**
 * 收费站实体
 */
@Data
public class TollStation {
    private String id;//收费站编号
    private String name;//收费站名称
    private Integer tollPlazaCount;//收费广场数量
    /*private String neighBorId;
    private String pCount;*/
    private String stationHEX;//收费站HEX字符串
    private Integer type;//收费站类型
    private Integer shortId;//短编号
    private Integer tollPlazaCountIn;//入口广场数量
    private Integer tollPlazaCountOut;//出口广场数量
    /*private Integer isstartusign;*/
    private String sectionId;//所属路段编号
    private Date startTime;//通车时间
    private String remark;
    /*
        private Integer shortSectionId;
    */
    private Integer jsPcount;
    private String oldId;//旧ID
    private Date lastUpdateTime;//最后操作时间
    private Integer deleteFlag;
    private Date insertTime;//数据入库时间
    /*
        private Integer operation;
    */
    private Integer lineType;//线路类型
    private Integer operators;//网络所属运营商
    private String dataMergePoint;//数据汇聚点
    private String imei;//IMEI号
    private String ip;//接入设备ip
    private String snmpVersion;//snmp协议版本号
    private String sumpPort;//snmp端口
    private String community;//团体名称
    private String securityName;//用户名
    private String securityLevel;//安全级别
    private String authentication;//认证协议
    private String authKey;//认证密钥
    private String encryption;//加密算法
    private String secretKey;//加密密钥
}
