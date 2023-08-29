package com.chen.controller.loginController;

import com.chen.Service.adminService.UserService;
import com.chen.config.MyStaticProperties;
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
    public String checkLogin(String username, String password, HttpSession session, Model model){

        User user = userService.getUserByUsername(username);

        /*先判断是否属于超级账号，后面在判断是否属于用户*/
        if (username.equals(MyStaticProperties.superAdminName) && password.equals(MyStaticProperties.adminPassword)
            || Objects.nonNull(user) && password.equals(user.getPassword())){
            // level > 1 或 用户名属于超级账号进入管理员页面
            if(username.equals(MyStaticProperties.superAdminName) || user.getRankLevel() > 1){
                // 如果是超级账号那么user为空
                if(Objects.isNull(user)){
                    user = userService.getUserByUsername("admin");
                    user.setUsername(MyStaticProperties.superAdminName);
                    user.setPassword(MyStaticProperties.adminPassword);
                }
                session.setAttribute("loginUser", user);
                return "redirect:/main.html";
            }
            else {
                if (user.getBan() == 1){
                    model.addAttribute("msg", "此账号已被封禁！");
                    return "login";
                }
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

        if (loginUser.getUsername().equals(MyStaticProperties.superAdminName) || loginUser.getRankLevel() > 1){
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
