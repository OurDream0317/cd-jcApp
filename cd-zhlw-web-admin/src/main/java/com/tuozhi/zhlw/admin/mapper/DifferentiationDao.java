package com.tuozhi.zhlw.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DifferentiationDao {

    List<Map<String, String>> findJinPoliceList(@Param("cardId") String cardId);
}
