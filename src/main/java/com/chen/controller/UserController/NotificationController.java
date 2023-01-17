package com.chen.controller.UserController;

import com.chen.MyUtils.NotificationUtil;
import com.chen.Service.adminService.NotificationService;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationUtil notificationUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/read/{id}/{message}")
    public String read(@PathVariable("id") Integer id, @PathVariable("message") String message){
        notificationService.updateStatus(id);
        return "redirect:/toPersonal/" + message;
    }

    @GetMapping("/readAllSupport")
    public String readAllSupport(HttpSession session){
        User userLogin = (User) session.getAttribute("loginUser");
        notificationService.updateSomeStatus(userLogin.getId(), "support");
        return "redirect:/toPersonal/support";
    }

    @GetMapping("/readAllChat")
    public String readAllChat(HttpSession session){
        User userLogin = (User) session.getAttribute("loginUser");
        notificationService.updateSomeStatus(userLogin.getId(), "chat");
        return "redirect:/toPersonal/chat";
    }

    @GetMapping("/submitChat/{id}")
    @ResponseBody
    public String submitChat(@PathVariable("id") Integer id, String content, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        notificationUtil.setChatNeed(loginUser.getId(), id, content, userService);
        notificationService.insertChar(notificationUtil);

        return "success";
    }

    @GetMapping("/reply/{id}")
    public String replyChat(@PathVariable("id") Integer id, HttpSession session){

        return "redirect:/chat/" + id;
    }
}
