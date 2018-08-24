package com.example.admin_java.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.global.Constant;
import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Author: Think
 * @Date: 2018/8/18
 * @Time: 19:35
 */
@Slf4j
public class JWTUtil {


    /**
     * 校验jwt token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("jwt token 校验失败, error message: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取jwt token中的username
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        String username = "";
        try {
            DecodedJWT jwt = JWT.decode(token);
            long expTime = jwt.getClaim("exp").asLong();
            if (System.currentTimeMillis()/1000 > expTime) {
                log.info("jwt token 过期 username: {}", username);
                return username;
            }
            username = jwt.getClaim("username").asString();
            log.info("jwt token 获取username成功, username: {}", username);
            return username;
        } catch (JWTDecodeException e) {
            log.error("jwt token 获取username失败, error message: {}", e.getMessage());
            return username;
        }

    }

    /**
     * 生成签名
     * @param username
     * @param secret
     * @return
     */
    public static String createSignature(String username, String secret) {
        String signature = "";
        try {
            Date date = new Date(System.currentTimeMillis() + Constant.JWT_EXPIRE_TIME * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            signature = JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
            log.info("生成signature成功, signature: {}", signature);
            return signature;
        } catch (UnsupportedEncodingException e) {
            log.error("生成signature失败, error message: {}", e.getMessage());
            return signature;
        }
    }

    
}
