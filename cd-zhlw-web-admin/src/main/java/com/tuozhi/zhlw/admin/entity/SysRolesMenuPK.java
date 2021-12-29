package com.tuozhi.zhlw.admin.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/09 17:42
 **/
@Embeddable
@Data
public class SysRolesMenuPK implements Serializable {
    private Long roleId;
    private Long functionId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((functionId == null) ? 0 : functionId.hashCode());
        result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
        return result;
    }



    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SysRolesMenuPK other = (SysRolesMenuPK) obj;
        if (functionId == null) {
            if (other.functionId != null)
                return false;
        } else if (!functionId.equals(other.functionId))
            return false;
        if (roleId == null) {
            if (other.roleId != null)
                return false;
        } else if (!roleId.equals(other.roleId))
            return false;
        return true;
    }
}
