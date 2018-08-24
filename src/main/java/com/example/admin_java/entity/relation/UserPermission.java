package com.example.admin_java.entity.relation;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/24
 * @Time: 15:24
 */
@Data
@Entity
@Table(name = "user")
public class UserPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int uid;
    private String account;
    private String avatar;
    private String nickname;
    private int rid;
    private String gender;
    private int status;
    @Column(name = "last_login")
    private String lastLogin;
    @Column(name = "role_name")
    private String roleName;


}
