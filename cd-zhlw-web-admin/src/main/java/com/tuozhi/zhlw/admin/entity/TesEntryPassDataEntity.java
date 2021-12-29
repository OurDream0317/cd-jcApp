package com.tuozhi.zhlw.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="JC_S_LANE_ENTRYPASSDATA")
public class TesEntryPassDataEntity implements Serializable{

    @Id
    private Long sid;
    private String Id;
    private String obuId; //OBU序号编码
    private String cardId; //CPC卡或ETC卡的编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime; //入口处理时间
    private String vehicleId;
    private Long vehicleType;
    private Long vehicleClass;
    private String enWeight;
    private String specialType;
    private String vehicleSignId;
    private String entollLaneHex;
    private String passId; //通行标识ID
    private Long enaxleCount;
    private Long mediaType; //通行介质类型
    private String identifyVehicleId;
    private String rstationId;
    private String rstationName;
    private Long requestId;
    private String eludemoneyType;
    private String eludemoneyTypeItem;
}
