package com.tuozhi.zhlw.common.base.mymapper;

import com.tuozhi.zhlw.common.base.inputParam.FilterBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QueryConfig {
    private String orFieldValue;
    public List<FilterBase> Filters;
    public Map<ColumnIndex, Object> FiltersMap;
    private boolean isOrQuery = false;
    public PageQuery Paging;

    public QueryConfig() {
        this.Filters = new ArrayList<FilterBase>();
        this.FiltersMap = new HashMap<ColumnIndex, Object>();
        this.Paging = new PageQuery();
    }

    public boolean isOrQuery() {
        return isOrQuery;
    }

    public boolean isVaildOrQuery() {
        return StringUtils.isNotEmpty(orFieldValue);
    }

    public String getOrFieldValue() {
        return orFieldValue;
    }

    public String getOrFieldValue_ForLike() {
        return "%" + orFieldValue + "%";
    }

    public void setOrFieldValue(String orFieldValue) {
        isOrQuery = true;
        this.orFieldValue = orFieldValue;
    }
}
