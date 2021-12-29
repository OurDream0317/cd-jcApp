package com.tuozhi.zhlw.admin.jc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WorkFlowNodeMapper {
    List findWorkFlowNodeByDefinationId(@Param("definationId") Integer definationId);
}
