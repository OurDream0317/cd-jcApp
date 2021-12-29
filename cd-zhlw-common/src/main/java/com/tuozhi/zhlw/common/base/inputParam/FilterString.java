package com.tuozhi.zhlw.common.base.inputParam;

import org.apache.commons.lang3.StringUtils;

public class FilterString extends FilterBase {
    public FilterString() {

    }

    public FilterString(ColumnIndex Key, String Value) {
        this.Key = Key;
        this.Value = Value;
    }

    public String Value;

    public String getLikeValue() {
        if (StringUtils.isNotEmpty(Value)) {
            return "%" + Value + "%";
        } else {
            return "%";
        }
    }
}