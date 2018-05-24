package com.example.admin_java.result;

/**
 *
 * 响应码枚举
 *
 * @Author: Think
 * @Date: 2018/5/15
 * @Time: 22:01
 */
public enum ResultEnum {

    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    UNAUTHORIZED(401, "未认证"),
    NOT_FOUND(404, "接口不存在"),
    INTERNAL_SERVICE_ERROR(500, "服务器错误"),
    USERNAME_NOT_EMPTY(10001, "用户名不能为空"),
    LOGIN_ERROR(10002, "登录失败");

    public int code;
    public String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取message的值
     * @param code
     * @return
     */
    public static String getMessageByCode(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.code == code) {
                return resultEnum.message;
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
