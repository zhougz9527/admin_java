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
 * @Time: 17:51
 */
@Data
@Entity
@Table(name = "menu")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String title;
    private String icon;
    private String url;
    private int type;
    private int sort;
    @Column(name = "parent_id")
    private int parentId;
    private int status;
}
