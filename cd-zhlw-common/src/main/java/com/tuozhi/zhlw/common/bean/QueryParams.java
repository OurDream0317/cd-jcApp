package com.tuozhi.zhlw.common.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/06 22:15
 * 分页bean
 **/

@Data
public class QueryParams {
    private boolean paging = true;
    private String sort;
    private int start;//开始行数索引
    private int page;//第几页
    private int limit;;//一页显示几行
    private String sortString;

    public String getSortString() {
        List<SortBean> sortBeans = JSON.parseArray(this.getSort(), SortBean.class);
        if (CollectionUtils.isEmpty(sortBeans)) {
            return "";
        }
        SortBean sortBean = sortBeans.get(0);
        return sortBean.getProperty() +" " + sortBean.getDirection();
    }

    @Data
    private static class SortBean {
      private String property;
      private String direction;
    }
    /**
     	* 将参数对象转化为Map对象
     * @param conditions
     * @param params
     */
    public static void putInMapByQueryParams(Map<String,Object> conditions,QueryParams  params){
    	if(null==params) {
    		return ;
    	}
    	if(params.isPaging()) {
    		conditions.put("pageStart", (params.getPage()-1)*params.getLimit());
    		conditions.put("pageEnd", (params.getPage())*params.getLimit());
    	}
    	if(null!=params.getSort()) {
    		conditions.put("sort", params.getSort());
    	}
    }

}
