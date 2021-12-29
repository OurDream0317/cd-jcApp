package com.tuozhi.zhlw.common.enums;

import lombok.Getter;

@Getter
public enum ResultMsgEnum {

    NO_LOGIN_INFO(1,"没有登录信息!"),
    NO_USER(2,"用户名不存在!"),
    USER_IS_STOP(3,"帐号停用，请联系管理员！"),
    PASSWORD_ERROR(4,"您输入的密码错误，请重新输入！"),
    TOKEN_OK(5,"创建token成功"),
    LOGIN_OK(6,"登录成功！"),
     SAVE_OK(7,"保存成功！"),
     SAVE_ERROR(8,"保存失败！"),
    UPDATE_OK(7,"修改成功！"),
    UPDATE_ERROR(8,"修改失败！"),
     DELETE_ERROR(9,"删除失败！"),
     DELETE_OK(10,"删除成功！"),
    SUCCESS(11,"success"),
    ERROR(12,"error"),
    FUNCTION_ID_ERROR(12,"FUNCTION_ID 不能重复！"),
    FUNCTION_ID_NULL(13,"FUNCTION_ID 不能为空"),
    QUERY_OK(14,"查询成功！"),
    QUERY_ERROR(15,"查询失败！"),
    RETURN_LOGIN(16,"您的登陆已超时，请重新登陆！！"),
	INSERT_NOKEY(17,"主键不能重复添加"),
	NO_FILE(18,"没有传入文件"),
	FILENOTFOUND(19,"未找到文件"),
	SUCCESSADD(20,"成功导出"),
	FILEALIKE(21,"文件名字相同"),
    USER_STOP(22,"帐号已停用，请联系管理员!"),
    ID_HAVING(23,"员工编号以存在!"),
    PASSWORD_INCONFORMITY(24,"两次密码输入不一致！"),
    PASSWORD_EDITOR_SUCCESS(25,"密码修改成功,请重新登录。请记住新密码："),
    EXCEL_ERROR(26,"请先执行查询操作！"),

    INPUT_IS_NULL(500,"参数不能为空"),
    TOKE_IS_TIMEOUT(501,"TOKEN 超时，请重新登录！"),
    USER_LOGIN_REPEAT(502,"同一浏览器只能登录一个帐号！"),
    PASSWORD_CHECK_1(503,"不能与前6次密码相同！");



    private int code;
    private String msg;

    private ResultMsgEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
