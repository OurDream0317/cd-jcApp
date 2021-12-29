package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author ma_zy
 * @Date Created in 19:03 2019/10/8
 * @Modified by
 */
@Repository
public interface LogMapper extends MyMapper<SysLogEntity>{

    List<SysLogEntity> findLogsByCondition(@Param(value = "startTime") String startTime,
                                           @Param(value = "endTime") String endTime,
                                           @Param(value = "loginName") String loginName) ;


}
