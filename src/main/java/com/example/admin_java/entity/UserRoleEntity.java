package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/23
 * @Time: 12:24
 */
@Data
@Entity
@Table(name = "user_role")
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private int uid;
    private int rid;
}
