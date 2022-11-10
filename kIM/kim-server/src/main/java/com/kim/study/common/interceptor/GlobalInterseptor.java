package com.kim.study.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterseptor
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/3 10:04
 * @Version 1.0
 */

@Component
public class GlobalInterseptor implements AsyncHandlerInterceptor {



    @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            response.sendRedirect("http://www.baidu.com");
            return false;
        }else {
            return true;
        }

    }
}
