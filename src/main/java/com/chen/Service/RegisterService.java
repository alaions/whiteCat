package com.chen.Service;

import com.chen.MyUtils.CheckUtil;
import com.chen.pojo.RegisterUser;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.soap.SAAJResult;

@Service
public class RegisterService {

    @Autowired
    private CheckUtil checkUtil;

    public String registerCheck(RegisterUser registerUser){
        return checkUtil.checkReturnMessage(registerUser);
    }

    public User registerUserTurnToUser(RegisterUser registerUser){
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(registerUser.getPassword());
        user.setEmail(registerUser.getEmail());
        return user;
    }

}
