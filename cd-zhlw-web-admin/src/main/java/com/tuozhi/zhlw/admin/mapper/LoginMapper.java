package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author linqi
 * @create 2019/09/05 9:16
 **/
public interface LoginMapper extends MyMapper<LoginUser> {

    @Select("select t.ID as deptId,t.PARENT_ID as deptParentId,t.DEPT_NAME as deptName,t.DEPT_WORK as deptWork," +
            "u.LOGIN_NAME as loginName,u.USER_NAME as userName,u.PASSWORD as password,u.ID as userId," +
            "u.LAST_PASSWORD_MODIFY_TIME lastPasswordModifyTime,u.PASSWORD_HISTORY passwordHistory, " +
            "t.PRIVILEGE_ID as privilegeId ,u.VALID_STATUS as validStatus" +
            ",(select wm_concat(ROLE_ID)  from SYS_USER_ROLES where USER_ID= u.ID) roleIds,t.DEPT_LONG_ID deptLongId, " +
            "t.WORKFLOWDEPTROLE workFlowDeptRole " +
            ",jud.GXID gxId,jud.GXNAME gxName "+
            "from SYS_USERS u left join SYS_DEPT t on t.ID = u.DEPT_ID  left join cd_jc.jc_user_dept jud on u.id = jud.USER_ID where u.LOGIN_NAME = #{loginName}")
    LoginUser findBaseUserByLoginName(String loginName);
}
