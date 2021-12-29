package com.tuozhi.zhlw.common.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class JsonUtil {
    /**
     * json转list
     *
     * @param jsons
     * @return
     */
    public static List<Map<String, Object>> convertJsonToList(String jsons) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArr = JSONArray.parseArray(jsons);
        if (jsonArr != null) {
            for (Iterator<?> it = jsonArr.iterator(); it.hasNext(); ) {
                try {
                    JSONObject obj = (JSONObject) it.next();
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (java.util.Map.Entry<String, Object> entry : obj.entrySet()) {
                        row.put(entry.getKey(), entry.getValue());
                    }
                    list.add(row);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
        return list;
    }


}
