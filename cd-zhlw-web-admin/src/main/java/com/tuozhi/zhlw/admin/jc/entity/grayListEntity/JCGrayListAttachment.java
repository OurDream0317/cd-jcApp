package com.tuozhi.zhlw.admin.jc.entity.grayListEntity;

import lombok.Data;

import java.io.Serializable;
@Data
public class JCGrayListAttachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer attachmentId;
    private String attachmentName;
    private Long attachmentSize;
    private String attachmentPath;
    private Integer fileType;
    private Integer requestId;
    private String upfileusername;
    private String upfiledept;
}
