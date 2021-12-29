package com.tuozhi.zhlw.common.base.returnParam;

import com.tuozhi.zhlw.common.base.mymapper.CdError;

import java.util.List;

public class CdError_List extends CdError {
    private List root;

    private int totalProperty;

    public CdError_List() {
        super();
        this.totalProperty = 0;
    }

    public List getRoot() {
        return root;
    }

    public void setRoot(List list) {
        this.root = list;
    }

    public int getTotalProperty() {
        if (this.totalProperty != 0) {
            return totalProperty;
        } else {
            return null == this.root ? 0 : this.root.size();
        }
    }

    public void setTotalProperty(int totalProperty) {
        this.totalProperty = totalProperty;
    }
}
