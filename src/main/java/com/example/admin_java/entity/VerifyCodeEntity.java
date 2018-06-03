package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Think
 * @Date: 2018/6/3
 * @Time: 22:38
 */
@Data
@Entity
@Table(name = "verify_code")
public class VerifyCodeEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String account;
    private String code;
    private int status;
    @Column(name = "expire_time")
    private String expireTime;
    private String gtime;
    private String utime;

}
