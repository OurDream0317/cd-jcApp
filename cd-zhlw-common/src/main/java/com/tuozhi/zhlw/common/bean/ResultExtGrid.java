package com.tuozhi.zhlw.common.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author linqi
 * @create 2019/09/06 23:29
 **/

@Data
public class ResultExtGrid implements Serializable {
    private String message;
    private boolean success;
    private List root;
    private Long totalProperty;
    private Object special;
}
