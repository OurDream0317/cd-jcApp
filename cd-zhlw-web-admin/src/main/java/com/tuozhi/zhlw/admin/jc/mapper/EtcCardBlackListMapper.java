package com.tuozhi.zhlw.admin.jc.mapper;






import java.util.List;
import java.util.Map;


public interface EtcCardBlackListMapper {
    
    List<Map<String, Object>> findAll(Map<String, Object> selectMap);
    Long selectAllDataCount(Map<String, Object> selectMap);
    
    List<Map<String, Object>> findEnumCode();
    
    List<Map<String, Object>> findPicture(Map<String, Object> selectMap);
    
    
    
}
