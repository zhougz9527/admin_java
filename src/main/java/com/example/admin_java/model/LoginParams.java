package com.example.admin_java.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/7/24
 * @Time: 17:37
 */
@Data
public class LoginParams implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "账号不能为空")
    private String account;
    private String password;

}
