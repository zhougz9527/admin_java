package com.example.admin_java.repository;

import com.example.admin_java.entity.VerifyCodeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Think
 * @Date: 2018/6/3
 * @Time: 22:52
 */
public interface VerifyCodeRepository extends BaseRepository<VerifyCodeEntity, Integer> {

    VerifyCodeEntity findByAccount(String account);

    @Transactional(timeout = 10)
    @Modifying
    @Query("update VerifyCodeEntity entity set entity.expireTime = ?1 where entity.account = ?2 and entity.code = ?3")
    void updateDateByAccountAndCode(String date, String account, String code);

    @Query("select vc from VerifyCodeEntity vc where vc.account = ?1 and ?2 < vc.expireTime ")
    VerifyCodeEntity findByAccountAndDate(String account, String date);

    @Transactional(timeout = 10)
    @Modifying
    @Query("update VerifyCodeEntity entity set entity.status = 1 where entity.account = ?1 and entity.code = ?2")
    void updateStatusByAccountAndCode(String account, String code);
}
