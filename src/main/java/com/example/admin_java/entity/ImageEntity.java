package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/7/25
 * @Time: 12:45
 */
@Data
@Entity
@Table(name = "image")
public class ImageEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int type;
    @Column(name = "image_url")
    private String imageUrl;
    private int used;

}
