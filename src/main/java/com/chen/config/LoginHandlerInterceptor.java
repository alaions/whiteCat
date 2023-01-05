package com.chen.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //得到session的值
        Object loginUser = request.getSession().getAttribute("loginUser");

        if (loginUser != null){
            return true;
        } else {
            request.setAttribute("msg", "没有权限，请先登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }
    }
}
