package com.chen.controller.UserController;

import com.chen.Service.adminService.FansService;
import com.chen.pojo.Fans;
import com.chen.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class FansController {

    @Autowired
    private FansService fansService;

    @GetMapping("/beFans/{id}")
    public String beFans(Fans fans, HttpSession session, @PathVariable("id") Integer id){
        User loginUser = (User) session.getAttribute("loginUser");
        fans.setUserId(id);
        fans.setFansId(loginUser.getId());
        Date date = new Date();
        fans.setCreatTime(date);
        fans.setUpdateTime(date);

        fansService.beFans(fans);

        return "redirect:/toOtherPersonal/" + id;
    }

    @GetMapping("/everBeFans/{id}")
    public String everBeFans(Fans fans, HttpSession session, @PathVariable("id") Integer id){
        User loginUser = (User) session.getAttribute("loginUser");
        fans.setUserId(id);
        fans.setFansId(loginUser.getId());
        fans.setUpdateTime(new Date());

        fansService.everBeFans(fans);

        return "redirect:/toOtherPersonal/" + id;
    }

}
