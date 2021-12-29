package com.tuozhi.zhlw.common.base.returnParam;

import com.tuozhi.zhlw.common.base.mymapper.CdError;

import java.util.List;

public class CdError_ObjectList<T> extends CdError {
    private List<T> root;
    private List<T> rootTwo;
    private List<Object> rootThree;

    public List<Object> getRootThree() {
        return rootThree;
    }

    public void setRootThree(List<Object> rootThree) {
        this.rootThree = rootThree;
    }

    public List<T> getRootTwo() {
        return rootTwo;
    }

    public void setRootTwo(List<T> rootTwo) {
        this.rootTwo = rootTwo;
    }

    private int totalProperty;

    public CdError_ObjectList() {
        super();
        this.totalProperty = 0;
    }

    public List<T> getRoot() {
        return root;
    }

    public void setRoot(List<T> list) {
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
