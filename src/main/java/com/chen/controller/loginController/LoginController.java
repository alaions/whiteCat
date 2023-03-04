package com.chen.controller.loginController;

import com.chen.Service.adminService.UserService;
import com.chen.mapper.UserMapper;
import com.chen.pojo.RegisterUser;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String checkLogin(String username, String password, HttpSession session){

        User user = userService.getUserByUsername(username);
        System.out.println(userService.getAdminPassword());
        if (Objects.nonNull(user)){
            if("admin".equals(username) && userService.getAdminPassword().equals(password)){
                session.setAttribute("loginUser", user);
                return "redirect:/main.html";
            } else if(password.equals(user.getPassword())){
                session.setAttribute("loginUser", user);
                return "redirect:/toUserIndex";
            }
        }

        return "redirect:/loginFiled";
    }

    /*registry.addViewController("/main.html").setViewName("admin/adindex");*/
    @GetMapping("/main.html")
    public String toAdmin(HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        String username = loginUser.getUsername();
        if ("admin".equals(username)){
            return "admin/adindex";
        }
        else {
            return "redirect:/";
        }
    }

    // 不知道为什么直接在checkLogin方法中存值msg然后重定向在前端用${}取不到值
    // 这里登录失败后重定向到这个方法里存值然后跳转会去，就可以避免重定向取不到值
    @GetMapping("/loginFiled")
    public String loginFiled(Model model){
        model.addAttribute("msg", "用户名或密码错误！");
        return "login";
    }

    @GetMapping("/register")
    public String toRegister(Model model){
        // 传一个对象，避免在register中用${}取对象的值的时候空指针异常
        model.addAttribute("registerUser", new RegisterUser());
        return "register";
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";
    }

}
