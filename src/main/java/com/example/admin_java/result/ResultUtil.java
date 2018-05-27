package com.example.admin_java.result;

/**
 *
 * 响应结果生成工具
 *
 * @Author: Think
 * @Date: 2018/5/15
 * @Time: 22:05
 */
public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result(200, ResultCodeEnum.getMessageByCode(200), object);
        return result;
    }

    public static Result succeedNoData() {
        return success(null);
    }

    public static Result error(int code) {
        Result result = new Result(code, ResultCodeEnum.getMessageByCode(code));
        return result;
    }

}
