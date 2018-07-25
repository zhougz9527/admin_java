package com.example.admin_java.repository;

import com.example.admin_java.entity.ImageEntity;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/7/25
 * @Time: 12:43
 */
public interface ImageRepository extends BaseRepository<ImageEntity, Integer>{

    ImageEntity findByImageUrl(String imageUrl);

    List<ImageEntity> findFirstByUsedAndTypeOrderByIdAsc(int used, int type);
}
