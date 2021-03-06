package com.example.admin_java.result;

import org.omg.PortableInterceptor.LOCATION_FORWARD;

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
    USERNAME_NOT_RULE(10004, "用户名必须是手机号或者邮箱"),
    NICKNAME_NOT_RULE(10005, "昵称只能包含中文数字和下划线,位数要大于4位且小于19位"),
    MOBILE_NOT_RULE(10006, "该手机号码已经被注册"),
    SEND_CODE_FAILED(10007, "发送验证码失败"),
    VERIFY_CODE_ERROR(10008, "验证码错误"),
    PARAM_ERROR(10009, "缺少必要参数"),
    IMAGE_CODE_ERROR(10010, "图形验证码错误"),
    IMAGE_BASE64_ERROR(10011, "图片转换base64错误"),
    PWD_MISS_OR_ACCOUNT_NOT_EXISTS(10012, "用户不存在"),
    TOKEN_NOT_NULL(10013, "token不能为空"),
    PWD_ERROR(10014, "密码错误"),
    TOKEN_NOT_EXISTS(10015, "token无效"),
    LOCATION_ERROR(10016, "定位失败");

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
