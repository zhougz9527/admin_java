package com.example.admin_java.config;

import com.alibaba.fastjson.JSON;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.RedisService;
import com.example.admin_java.service.UserService;
import com.example.admin_java.utils.JWTUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * filter过滤请求
 *
 * @Author: Think
 * @Date: 2018/8/7
 * @Time: 17:48
 */
@WebFilter(urlPatterns = { "/day/*", "/user/*", "/weather/*" }, filterName = "tokenFilter")
public class TokenFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","X-Admin-Token,Origin, X-Requested-With, Content-Type, Accept");


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String token = httpServletRequest.getHeader(Constant.HEADER_KEY);//header方式
        boolean isFilter = false;

        String method = ((HttpServletRequest) request).getMethod();
        int code = 0;
        Result result = null;
        if (method.equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else{
            if (null == token || token.isEmpty()) {
                code = -1;
                result = ResultUtil.error(10013);
            } else {
                // 获取jwt中的用户名
                String username = JWTUtil.getUsername(token);
                String redisToken = (String) redisService.get(username);
                if (StringUtils.isEmpty(redisToken)) {// 如果redisToken过期或者用户登出
                    code = -1;
                    result = ResultUtil.error(10015);
                } else {
                    UserEntity user = userService.findByAccount(username);
                    if (null == user) {
                        code = -1;
                        result = ResultUtil.error(10015);
                    } else {
                        code = 0;
                        isFilter = true;
                        // 更新redis中的token的过期时间
                        redisService.set(username, token, Constant.JWT_EXPIRE_TIME);
                        request.setAttribute("userEntity", user);
                    }
                }
            }
            if (-1 == code) {// 验证失败
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(response.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(result);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (UnsupportedEncodingException e) {
                    logger.error("过滤器返回信息失败: " + e.getMessage());
                } catch (IOException e) {
                    logger.error("过滤器返回信息失败: " + e.getMessage());
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }

            if (isFilter) {
                logger.info("token filter ok!");
                chain.doFilter(request, response);
            }
        }


    }

    @Override
    public void destroy() {

    }

}
