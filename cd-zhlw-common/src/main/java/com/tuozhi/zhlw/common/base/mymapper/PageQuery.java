package com.tuozhi.zhlw.common.base.mymapper;

import com.tuozhi.zhlw.common.base.util.SortUtil;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author WHQ
 * <p>
 * 接收分页请求的start(行索引)\limit(页大小)
 */
public class PageQuery {

    private boolean paging = true;
    private String sort;

    @Min(value = 0)
    @Digits(fraction = 0, integer = 32)
    private Integer start;//
    @Min(value = 1)
    @Digits(fraction = 0, integer = 32)
    private Integer page;// 当前页
    @Digits(fraction = 0, integer = 32)
    @Max(value = 100000)
    private Integer limit;

    public Integer getStart() {
        return start;
    }

    public Integer getPageStart() {
        return start;
    }

    public Integer getPageEnd() {
        return limit;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public String getSort() {
        return sort;
    }

    public String getOrderString() {
        if (StringUtils.isNotEmpty(sort)) {
            return " order by " + SortUtil.sortDynamics(sort, "");
        } else {
            return "";
        }
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
