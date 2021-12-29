package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class BlackListAttachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long attachmentId;
    private Integer flowpathId;
    private String attachmentName;
    private Long attachmentSize;
    private String attachmentPath;
    private Long requestId;
    private Integer fileType;
    private Long userId;
    private String userName;
    private Long deptId;
    private String deptName;
}
