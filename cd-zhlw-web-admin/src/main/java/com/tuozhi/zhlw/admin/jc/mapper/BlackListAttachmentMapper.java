package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.BlackListAttachment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BlackListAttachmentMapper {


    /**
     * 获取 黑名单附件数据 根据 黑名单申请流程表的RequestId
     *
     * @param requestId
     * @return
     */
    List<BlackListAttachment> getAppDataByRequestId(@Param("requestId") Long requestId);



    List<BlackListAttachment> getBZAppDataByRequestId(@Param("attachmentId") Long attachmentId,@Param("requestId") Long requestId);
    /**
     * 删除 黑名单附件 根据AttachmentId
     *
     * @param attachmentId
     * @return
     */
    int deleteDataByAttachmentId(@Param("attachmentId") Integer attachmentId);

    @Insert("insert into CD_JC.JC_APP_BLACKLIST_ATTACHMENT  values(CD_JC.SEQ_JC_APP_BL_ATTACHMENT.nextval, #{attachmentName},#{attachmentSize}," +
            "#{attachmentPath},#{requestId},#{fileType},#{userId},#{userName},#{deptId},#{deptName},#{flowpathId})")
    int saveBlacklistAttachment(BlackListAttachment blacklistAttachment);

    /**
     * 获取 黑名单附件 根据附件ID
     *
     * @param attachmentId
     * @return
     */
    BlackListAttachment getDataByAttachmentId(@Param("attachmentId") Integer attachmentId);
}
