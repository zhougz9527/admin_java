package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/23
 * @Time: 12:00
 */
@Data
@Entity
@Table(name = "role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int rid;
    @Column(name = "role_name")
    private String roleName;
    private String remark;


}
