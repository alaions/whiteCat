package com.chen.controller.adminController;

import com.chen.MyUtils.CheckUtil;
import com.chen.MyUtils.CutPage;
import com.chen.Service.adminService.UserService;
import com.chen.pojo.Select;
import com.chen.pojo.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CutPage cutPage;

    @Autowired
    private Select select;

    @Setter
    @Getter
    private List<User> userList;

    @GetMapping("/memberList")
    public String memberList(Model model){
        // 分页信息
        cutPage.setNowPage(1);
        // 将展示数量恢复
        cutPage.setEveryPageCount(CutPage.EVERYPAGECOUNT);
        // 用户总量
        cutPage.setTotalCount(userService.getTotalUserCount());
        // 搜索框为空
        select.setSelectMessage("");
        // 将展示数量恢复
        select.setShowCount(CutPage.EVERYPAGECOUNT);
        // 分页展示判断
        userList = userService.getUserList();
        model.addAttribute("userList", cutPage.getLimitList(userList));
        // 更新要展示的list
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("select", select);

        return "admin/user/member-list";
    }

    @GetMapping("/selectSubmit")
    // selectMessage不为空，则是正常搜索，为空则是空搜索或者是nextPage重定向回来
    public String selectSubmit(Select viewSelect, Model model){
        // 直接select = viewSelect会将showCount归0
        int showCount = select.getShowCount();
        select = viewSelect;
        select.setShowCount(showCount);

        // 每次搜索前将页码回调成1，避免List溢出
        cutPage.setNowPage(1);
        // 设置搜索后的总页数
        cutPage.setTotalCount(userService.getUserByWhichCount(viewSelect));
        // 更新要展示的list
        userList = userService.getUserByWhich(viewSelect);

        model.addAttribute("userList", cutPage.getLimitList(userList));
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);

        return "admin/user/member-list";
    }

    @GetMapping("/nextPage")
    public String nextPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (cutPage.getNowPage() != cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getNowPage() + 1);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("userList", cutPage.getLimitList(userList));
        return "admin/user/member-list";
    }


    @GetMapping("/lastPage")
    public String lastPage(Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if(cutPage.getNowPage() != 1){
            cutPage.setNowPage(cutPage.getNowPage() - 1);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("userList", cutPage.getLimitList(userList));
        return "admin/user/member-list";
    }

    @GetMapping("/toWhichPage/{page}")
    public String toWhichPage(@PathVariable("page") Integer page, Model model){
        // 这里修改了cutPage但是不用重新传入session，session是取地址，实时更新，model等于request
        if (page > cutPage.getPageCount()){
            cutPage.setNowPage(cutPage.getPageCount());
        } else {
            cutPage.setNowPage(page);
        }
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("userList", cutPage.getLimitList(userList));
        return "admin/user/member-list";
    }

    @GetMapping("/updateShowCount/{showCount}")
    public String updateShowCount(@PathVariable("showCount") int showCount, Model model){
        cutPage.setNowPage(1);
        cutPage.setEveryPageCount(showCount);
        select.setShowCount(showCount);
        model.addAttribute("select", select);
        model.addAttribute("cutPage", cutPage);
        model.addAttribute("userList", cutPage.getLimitList(userList));
        return "admin/user/member-list";
    }

    @GetMapping("/deleteUser/{id}")
    public String memberList(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "redirect:/user/memberList";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Integer id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("null", "");
        model.addAttribute("message", "");
        return "admin/user/member-edit";
    }

    @GetMapping("/submitUpdate")
    public String submitUpdate(User user, String repassword, Model model){
        if(user.getPassword().equals(repassword)){
            userService.updateUserById(user);
            model.addAttribute("user", userService.getUserById(user.getId()));
            model.addAttribute("message", "修改成功！");
        }

        return "admin/user/member-edit";
    }

    @GetMapping("/usernameCheck")
    @ResponseBody
    public String usernameCheck(String username){
        if(!StringUtils.hasText(username)){
            return "用户名不能为空！";
        } else {
            if (username.length() < 3){
                return "用户名长度不能小于3！";
            }else if (username.length() > 10){
                return "用户名长度不能大于10！";
            }
            else {
                return "ok";
            }
        }
    }

    @GetMapping("/passwordCheck")
    @ResponseBody
    public String passwordCheck(String password){
        if(!StringUtils.hasText(password)){
            return "密码不能为空！";
        } else {
            if (password.length() < 6){
                return "密码长度必须6至12位！";
            } else {
                return "ok";
            }
        }
    }

    @GetMapping("/rePasswordCheck")
    @ResponseBody
    public String rePasswordCheck(String password, String rePassword){
        if(password.equals(rePassword)){
            return "ok";
        } else {
            return "两次密码不一致！";
        }
    }

    @GetMapping("/emailCheck")
    @ResponseBody
    public String emailCheck(String email){
        if (email.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$")) {
            return "ok";
        } else {
            return "邮箱格式不正确!";
        }
    }

    @GetMapping("/memberAdd")
    public String memberAdd(){
        return "admin/user/member-add";
    }

    @PostMapping("/submitAdd")
    @ResponseBody
    public String submitAdd(User user, String rePassword){
        //后端再次判断
        if (CheckUtil.check(user.getUsername(), user.getPassword(), rePassword, user.getEmail())){
            userService.insertUser(user);
            return "添加成功！";
        } else {
            return "数据不符合格式，添加失败！";
        }

    }
}
