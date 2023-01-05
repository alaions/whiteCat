package com.chen.controller.UserController;

import com.chen.MyUtils.NotificationUtil;
import com.chen.Service.adminService.NotificationService;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ContactAdminController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUtil notificationUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/contactMe")
    public String toContactMe(){
        return "user/contact";
    }

    @GetMapping("/sendContactMe")
    @ResponseBody
    public String contactBug(String content, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        int id = loginUser.getId();
        notificationUtil.setChatNeed(id, 1, content, userService);
        notificationService.insertChar(notificationUtil);
        return "success";
    }

}
