package com.tuozhi.zhlw.common.annotation;

import java.lang.annotation.*;

/**
 * @author linqi
 * @create 2019-08-30 18:56
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
