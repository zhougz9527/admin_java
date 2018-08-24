package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/23
 * @Time: 12:22
 */
@Data
@Entity
@Table(name = "role_permission")
public class RolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private int rid;
    private int pid;
}
