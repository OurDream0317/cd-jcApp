package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/06 22:53
 **/

    public interface UserMapper extends MyMapper<SysUserEntity> {

        @Select({"select u.id,u.LOGIN_NAME,u.USER_NAME,u.id as deptId," +
                "d.DEPT_NAME, u.PASSWORD ,u.valid_status " +
                "from SYS_USERS u " +
                "left join  SYS_DEPT d on d.id = u.DEPT_ID where 1=1"})
        public List<SysUserEntity> findGridAll();


        @Select("select  r.DATA_PRIVILEGE_ID ,s.FUNCTION_CODE,S.FUNCTION_NAME, S.DESCRIPTION,S.ICONCLS" +
                "     from SYS_USER_ROLES y " +
                "    inner join SYS_ROLES r on r.ID = y.ROLE_ID     " +
                "     inner join SYS_ROLES_MENU f on y.ROLE_ID = f.ROLE_ID " +
                "     inner join sys_menu s on f.FUNCTION_ID = s.ID and s.valid_status = 1 " +
                "     where y.USER_ID = #{userId}")
    public List<Map<String,Object>> findFunctionDataByByUserId(Long userId);


    @Update("update SYS_USERS set VALID_STATUS = '0' where ID = #{id}")
    int deleteUserByUpdateMark(String id) ;

    public List<SysUserEntity> findAllByUserName(@Param("userName") String userName) ;
    
    public List<SysUserEntity> findAllByUserNameAndDeptId(@Param("userName") String userName,@Param("deptIds") List<Long> deptIds) ;

    @Select("select count(1) from sys_users where user_name = #{userName,jdbcType=VARCHAR}")
    int queryRepeatUserName(String userName) ;

    @Select("select count(1) from sys_users where login_name = #{loginName,jdbcType=VARCHAR}")
    int queryRepeatLoginName(String loginName) ;

    /**
     * 获取 用户数据 根据DeptId和workFlowDeptRole
     * @param deptId
     * @param workFlowDeptRole
     * @return
     */
    List<SysUserEntity> getDataListByDeptAndDeptRole(@Param("deptId")Long deptId, @Param("workFlowDeptRole")Long workFlowDeptRole);

    /**
     * 根据当前登录人的部门ID查询
     * @return
     */
    String findLoginInfo(@Param("deptId") Long deptId);

}
