package com.chen.controller.UserController;

import com.chen.Service.adminService.UserService;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SelectUserController {

    @Autowired
    private UserService userService;

    private List<User> userList;

    @GetMapping("/user.html")
    public String user(Model model){
        model.addAttribute("userList", userList);
        return "user/user";
    }

    @GetMapping("/toUserPage")
    public String toUserPage(){
        userList = userService.getUserList();
        return "redirect:/user.html";
    }

    @GetMapping("/searchName")
    @ResponseBody
    public String searchName(String nameMessage){
        System.out.println(nameMessage);
        List<User> list = userService.getUserListByMessage(nameMessage);
        if (list.size() == 0){
            return "none";
        }
        userList = list;
        return "success";
    }
}
