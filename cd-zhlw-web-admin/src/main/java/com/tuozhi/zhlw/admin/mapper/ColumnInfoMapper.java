package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.ColumnInfo;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/19 6:37
 **/

public interface ColumnInfoMapper extends MyMapper<ColumnInfo> {

    @Select("DELETE SYS_SQL_COLUMNS WHERE FUNCTIONID = #{functionId}")
    void delSysColumnsInfo(@Param("functionId") Long functionId);

//    @Select("SELECT c.* FROM  \n" +
//            "             SYS_SQL_COLUMNS C left join PREDEFINE_MENU P \n" +
//            "             ON C.FUNCTIONID = P.FUNCTIONID  left join SYS_MENU M ON M.PRE_DEFINE_MENU_ID = P.FUNCTIONID \n" +
//            "             WHERE  \n" +
//            "              C.FUNCTIONID = #{functionId} ORDER BY C.ORDERINDEX"
//            )
    List<Map<String,Object>> findAllColumnsInfoByFunctionId(@Param("functionId") Long functionId);
    List findByFunctionIdAndText(@Param("functionId") Long functionId,@Param("text") String text);
}
