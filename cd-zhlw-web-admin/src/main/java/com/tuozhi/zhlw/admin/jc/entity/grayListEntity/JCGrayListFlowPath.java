package com.tuozhi.zhlw.admin.jc.entity.grayListEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCGrayListFlowPath implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer flowPathId;
    private Integer requestId;
    private String flowPathName;
    private Integer loginUserId;
    private String handleSuggestion;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date readTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operateTime;
    private String operateUserName;
    private String deptId;
    private Integer sendDirection;
    private Integer flowPathStatus;
    private Integer workFlowDefinationId;
    private Integer workFlowNodeId;
    private String deptName;
}
