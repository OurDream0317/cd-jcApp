package com.tuozhi.zhlw.common.base.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "sys_privilege_object")
public class DataPrivilegeEntity implements Cloneable {
    private Integer sid;
    private String objectPropertyRelation;
    private String objectPropertyRelationGroup;
    private String objectPropertyName;
    private String objectExpress;
    private String objectPropertyValue;
    private Integer objectId;
    private Integer privilegeId;

    private List<String> objectPropertyValue_List;
    private String objectPropertyValue_InString;

    public Object clone() {
        DataPrivilegeEntity o = null;
        try {
            o = (DataPrivilegeEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return o;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SID", length = 11)
    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Column(name = "ObjectPropertyRelation", length = 100)
    public String getObjectPropertyRelation() {
        return objectPropertyRelation;
    }

    public void setObjectPropertyRelation(String objectPropertyRelation) {
        this.objectPropertyRelation = objectPropertyRelation;
    }

    @Column(name = "ObjectPropertyRelationGroup", length = 50)
    public String getObjectPropertyRelationGroup() {
        return objectPropertyRelationGroup;
    }

    public void setObjectPropertyRelationGroup(String objectPropertyRelationGroup) {
        this.objectPropertyRelationGroup = objectPropertyRelationGroup;
    }

    @Column(name = "ObjectPropertyName", length = 50)
    public String getObjectPropertyName() {
        return objectPropertyName;
    }

    public void setObjectPropertyName(String objectPropertyName) {
        this.objectPropertyName = objectPropertyName;
    }

    @Column(name = "ObjectExpress", length = 50)
    public String getObjectExpress() {
        return objectExpress;
    }

    public void setObjectExpress(String objectExpress) {
        this.objectExpress = objectExpress;
    }

    @Column(name = "ObjectPropertyValue", length = 100)
    public String getObjectPropertyValue() {
        return objectPropertyValue;
    }

    public void setObjectPropertyValue(String objectPropertyValue) {
        this.objectPropertyValue = objectPropertyValue;
    }

    @Column(name = "ObjectId", length = 64)
    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    @Column(name = "privilegeId", length = 64)
    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }


    @Transient
    public String getObjectPropertyValue_InString() {
        return objectPropertyValue_InString;
    }

    public void setObjectPropertyValue_InString(String objectPropertyValue_InString) {
        this.objectPropertyValue_InString = objectPropertyValue_InString;
    }

    @Transient
    public List<String> getObjectPropertyValue_List() {
        return objectPropertyValue_List;
    }

    public void setObjectPropertyValue_List(List<String> objectPropertyValue_List) {
        this.objectPropertyValue_List = objectPropertyValue_List;
    }

    public String GetPrivilegeSql() {
        String joinstr = StringUtils.join(objectPropertyValue_List.toArray(), "','");
        if (StringUtils.isEmpty(joinstr)) {
            joinstr = "''";
        } else {
            joinstr = "'" + joinstr + "'";
        }
        return " and " + objectPropertyName + " in (" + joinstr + ") ";
    }

}
