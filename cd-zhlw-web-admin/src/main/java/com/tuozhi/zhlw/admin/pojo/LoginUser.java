package com.tuozhi.zhlw.admin.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/05 0:09
 **/

@Data
public class LoginUser implements Serializable {

    public LoginUser() {
    }

    private static final long serialVersionUID = 4507869346123296527L;

    @Id
    private Long id;
    // 登录成功ID
    private Long userId;
    // 用户名
    private String loginName;
    private String deptWork;//部门编号 列如1010101
    private Long deptId;
    private Long deptParentId;
    private String deptName;
    private String userName;
    private String password;
    private Long privilegeId;
    private String roleIds;
    private Integer validStatus;
    private List<Map<String,Object>> roleDataFunctions;
    private String workFlowDeptRole;//部门角色
    private String deptLongId;//实际部门的长编号

    private Date lastPasswordModifyTime;
    private String passwordHistory;
    private boolean passwordModifyStatus;
    private String gxName;//所属公司

    public LoginUser(Long userId, String loginName) {
        super();
        this.userId = userId;
        this.loginName = loginName;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LoginUser other = (LoginUser) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        return true;
    }
    

}
