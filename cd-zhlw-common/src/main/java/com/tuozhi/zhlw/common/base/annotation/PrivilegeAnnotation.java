package com.tuozhi.zhlw.common.base.annotation;

import com.tuozhi.zhlw.common.base.annotation.enumclass.Model;

import java.lang.annotation.*;

/**
 * @author WHQ 自定义权限注解方法
 */
@Retention(value = RetentionPolicy.RUNTIME) // 编译器会记录在class文件中，运行时JVM可以获取Annotation信息
@Target(value = {ElementType.METHOD}) // 表示该方法在方法上使用
@Inherited // 表示该注解具有继承性
public @interface PrivilegeAnnotation {
    int functionId() default 0; // 这个属性代表访问业务方法 需要权限值functionID

    String Description(); //这个属性代表访问业务方法描述 ,增删改操作会记录在数据库日志表中

    public enum LogType {DataBase, LogFile, None}

    Model modelName();//模块名字

    LogType Log() default LogType.None; //这个属性代表此业务方法记录在日志文件中或数据库日志表中
}
