package com.tuozhi.zhlw.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContainerDao {

    List<Map<String, String>> findContainerRecord(@Param("vehicleId") String vehicleId,
                                                  @Param("startTime") String startTime,
                                                  @Param("endTime") String endTime);

    Map<String, String> findSplitPassTdByPassId(String passId);

    Map<String, String> findSplitExitPtSdByPassId(String passId);

    List<Map<String, String>> findContainerPackageList();

    List<Map<String, String>> findContainerCheckRList();

}
