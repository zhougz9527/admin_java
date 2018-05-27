package com.example.admin_java.result;

/**
 *
 * 响应码枚举
 *
 * @Author: Think
 * @Date: 2018/5/15
 * @Time: 22:01
 */
public enum ResultCodeEnum {

    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    UNAUTHORIZED(401, "未认证"),
    NOT_FOUND(404, "接口不存在"),
    INTERNAL_SERVICE_ERROR(500, "服务器错误"),
    USERNAME_NOT_EMPTY(10001, "用户名不能为空"),
    LOGIN_ERROR(10002, "登录失败"),
    PWD_NOT_RULE(10003, "密码必须大于等于6位且小等于12位"),
    MOBILE_NOT_RULE(10004, "手机号码格式错误"),
    NICKNAME_NOT_RULE(10005, "昵称只能包含中文数字和下划线,位数要大于4位且小于19位");

    public int code;
    public String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取message的值
     * @param code
     * @return
     */
    public static String getMessageByCode(int code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.code == code) {
                return resultCodeEnum.message;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
