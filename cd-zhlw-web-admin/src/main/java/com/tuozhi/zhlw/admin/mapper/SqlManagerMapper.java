package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysSqlManager;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/18 17:45
 **/

public interface SqlManagerMapper extends MyMapper<SysSqlManager> {

    @Select(" SELECT DISTINCT b.* FROM BASE_FUNC_SQL_PARAM b left join SYS_MENU m on b.FUNCTIONID = m.PRE_DEFINE_MENU_ID WHERE b.FUNCTIONID =  #{functionId} ORDER BY b.PARAMID ASC ")
    List<Map<String, Object>> getSysSqlManagerList(long functionId);

    //根据sid查询PARAMSCRIPTSOURCE
    @Select("SELECT PARAMSCRIPTSOURCE from BASE_FUNC_SQL_PARAM WHERE SID  = #{sid}")
    String getBySid(String sid);

    @Select("DELETE FROM BASE_FUNC_SQL_PARAM WHERE FUNCTIONID = #{functionId}")
    void deleteByFunctionId(Long functionId);

    @Select(" SELECT pm.* FROM PREDEFINE_MENU pm " +
            " left join sys_menu m on pm.FUNCTIONID = m.PRE_DEFINE_MENU_ID WHERE  pm.FUNCTIONID  = #{functionId} ")
    List<Map<String, Object>> getSysSqlFuncList(Long functionId);

}
