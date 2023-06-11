package com.chen.config;

import com.chen.pojo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AdminInterceptorAspect {

    @Autowired
    private HttpSession session;

    @Around("     execution(* com.chen.controller.adminController.AdminCommentController.deleteComment(..))"

            + "|| execution(* com.chen.controller.adminController.TopicController.deleteTopic(..))"

            + "|| execution(* com.chen.controller.adminController.UserController.deleteUser(..))"
            + "|| execution(* com.chen.controller.adminController.UserController.submitUpdate(..))"
            + "|| execution(* com.chen.controller.adminController.UserController.submitAdd(..))"

            + "|| execution(* com.chen.controller.adminController.AdminTagController.deleteTag(..))"
            + "|| execution(* com.chen.controller.adminController.AdminTagController.submitUpdate(..))"
            + "|| execution(* com.chen.controller.adminController.AdminTagController.submitAdd(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        User loginUser = (User)session.getAttribute("loginUser");

        if (!StringUtils.isEmpty(loginUser)){
            if (loginUser.getUsername().equals(MyStaticProperties.superAdminName)){
                return joinPoint.proceed();
            }
        }

        return "403.html";
    }


}