package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Think
 * @Date: 2018/5/24
 * @Time: 22:43
 */
@Data
@Entity
@Table(name = "user")
public class UserEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private int uid;
    private String account;
    private String avatar;
    private String password;
    private String nickname;
    private String gender;
    private int status;
    @Column(name = "last_login")
    private String lastLogin;
    private String gtime;
    private String utime;


}
