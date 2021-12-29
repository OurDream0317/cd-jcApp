package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.PredefineMenu;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author linqi
 * @create 2019/09/19 6:36
 **/

public interface PredefineMenuMapper extends MyMapper<PredefineMenu> {

    @Select("SELECT * from PREDEFINE_MENU where FUNCTIONID = #{functionId}")
     PredefineMenu findAllByFunctionId(Long functionId);

    //当当前用户为admin管理员时查询所有路段
    @Select("SELECT wm_concat(DISTINCT(''''||bs.id||'''')) from SYS_DEPT sd left join CD_PASS.base_section bs on sd.DEPT_LONG_ID=bs.id ")
    String getByDeptLoginIdOfAdmin();

    //根据当前用户的deptId去查询该用户所管理的所有路段
    @Select("SELECT\n" +
            "\twm_concat(DISTINCT(''''||sqlbs.SE_ID||''''))\n" +
            "FROM\n" +
            "\tCD_SYSTEM.SYS_DEPT sqlsd LEFT\n" +
            "\tJOIN CD_PASS.BASIC_SECTION sqlbs ON sqlsd.DEPT_LONG_ID = sqlbs.SE_ID \n" +
            "WHERE\n" +
            "\tsqlsd.WORKFLOWDEPTROLE = 2 CONNECT BY PRIOR sqlsd.ID = sqlsd.PARENT_ID START WITH sqlsd.ID=#{deptId}")
    String getByDeptLoginId(Long deptId);

    @Select("DELETE FROM PREDEFINE_MENU WHERE PARENTID = #{functionId} or FUNCTIONID = #{functionId}")
    void deleteByFunctionId(Long functionId);

    @Select("DELETE FROM BASE_FUNC_SQL_PARAM WHERE FUNCTIONID = #{functionId}")
    void deleteBaseFuncSqlParamByFunctionId(Long functionId);

    @Select("SELECT * FROM PREDEFINE_MENU WHERE FUNCTIONID = #{functionId}")
    PredefineMenu findByFunctionId(Long functionId);
}
