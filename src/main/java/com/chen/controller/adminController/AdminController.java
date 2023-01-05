package com.chen.controller.adminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/welcome")
    public String welcome(){
        return "admin/welcome";
    }

    @GetMapping("/charts")
    public String charts(){
        return "admin/charts";
    }
}
