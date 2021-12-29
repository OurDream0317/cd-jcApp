package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "JC_OPERATION_ATTCHMENT")
public class JCOperationAttachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer attachmentId;
    private String attachmentName;
    private Long attachmentSize;
    private String attachmentPath;
    private Integer type;
    private String typeName;
    private Long Jid;
    private String upFileUserName;//上传人
    private String upFileDept;//部门
}
