package com.example.admin_java.config;

import com.alibaba.fastjson.JSON;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
@Slf4j
@WebFilter(urlPatterns = { "/day/*", "/user/*", "/weather/*" }, filterName = "tokenFilter")
public class TokenFilter implements Filter {

    @Autowired
    RedisService redisService;

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
                UserEntity user = (UserEntity) redisService.get(token);
                if (null == user) {
                    code = -1;
                    result = ResultUtil.error(10015);
                } else {
                    code = 0;
                    isFilter = true;
                    request.setAttribute("user", user);
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
                    log.error("过滤器返回信息失败: {}", e.getMessage());
                } catch (IOException e) {
                    log.error("过滤器返回信息失败: {}", e.getMessage());
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
                log.info("token filter过滤ok!");
                chain.doFilter(request, response);
            }
        }


    }

    @Override
    public void destroy() {

    }

}
