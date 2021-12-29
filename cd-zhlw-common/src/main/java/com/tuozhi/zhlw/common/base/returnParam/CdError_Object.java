package com.tuozhi.zhlw.common.base.returnParam;

import com.tuozhi.zhlw.common.base.mymapper.CdError;

public class CdError_Object<T> extends CdError {
    private T obj;

    public CdError_Object() {
        super();
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
