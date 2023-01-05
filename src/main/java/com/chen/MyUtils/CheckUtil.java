package com.chen.MyUtils;

import com.chen.Service.adminService.UserService;
import com.chen.pojo.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class CheckUtil {

    @Autowired
    private UserService userService;


    static public boolean check(@Nullable String username,
                                @Nullable String password,
                                @Nullable String rePassword,
                                @Nullable String email){
       return checkUsername(username) && checkPassword(password) && checkPasswordReply(password, rePassword) && checkEmail(email);


    }

    public boolean check(RegisterUser registerUser){
        return checkUsername(registerUser.getUsername()) &&
                checkPassword(registerUser.getPassword()) &&
                checkPasswordReply(registerUser.getPassword(), registerUser.getAgainPassword()) &&
                checkEmail(registerUser.getEmail()) &&
                Objects.isNull(userService.getUserByUsername(registerUser.getUsername()));
    }


    public String checkReturnMessage(RegisterUser registerUser){
        String message = "";
        if (!Objects.isNull(userService.getUserByUsername(registerUser.getUsername()))){
            return "用户名已存在！";
        }
        if (!StringUtils.hasText(registerUser.getUsername())){
            return "用户名不能为空！";
        }
        if (!checkUsername(registerUser.getUsername())){
            return  "用户名长度需3-10！";
        }
        if (!StringUtils.hasText(registerUser.getPassword())){
            return "密码不能为空！";
        }
        if (!checkPassword(registerUser.getPassword())){
            return "密码长度需6-10！";
        }
        if (!StringUtils.hasText(registerUser.getAgainPassword())){
            return "密码不能为空！";
        }
        if (!checkPasswordReply(registerUser.getPassword(), registerUser.getAgainPassword())){
            return "两次密码不一致！";
        }
        if (!StringUtils.hasText(registerUser.getEmail())){
            return "邮箱不能为空！";
        }
        if (!checkEmail(registerUser.getEmail())){
            return "邮箱不正确！";
        }
        return message;
    }


    static public boolean checkUsername(String username){
        if (StringUtils.hasText(username)){
            return username.length() >= 3 && username.length() <= 10;
        }
        return false;
    }

    static public boolean checkPassword(String password){
        if (StringUtils.hasText(password)){
            return password.length() >= 6 && password.length() <= 10;
        }
        return false;

    }

    static public boolean checkPasswordReply(String password, String rePassword){
        if (StringUtils.hasText(password)){
            return password.equals(rePassword);
        }
        return false;
    }

    static public boolean checkEmail(String email){
        if (StringUtils.hasText(email)){
            return email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
        }
        return false;

    }

}
